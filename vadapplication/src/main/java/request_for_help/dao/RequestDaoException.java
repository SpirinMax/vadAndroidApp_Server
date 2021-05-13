package request_for_help.dao;

public class RequestDaoException extends Exception {

	private int errorType;

	public RequestDaoException(String message, int errorType) {
		super(message);
		this.errorType = errorType;
	}

	public int getErrorType() {
		return errorType;
	}

}
