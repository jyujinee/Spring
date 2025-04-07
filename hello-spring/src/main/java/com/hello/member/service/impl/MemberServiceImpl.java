package com.hello.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hello.beans.Sha;
import com.hello.exceptions.MemberLoginException;
import com.hello.member.dao.MemberDao;
import com.hello.member.service.MemberService;
import com.hello.member.vo.MemberLoginRequestVO;
import com.hello.member.vo.MemberRegistRequestVO;
import com.hello.member.vo.MembersVO;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private Sha sha;

    @Transactional
	@Override
	public boolean createNewMember(MemberRegistRequestVO memberRegistRequestVO) {
		
		int emailCount = this.memberDao.selectMemberCountBy(memberRegistRequestVO.getEmail());
		
		// 존재하는 이메일인지 확인
		if(emailCount == 1) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다. 다른 이메일을 입력해주세요.");
		}
		
		
		// 사용자가 입력한 비밀번호 암호화를 위해 Salt를 발급 받는다.
		// 암호화 -> 원문이 무엇인지 모르게 한다.
    	// "ABC" -> 암호화 -> "dsalkjas dlkfdsal fklajfs"
    	//                       A        B        C
    	//        Rainbow Sheet		
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

    @Transactional(readOnly = true)
	@Override
	public boolean checkDuplicateEmail(String email) {
		// email이 1이면, 이미 서버에 있는 이메일이다.
		return this.memberDao.selectMemberCountBy(email) == 1;
	}

	@Override
	public MembersVO doLogin(MemberLoginRequestVO memberLoginRequestVO) {
		// 1. 이메일로 회원의 모든 정보를 조회한다.
		MembersVO memberVO = this.memberDao.selectOneMemberBy(memberLoginRequestVO.getEmail());
		
		// 1-1. 회원의 정보가 null이면 사용자에게 예외를 던져버린다.
		//		"비밀번호 또는 이메일을 잘못 입력했습니다. 확인 후 다시 시도해주세요."
		if(memberVO == null) {
//			throw new IllegalArgumentException("비밀번호 또는 이메일을 잘못 입력했습니다. 확인 후 다시 시도해주세요.");
			throw new MemberLoginException(memberLoginRequestVO); // @ControllerAdive에서 모든 예외처리를 한다! 
		}

		// 2. 회원의 정보 중 BLOCK_YN = Y라면 예외를 던져버린다.
		//		"비밀번호가 n회 틀려 계정 접근이 제한되었습니다. 관리자에게 문의하세요."
		if(memberVO.getBlockYn().equals("Y")) {
//			throw new IllegalArgumentException("비밀번호가 " + memberVO.getLoginFailCount()
//										+"회 틀려 계정 접근이 제한되었습니다. 관리자에게 문의하세요.");
			throw new MemberLoginException(memberLoginRequestVO);
		}
		
		// 3. SALT를 이용해 memberLoginRequestVO의 password를 암호화한다.
		String salt = memberVO.getSalt();
		String password = memberLoginRequestVO.getPassword();
		password = sha.getEncrypt(password, salt);
		
		// 4. 회원의 정보 중 password 값이 memberLoginRequestVO의 암호화된 password와 같은지 비교한다.
		if(!memberVO.getPassword().equals(password)) {
			// 5. 만약 다르면 MEMBERS 테이블의 LOGIN_FAIL_COUNT 값을 1 증가시킨다.
			// 	   LATEST_LOGIN_FAIL_DATES는 현재 시간으로 업데이트 시킨다.
			this.memberDao.updateLoginFailCount(memberLoginRequestVO.getEmail());
			
			// 6. 업데이트된 LOGIN_FAIL_COUNT의 값이 5이상이라면 "BLOCK_YN"값을 Y로 업데이트 시킨다.
			this.memberDao.updateBlock(memberLoginRequestVO.getEmail());
			
			// 7. 사용자에게 예외를 던져버린다.
			//	"비밀번호 또는 이메일을 잘못 입력했습니다. 확인 후 다시 시도해주세요."
//			throw new IllegalArgumentException("비밀번호 또는 이메일을 잘못 입력했습니다. 확인 후 다시 시도해주세요.");
			throw new MemberLoginException(memberLoginRequestVO);
			
		}
		else {			
			// 8. 회원의 정보 중 password값이 memberLoginRequestVO의 암호화된 password와 같다면
			//		MEMBERS 테이블의 LOGIN_FAIL_COUNT는 0으로 수정한다.
			// 		LATEST_LOGIN_DATE는 현재 시간으로 업데이트한다.
			//		LATEST_LOGIN_IP은 현재 사용자의 ip로 업데이트한다.
			//		LOGIN_YN = Y로 업데이트한다.
			this.memberDao.updateLoginSuccess(memberLoginRequestVO.getEmail());
		}
		// 9. 조회된 사용자의 정보를 반환시킨다.
		return memberVO;
	}

	// 로그아웃
	@Transactional
	@Override
	public boolean doLogout(String email) {
		return this.memberDao.updateLogoutStatus(email) > 0;
	}

	// 회원삭제
	@Transactional
	@Override
	public boolean doDeleteMe(String email) {
		return this.memberDao.deleteOneMemberBy(email) > 0;
	}
	

}