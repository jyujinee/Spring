package com.hello.exceptions;

import com.hello.member.vo.MemberLoginRequestVO;

public class MemberLoginException extends RuntimeException{
	
	/**
	 * 똑같은 예외가 발생했을 때, 서버가 두 대이상이면 같은 예외처리를 하기 위해 복제한다.(Clustering)
	 * Clustering에 필요하기 때문에 serialVersionID를 부여한다.
	 */
	private static final long serialVersionUID = -8044052751611282859L;
	// 예외를 던질때 doLogin()의 catch 구문을 없앤다 -> 대신 여기서 처리함
	private MemberLoginRequestVO memberLoginRequestVO;

	public MemberLoginException(MemberLoginRequestVO memberLoginRequestVO) {
		super("비밀번호 또는 이메일을 잘못 작성했습니다. 확인 후 다시 시도해주세요.");
		this.memberLoginRequestVO = memberLoginRequestVO;
	}
	
	public MemberLoginException(MemberLoginRequestVO memberLoginRequestVO, int failCount) {
		super("비밀번호가 " + failCount + "차례 틀려 계정 접근이 제한되었습니다. 관리자에게 문의하세요.");
		this.memberLoginRequestVO = memberLoginRequestVO;
	}

	public MemberLoginRequestVO getMemberLoginRequestVO() {
		return memberLoginRequestVO;
	}	

}
