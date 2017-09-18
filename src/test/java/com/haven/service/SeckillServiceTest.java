package com.haven.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haven.dto.Exposer;
import com.haven.dto.SeckillExecution;
import com.haven.entity.Seckill;
import com.haven.exception.RepeatKillException;
import com.haven.exception.SeckillCloseException;
import com.haven.exception.SeckillException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}
	
	@Test
	public void testGetById() {
		long id = 1;
		Seckill seckill = seckillService.getById(id);
		logger.info("seckill={}", seckill);
	}
	
	// 集成测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() {
		long id = 1;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()) {
			// 秒杀开始
			logger.info("exposer={}", exposer);
			long phone = 18814142805L;
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
				logger.info("execution={}", execution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			} catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		} else {
			// 秒杀未开启
			logger.warn("exposer={}", exposer);
		}
	}
	
	@Test
	public void testExportSeckillUrl() {
		long id = 1;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		logger.info("exposer={}", exposer);
		//Exposer [exposed=true, md5=c1a390ddfa0bc1f9705eccceaef8a648, seckillId=1, now=0, start=0, end=0]
	}
	
	@Test
	public void testExecuteSeckill() {
		long id = 1;
		long phone = 18814142805L;
		String md5 = "c1a390ddfa0bc1f9705eccceaef8a648";
		try {
			SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
			logger.info("execution={}", execution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}
	
}
