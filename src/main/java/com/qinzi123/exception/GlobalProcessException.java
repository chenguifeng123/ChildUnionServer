package com.qinzi123.exception;

/**
 * Created by chenguifeng on 2018/11/8.
 */
public class GlobalProcessException extends RuntimeException{

	private final String code;

	private final String message;

	private final Throwable cause;

	public GlobalProcessException()
	{
		super();

		message = null;
		cause = null;
		code = null;

	}

	public GlobalProcessException(String code, String message, Throwable cause)
	{
		super(message, cause);
		this.code = code;
		this.message = message;
		this.cause = cause;
	}

	public GlobalProcessException(String message, Throwable cause)
	{
		super(message, cause);
		this.message = message;
		this.cause = cause;
		code = null;
	}

	public GlobalProcessException(String message)
	{
		super(message);
		this.message = message;
		cause = null;
		code = null;
	}

	public GlobalProcessException(String code, String message)
	{
		super(message);
		this.code = code;
		this.message = message;
		cause = null;
	}

	public GlobalProcessException(Error error)
	{
		super(error.msg());
		this.code = error.code();
		this.message = error.msg();
		cause = null;
	}

	public GlobalProcessException(Error error, Throwable throwable)
	{
		super(error.msg());
		this.code = error.code();
		this.message = error.msg();
		cause = throwable;
	}

	public String getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}

	public Throwable getCause()
	{
		return cause;
	}

}
