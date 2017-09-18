package com.haven.service;

import java.util.List;

import com.haven.dto.Exposer;
import com.haven.dto.SeckillExecution;
import com.haven.entity.Seckill;
import com.haven.exception.RepeatKillException;
import com.haven.exception.SeckillCloseException;
import com.haven.exception.SeckillException;

/**
 * ҵ��ӿڣ�վ�ڡ�ʹ���ߡ��ĽǶ���ƽӿ�
 * �������棺�����������ȡ��������������ͣ�����/�쳣��
 */
public interface SeckillService {

	/**
	 * ��ѯ������ɱ��¼
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * ��ѯ������ɱ��¼
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * ��ɱ����ʱ�����ɱ��ַ���������ϵͳʱ�����ɱʱ��
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) 
			throws SeckillException, RepeatKillException, SeckillCloseException;
	
}
