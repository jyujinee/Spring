package com.hello.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hello.common.vo.AjaxResponse;
import com.hello.member.service.MemberService;
import com.hello.member.vo.MemberLoginRequestVO;
import com.hello.member.vo.MemberRegistRequestVO;
import com.hello.member.vo.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @GetMapping("/member/regist")
    public String viewMemberRegistPage() {
    	return "member/memberregist";
    }
    
    @PostMapping("/member/regist")								 
    public String doMemberRegist(@Valid @ModelAttribute MemberRegistRequestVO memberRegistRequestVO,
    							BindingResult bindingResult,
    							Model model) {
    	
    	// 파라미터 유효성 검사
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("userInputRegist", memberRegistRequestVO);
    		return "member/memberregist";
    	}
    	
    	// 비밀번호와 비밀번호 재입력이 같은지 확인
    	String password = memberRegistRequestVO.getPassword();
    	String confirmPassword = memberRegistRequestVO.getConfirmPassword();
    	
    	if(! password.equals(confirmPassword)) {
    		model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
    		model.addAttribute("userInputRegist", memberRegistRequestVO);
    		return "member/memberregist";
    	}
    	// 이상이 없으면 Service 호출
    	try {
    		// 이미 있는 이메일인 경우 IllegalExpetion이 발생함.
    		this.memberService.createNewMember(memberRegistRequestVO);
    	}
    	catch(IllegalArgumentException iae) {
    		// 이미 존재하는 이메일
    		model.addAttribute("emailErrorMessage", iae.getMessage());
    		model.addAttribute("userInputRegist", memberRegistRequestVO);
    		return "member/memberregist"; 
    	}
    	return "redirect:/member/login";
    }
    
    // 사용할 수 있는 이메일인지 확인한다.
    // url 특성상 .이 포함되면 잘리기 때문에 ?로 대체한다
    // http://localhost:8080/member/available?email=test01@naver.com
    @ResponseBody // 응답 데이터의 반환타입을 JSON으로 바꿔준다.
    @GetMapping("/member/available")
    public AjaxResponse checkAvailableEmail(@RequestParam String email) {
    	
    	boolean duplicated = this.memberService.checkDuplicateEmail(email);
    	Map<String, Object> resultMap = new HashMap<>();
    	
    	resultMap.put("available", !duplicated);
    	
    	return new AjaxResponse(resultMap);
    }
    
    
    @GetMapping("/member/login")
    public String viewLoginPage() {
    	return "member/memberlogin";
    }
    
    @PostMapping("/member/login")
    public String doLogin(@Valid @ModelAttribute MemberLoginRequestVO memberLoginRequestVO
    						, BindingResult bindingResult 
    						, @RequestParam String nextUrl
    						, Model model
    						, HttpSession session // 세션을 생성
    						, HttpServletRequest request) { // 브라우저가 요청한 모든 데이터가 들어감(새로운 세션 발급) 
    	
		// 사용자의 IP를 가져올때 HttpServeltRequest가 사용.
			String userIp = request.getRemoteAddr();
			System.out.println("IP: " + userIp);
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("userInput", memberLoginRequestVO);
    		return "member/memberlogin";
    	}
    	try {
    		MembersVO memberVO = this.memberService.doLogin(memberLoginRequestVO);
    		// 사이트에 접속했을 때 발급받은 세션은 폐기시킨다. (로그아웃)
    		session.invalidate();
    		
    		// 새로운 세션을 발급받는다.
    		session = request.getSession(true);
    		
    		// 서버가 세션에 회원 정보를 기억(기록)한다.
    		// 해당 사용자의 고유한 세션 아이디를 브라우저에게 쿠키를 보내준다.
    		session.setAttribute("__LOGIN_USER__", memberVO);
    	}
    	catch(IllegalArgumentException iae) {
			model.addAttribute("userInput", memberLoginRequestVO);
    		model.addAttribute("errorMessage", iae.getMessage());
    		return "member/memberlogin";
    	}
    	// 에러가 안나면, 로그인 성공
    	return "redirect:" + nextUrl;
    }
    
    @GetMapping("/member/logout")
    public String doLogout(HttpSession session) {
    	// 회원의 정보를 Session에서 가져온다. (__LOGIN_USER__를 이용해 MembersVO를 가져옴)
    	// session.getAttribute는 Object 타입을 반환시킨다. -> 타입 캐스팅이 필요함.
    	MembersVO memberVO = (MembersVO)session.getAttribute("__LOGIN_USER__");
    	// MembersVO 에서 email 추출
    	this.memberService.doLogout(memberVO.getEmail());
    	
    	// 세션 폐기 = 로그아웃
    	session.invalidate();
    	
    	return "redirect:/member/login";
    }
    
    @GetMapping("/member/mypage")
    public String viewMyPage(@SessionAttribute("__LOGIN_USER__") MembersVO memberVO,
    						Model model) {
    	// 세션을 이용해서 DB에 접근할 필요가 없다.
    	model.addAttribute("loginUser", memberVO);
    	
    	return "member/mypage";
    }
    
    @GetMapping("/member/delete-me")
    public String doDeleteMe(@SessionAttribute("__LOGIN_USER__") MembersVO memberVO,
    						HttpSession session) {
    	boolean success = this.memberService.doDeleteMe(memberVO.getEmail());
    	
    	if(success) {
    		session.invalidate(); // 로그아웃, 회원삭제도 세션 폐기
    		return "redirect:/member/success-delete-me";
    	}
    	return "redirect:/member/fail-delete-me";
    }
    
    @GetMapping("/member/{result}-delete-me")
    public String viewDeleteResultPage(@PathVariable String result) {
    	
    	if( !result.toLowerCase().equals("success") && !result.toLowerCase().equals("fail")) {
    		return "error/404";
    	}
    	return "member/"+ result + "deleteme";
    }
    
   
    
    

}