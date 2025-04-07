package com.hello.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	// @ExceptionHandler: MemberLoginException 예외가 발생하면 다음 메소드viewMemberLoginExceptionPage를 실행한다.
	@ExceptionHandler(MemberLoginException.class)
	public String viewMemberLoginExceptionPage(MemberLoginException mle
												, Model model) {
		
		LOGGER.error(mle.getMessage(), mle);
		model.addAttribute("userInput", mle.getMemberLoginRequestVO());
		model.addAttribute("errorMessage", mle.getMessage());
		
		return "member/memberlogin";
	}
	
	@ExceptionHandler(MemberRegistException.class)
	public String viewMemberRegistExceptionPage(MemberRegistException mre
												, Model model) {
		
		LOGGER.error(mre.getMessage(), mre);
		model.addAttribute("userInputRegist", mre.getMemberRegistRequestVO());
		model.addAttribute("emailErrorMessage", mre.getMessage());
		
		return "member/memberregist";
	}
	
	@ExceptionHandler(PageNotFoundException.class)
	public String viewPageNotFoundExceptionPage(PageNotFoundException pnfe
												, Model model) {
		LOGGER.error(pnfe.getMessage(), pnfe);
		model.addAttribute("cause", pnfe.getMessage());
		
		return "error/404";
	}
	
	@ExceptionHandler(NotExistsException.class)
	public String viewNotExistsExceptionPage(NotExistsException nee
											, Model model) {
		LOGGER.error(nee.getMessage(), nee);
		model.addAttribute("cause", nee.getMessage());
		
		return "error/404";
	}
	
	// 없는 URL을 요청할 경우 발생하는 예외: NoHandlerFoundException (Exception 상속)
	// RuntimeException이 아닌 Exception 예외 처리한다.
	@ExceptionHandler(NoHandlerFoundException.class)
	public String viewNoHandlerFoundExceptionPage() {
		return "error/404";
	}
	
	// RuntimeException은 가장 밑에 위치해야한다. (위의 에러들의 처리를 다 해버린다.)
	// 만들지 않은 다른 모든 예외에 대한 예외처리
	@ExceptionHandler(RuntimeException.class)
	public String viewOtherExceptionPage(RuntimeException re) {
		
		LOGGER.error(re.getMessage(), re);
		
		// 서버 에러
		return "error/500";
	}
	
	
}
