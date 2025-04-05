package com.hello.bbs.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class BoardWriteRequestVO {
	
	// 데이터 베이스에 전송할 데이터만 가져온다.
	@NotEmpty(message="제목은 필수 입력값입니다.")
	private String subject;
	
	@NotEmpty(message="내용은 필수 입력값입니다.")
	private String content;

//	@NotEmpty(message="이메일은 필수 입력값입니다.")
//	@Email(message="이메일 형식이 아닙니다.")
	private String email;
	
	// 스프링에서 file의 타입은 MultipartFile이다.
//	private MultipartFile file;
	
	// 파일의 아이디를 미리 발급받아 쿼리에 맵퍼하기 위해 id를 변수로 만든다.
	private int id;
	
	// 첨부파일이 여러개인 경우 List이다.
	private List<MultipartFile> file;
	
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

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
