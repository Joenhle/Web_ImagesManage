package com.itheima.exception;
/**
 * Dao�����׳�����ʱ�쳣����ʹ�ø��쳣
 * @author wzhting
 *
 */
public class DaoException extends RuntimeException {

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
