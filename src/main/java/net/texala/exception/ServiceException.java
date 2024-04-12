package net.texala.exception;

public class ServiceException extends BaseException{

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
}