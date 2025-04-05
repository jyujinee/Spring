<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <link rel="stylesheet" href="/css/common.css" type="text/css" />
<div class="login-status-wrapper">
    <ul>
        <c:choose>
            <%-- 세션값이 비어있으면 로그인을 안했다는 의미 --%>
            <c:when test="${empty sessionScope.__LOGIN_USER__}">
                <li>
	                <a href="/member/regist">회원가입</a>                
                </li>
                <li>
	                <a href="/member/login">로그인</a>
                </li>
            </c:when>

            <c:otherwise>
                <%--세션에 넣은 데이터가 MembersVO memberVO 이므로, memberVO의 name과 email을 보여준다. --%>
                <li>
                    <a href="/member/mypage">${sessionScope.__LOGIN_USER__.name}</a>
                    (${sessionScope.__LOGIN_USER__.email})
                </li>
                <li>
                    <a href="/member/logout">로그아웃</a>
                </li>
            </c:otherwise>
        </c:choose>
        
    </ul>
</div>
