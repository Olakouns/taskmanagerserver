package sn.esmt.tasks.taskmanager.dto.converters;

public class ApiResponse {

	private Boolean success;
    private String message;
    private int code = 200;
    private Object data;
    private ResponseType responseType;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, ResponseType responseType) {
		super();
		this.success = success;
		this.responseType = responseType;
	}

	public ApiResponse(Boolean success, String message, int code, Object data) {
		this.success = success;
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public ApiResponse(Boolean success, int code, Object data) {
		this.success = success;
		this.code = code;
		this.data = data;
	}

	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
	 * @return the responseType
	 */
	public ResponseType getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType the responseType to set
	 */
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public enum ResponseType{
    	USERNAME_EXISTS,
    	EMAIL_EXISTS,
    	ACCOUNT_IS_WAITING_TO_ACTIVE,
    	CODE_ERROR
    }
    
}
