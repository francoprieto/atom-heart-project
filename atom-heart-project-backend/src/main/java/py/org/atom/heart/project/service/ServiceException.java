package py.org.atom.heart.project.service;

public class ServiceException extends Exception{

	private static final long serialVersionUID = -2855395103272026137L;

	public ServiceException(Exception message) {
		super(message + "");
	}	
	
	public ServiceException(String message) {
		super(message);
	}
	
}
