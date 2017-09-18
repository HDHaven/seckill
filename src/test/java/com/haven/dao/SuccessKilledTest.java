package com.haven.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.haven.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledTest {

	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		long id = 1L;
		long userPhone = 18814142805L;
		int insertCount = successKilledDao.insertSuccessKilled(id, userPhone);
		System.out.println("insertCount="+ insertCount);
	}
	
	@Test
	public void testQueryByIdWithSeckill() {
		long id = 1L;
		long userPhone = 18814142805L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, userPhone);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
	
}
