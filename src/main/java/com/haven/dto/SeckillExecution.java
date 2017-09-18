package com.haven.dto;

import com.haven.entity.SuccessKilled;
import com.haven.enums.SeckillStateEnum;

/**
 * ��װ��ɱִ�к���
 */
public class SeckillExecution {

	private long seckillId;
	
	// ��ɱִ�н��״̬
	private int state;
	
	// ״̬��ʾ
	private String stateInfo;
	
	// ��ɱ�ɹ�����
	private SuccessKilled successKilled;

	public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
//		super();
		this.seckillId = seckillId;
		this.state = seckillStateEnum.getState();
		this.stateInfo = seckillStateEnum.getStateInfo();
		this.successKilled = successKilled;
	}

//	public SeckillExecution(SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
////		super();
//		this.state = seckillStateEnum.getState();
//		this.stateInfo = seckillStateEnum.getStateInfo();
//		this.successKilled = successKilled;
//	}

	public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnumo) {
//		super();
		this.seckillId = seckillId;
		this.state = seckillStateEnumo.getState();
		this.stateInfo = seckillStateEnumo.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	} 
	
}
