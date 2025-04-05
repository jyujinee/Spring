<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
<head>
      <meta charset="UTF-8">
      <title>My Page</title>
      <link href="/css/common.css" rel="stylesheet" type="text/css">
      <script src="/js/jquery-3.7.1.min.js" type="text/javascript"></script>
      <script src="/js/common.js" type="text/javascript"></script>
   
</head>
<body>
    
    <div class="my-page-wrapper">
	<jsp:include page="/WEB-INF/views/member/loginstatus.jsp"/> 
	    
	    <div class="my-page-content-wrapper">
	       <ul class="aside-menu">
	           <li>개인정보 수정</li>
	           <li>비밀번호 수정</li>
	           <li>내가 작성한 글 보기</li>
	           <li>내가 작성한 댓글 보기</li>
	           <li>내가 좋아요한 글 보기</li>
	           <li>내가 좋아요한 댓글 보기</li>
	           <li>북마크</li>
	           <li class="delete-me">
	               <a href="/member/delete-me">탈퇴</a>
	           </li>
	       
	       </ul>
	       
	       <div class="content">
	           <div>이메일</div>
	           <div>${loginUser.email}</div>
	           
	           <div>이름</div>
               <div>${loginUser.name}</div>
               
               <div>가입날짜</div>
               <div>${loginUser.joinDate}</div>
               
               <div>최근 접속한 IP</div>
               <div>${loginUser.latestLoginIp}</div>
               
               
               <div>비밀번호 변경일</div>
               <div>${loginUser.latestPasswordChangeDate}</div>
	       </div>
	       
	    </div>
    </div>

</body>
</html>