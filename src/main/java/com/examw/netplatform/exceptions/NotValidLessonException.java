package com.examw.netplatform.exceptions;
/**
 * 不是有效的课程
 * @author fengwei.
 * @since 2015年1月31日 上午9:58:26.
 */
public class NotValidLessonException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NotValidLessonException() {
		super();
	}

	public NotValidLessonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotValidLessonException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotValidLessonException(String message) {
		super(message);
	}

	public NotValidLessonException(Throwable cause) {
		super(cause);
	}
	
}
