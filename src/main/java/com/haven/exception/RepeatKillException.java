package com.haven.exception;

/**
 * ��ɱ�ظ��쳣
 */
public class RepeatKillException extends SeckillException {
	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message) {
		super(message);
	}
	
	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
