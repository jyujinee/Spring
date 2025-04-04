package com.hello.common.vo;

public class AjaxResponse {
	
	private int status;
	
	// 모든 타입의 데이터를 넣을 수 있기 때문에 타입을 'Object'로 쓴다.
	private Object data;
	
	public AjaxResponse(int status, Object data) {
	
		this.status = status;
		this.data = data;
	}
	
	public AjaxResponse(Object data) {
		// http header 코드를 넣어주기 위해서 200으로 작성한다.
		this(200,data);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
