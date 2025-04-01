package com.hello.bbs.vo;

public class BoardWriteRequestVO {
	// 데이터 베이스에 전송할 데이터만 가져온다.
	private String subject;
	private String content;
	private String email;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
