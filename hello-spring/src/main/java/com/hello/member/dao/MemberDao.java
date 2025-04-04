package com.hello.member.dao;

import com.hello.member.vo.MemberRegistRequestVO;
import com.hello.member.vo.MembersVO;

public interface MemberDao {
	
	// 새로운 회원 등록
	public int insertNewMember(MemberRegistRequestVO memberRegistRequestVO);
	
	// 이메일로 회원의 수를 카운트
	public int selectMemberCountBy(String email);
	
	// 회원 정보 조회 -> 이메일로 모든 정보를 조회(로그인)
	public MembersVO selectOneMemberBy(String email);

	// update 된 횟수니깐 int로 만든다.
	public int updateLoginFailCount(String email);

	public int updateBlock(String email);

	public int updateLoginSuccess(String email);


}