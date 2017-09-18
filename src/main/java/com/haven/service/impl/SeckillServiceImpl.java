package com.haven.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.haven.dao.SeckillDao;
import com.haven.dao.SuccessKilledDao;
import com.haven.dto.Exposer;
import com.haven.dto.SeckillExecution;
import com.haven.entity.Seckill;
import com.haven.entity.SuccessKilled;
import com.haven.enums.SeckillStateEnum;
import com.haven.exception.RepeatKillException;
import com.haven.exception.SeckillCloseException;
import com.haven.exception.SeckillException;
import com.haven.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	// md5盐值字符串，用于混淆MD5
	private final String slat = "asdfASD&^*^*";
	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = seckillDao.queryById(seckillId);
		if(seckill == null) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();// 系统当前时间
		if(nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			// 当前不是秒杀时间
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	@Transactional
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		if(md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data is rewrited!");
		}
		// 执行秒杀逻辑：减库存 + 插入明细
		Date nowTime = new Date();
		try {
			// 减库存
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if(updateCount <= 0) {
				// 减库存失败，秒杀结束
				throw new SeckillCloseException("seckill is closed!");
			} else {
				// 秒杀成功，记录购买明细
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount <= 0) {
					// 重复秒杀
					throw new RepeatKillException("seckill repeated!");
				} else {
					// 秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch(SeckillCloseException e1) {
			throw e1;
		} catch(RepeatKillException e2) {
			throw e2;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			// 将所有编译期异常转化为运行时异常
			throw new SeckillException("seckill inner error: "+ e.getMessage());
		}
		
	}

}
