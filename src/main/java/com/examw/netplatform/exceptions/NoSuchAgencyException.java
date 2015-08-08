package com.examw.netplatform.exceptions;
/**
 * 没有指定的机构[自定义异常类]
 * @author fengwei.
 * @since 2015年1月22日 上午9:43:12.
 */
public class NoSuchAgencyException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NoSuchAgencyException() {
		super();
	}

	public NoSuchAgencyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchAgencyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchAgencyException(String message) {
		super(message);
	}

	public NoSuchAgencyException(Throwable cause) {
		super(cause);
	}
}
