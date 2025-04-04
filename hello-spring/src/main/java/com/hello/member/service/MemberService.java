package com.hello.member.service;

import com.hello.member.vo.MemberLoginRequestVO;
import com.hello.member.vo.MemberRegistRequestVO;
import com.hello.member.vo.MembersVO;

public interface MemberService {
	
	// 회원 등록
	public boolean createNewMember(MemberRegistRequestVO memberRegistRequestVO);
	
	// 이메일 중복 검사
	public boolean checkDuplicateEmail(String email);
	
	// 타입이 불린이 아닌건 컨트롤에서 설명
	public MembersVO doLogin(MemberLoginRequestVO memberLoginRequestVO);

}