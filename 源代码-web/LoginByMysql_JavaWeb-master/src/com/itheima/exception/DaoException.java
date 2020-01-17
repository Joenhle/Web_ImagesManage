package com.itheima.exception;
/**
 * Dao层若抛出运行时异常，请使用该异常
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
