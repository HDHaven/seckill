package com.haven.service;

import java.util.List;

import com.haven.dto.Exposer;
import com.haven.dto.SeckillExecution;
import com.haven.entity.Seckill;
import com.haven.exception.RepeatKillException;
import com.haven.exception.SeckillCloseException;
import com.haven.exception.SeckillException;

/**
 * 业务接口：站在“使用者”的角度设计接口
 * 三个方面：方法定义粒度、参数、返回类型（类型/异常）
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时输出秒杀地址，否则输出系统时间和秒杀时间
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) 
			throws SeckillException, RepeatKillException, SeckillCloseException;
	
}
