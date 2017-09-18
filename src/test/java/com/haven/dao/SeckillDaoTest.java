package com.haven.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haven.entity.Seckill;

/**
 * 配置spring和Junit整合，Junit启动时加载springIoC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	// 注入Dao实现依赖类
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1L;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}
	
	@Test
	public void testQueryAll() {
		// Java没有保存形参的记录：queryALL(int offset, int limit) --> queryAll(int arg0, int arg1)
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
	
	public void testReduceNumber() {
		Date date = new Date();
		int updateCount = seckillDao.reduceNumber(1L, date);
		System.out.println("updateCount="+ updateCount);
	}
	
}
