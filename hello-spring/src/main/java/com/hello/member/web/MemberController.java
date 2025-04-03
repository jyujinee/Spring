package com.hello.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hello.member.service.MemberService;
import com.hello.member.vo.MemberRegistRequestVO;

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
    	this.memberService.createNewMember(memberRegistRequestVO);
    	return "redirect:/member/login";
    }
    
   
    
    

}