package com.hello.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.beans.Sha;
import com.hello.member.dao.MemberDao;
import com.hello.member.service.MemberService;
import com.hello.member.vo.MemberRegistRequestVO;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private Sha sha;

	@Override
	public boolean createNewMember(MemberRegistRequestVO memberRegistRequestVO) {
		// 사용자가 입력한 비밀번호 암호화를 위해 Salt를 발급 받는다.
		// 암호화: 원문이 무엇인지 모르게 한다.
		// 원문 "ABC" -> 암호화 => "fdjfdk" "dfjd" "dfkjd"
		//						A		B		C
		// Rainbow Sheet라고 한다. => Salt를 이용해 무력화 시킨다.
		// Salt 값은 난수이며, 발급할때 마다 다른 문자가 나온다.
		// "ABC" + "1231545" => "123A154B5C" => 암호화 => "fhjdfkhgd545asdsaSAD"
		// 발급받은 Salt를 이용해 SHA 알고리즘으로 암호화를 진행한다. (복호화 불가능 암호화)
		// 별도의 Salt를 관리하는 테이블이 필요하다.
		
		String salt = this.sha.generateSalt();
		String password = memberRegistRequestVO.getPassword();
		password = this.sha.getEncrypt(password, salt);

		// Salt값과 암호화된 비밀번호를 memberRegistRequestVO에 알맞게 할당한다.
		memberRegistRequestVO.setPassword(password);
		memberRegistRequestVO.setSalt(salt);
		
		// 데이터 베이스에 저장한다.
		return this.memberDao.insertNewMember(memberRegistRequestVO) > 0;
	}

}