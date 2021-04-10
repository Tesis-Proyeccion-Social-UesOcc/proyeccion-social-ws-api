package ues.occ.proyeccion.social.ws.app.dao;

import java.io.Serializable;

public class ServiceResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	public static final String CODE_OK = "00";
	public static final String MESSAGE_OK = "Request Successful";
	public static final String MESSAGE_NULL = "Request Successful, Data not found";
	
	public static final String CODE_OK_CREATED = "01";
	public static final String MESSAGE_CREATED = "Request Successful, Object created and stored in the database";
	
	public static final String CODE_FATAL = "99";
	public static final String MESSAGE_FATAL = "Request failed";
	
	public static final String CODE_FAIL_STORAGE_DOCUMENT_BUCKET = "98";
	public static final String MESSAGE_FAIL_STORAGE_DOCUMENT_BUCKET = "Problems to storage the file into the bucket";
	
	private String code;
	private String message;
	private Object result;
	
	public ServiceResponse() {
		super();
	}
	
	public ServiceResponse(String code, String message, Object result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
