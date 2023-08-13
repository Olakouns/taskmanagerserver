package sn.esmt.tasks.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RequestNotAcceptableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4762225915473017668L;
	private String message;

    public RequestNotAcceptableException(String message) {
        super(message);
        this.message = message;
    }

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
