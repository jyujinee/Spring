package com.hello.beans.interceptors;

import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.google.gson.Gson;
import com.hello.common.vo.AjaxResponse;
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
			
			// TO-DO: ajax call 일때는 "Access Denied" 메시지를 브라우저에게 보내도록 하기!
			/* 현직 개발자들에게도 머리 아픈 문제
			* 해결하려면 
			*		FULL Ajax로 만들거나
			*		FULL Template로 만든다.
			*
			* Template + Ajax를 동시에 사용하면, 처리하는 기준자체가 모호하다.
			* URL에 Ajax 임을 알려주는 티를 낸다. (명시한다.)
			* 						/reply/59 => /ajax/reply/59 
			*/
			
			// 사용자가 요청한 URL에서 Path(URL에서 host를 제외한 뒷부분)만 가져온다.
			String requestPath = request.getRequestURI();
			
			if(requestPath.startsWith("/ajax")) {
				// HttpStatus.UNAUTHORIZED: 인증되지 않음
				AjaxResponse ajaxResponse = 
						new AjaxResponse(HttpStatus.UNAUTHORIZED.value(), "Access Denied");
				
				
				// ajaxResponse를 JSON으로 변환한다. => ResponseBody로 쓸 수 없어서 직접 구현함
				// 객체 -> JSON, JSON -> 객체를 변환시키는 유틸리티를 Google에서 만들어서 배포하는 중이다!
				// GSON Library (pom.xml에 라이브러리 추가하기)
				Gson gson = new Gson();
				String json = gson.toJson(ajaxResponse);
				
				// json 데이터를 브라우저(클라이언트)에게 보내준다.
				response.setContentType("applicatoin/json");
				response.setContentLengthLong(json.length());
				
				// 응답 데이터(텍스트) 작성하기
				PrintWriter printWriter = response.getWriter();
				printWriter.append(json);
				printWriter.flush();
				return false;
			}
			
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
