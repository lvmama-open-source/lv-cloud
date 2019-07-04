package com.lv.cloud.exception;

import org.springframework.beans.BeansException;

public class CommonBeansException extends BeansException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CommonBeansException(String msg) {
		super(msg);
	}

	public CommonBeansException(String msg, Throwable cause) {
		super(msg, cause);
		
	}

}
