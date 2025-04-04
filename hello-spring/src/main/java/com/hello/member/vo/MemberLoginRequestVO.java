package com.hello.member.vo;

import jakarta.validation.constraints.NotEmpty;

public class MemberLoginRequestVO {
	
	@NotEmpty(message="이메일을 입력하세요.")
	private String email;

	// 로그인 할 때는 어떻게 입력해야하는지(형식에 대해) 알려주지 않는다 -> 보안 때문이다. 
	@NotEmpty(message="비밀번호를 입력하세요.")
	private String password;

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
