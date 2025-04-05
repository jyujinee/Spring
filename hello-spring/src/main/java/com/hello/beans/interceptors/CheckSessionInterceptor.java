package com.hello.beans.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import com.hello.member.vo.MembersVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class CheckSessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 세션의 여부를 확인한다.
		HttpSession session = request.getSession();
		MembersVO memberVO = (MembersVO)session.getAttribute("__LOGIN_USER__");
		
		// 세션이 없다면 로그인 페이지를 노출시킨다.
		if( memberVO == null) {
			// 스프링이 아닌 HttpServlet에서 가져온다. -> View Resolver가 없기 때문에 모든 경로를 작성해야한다.
			String path = "/WEB-INF/views/member/memberlogin.jsp";
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
			
			// 페이지 보여주기
			// forward: URL은 변경되지 않지만, 화면은 바뀌는 응답 상태
			// [Spring] forward:/member/login
			requestDispatcher.forward(request, response);
			return false;
		}
		// 동시에 컨틀로러 실행은 못하게 한다.
		
		return true;
	}
}
