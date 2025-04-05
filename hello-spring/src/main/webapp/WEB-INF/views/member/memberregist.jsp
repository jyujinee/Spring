<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>회원 등록 페이지</title>
      <link href="/css/common.css" rel="stylesheet" type="text/css">
      <script src="/js/jquery-3.7.1.min.js" type="text/javascript"></script>
      <script src="/js/common.js" type="text/javascript"></script>
   </head>
   <body>
      <jsp:include page="/WEB-INF/views/member/loginstatus.jsp"/> 
   
      <div class="member-regist-wrapper">
         <h1>회원가입</h1>
         <form:form modelAttribute="memberRegistRequestVO" class="member-regist-form">
            <div class="grid">
               <label for="email">이메일</label>
               <div>
                  <input type="email" name="email" id="email" value="${userInputRegist.email}" required/>
                  <c:if test="${not empty emailErrorMessage}">
                     <div class="error emailDuplicateError">${emailErrorMessage}</div>
                  </c:if>
                  <form:errors path="email" element="div" cssClass="error" />
               </div>
               
               <label for="name">이름</label>
               <div>
                  <input type="text" name="name" id="name" value="${userInputRegist.name}" required />
                  <form:errors path="name" element="div" cssClass="error" />
               </div>
               
               <label for="password">비밀번호</label>
               <div>
                  <input type="password" name="password" id="password" required />
                  <c:if test="${not empty errorMessage}">
                     <div class="error passwordEqualError">${errorMessage}</div>
                  </c:if>
                  <form:errors path="password" element="div" cssClass="error passwordPatternError" />
               </div>
               
               <label for="confirmPassword">비밀번호 재입력</label>
               <div>
                  <input type="password" name="confirmPassword" id="confirmPassword" required />
                  <c:if test="${not empty errorMessage}">
                     <div class="error passwordEqualError">${errorMessage}</div>
                  </c:if>
                  <form:errors path="confirmPassword" element="div" cssClass="error passwordPatternError" />
               </div>
               
               <div class="btn-group">
                  <div class="right-align">
				  
                     <button type="submit" class="regist-button" disabled>등록</button>
                     <button type="button" class="cancel-button">취소(뒤로가기)</button>
                  </div>
               </div>
            </div>
         </form:form>
      </div>
   </body>
</html>