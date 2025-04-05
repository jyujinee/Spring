<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 조회 페이지</title>
    <link rel="stylesheet" href="/common.css" type="text/css" />
    <script src="/jquery-3.7.1.min.js" type="text/javascript"></script>
    <script src="/common.js" type="text/javascript"></script>
  </head>
  <body>
  <jsp:include page="/WEB-INF/views/member/loginstatus.jsp"/> 
  
    <h1>게시글 조회</h1>
    <div class="detail-wrapper">
      <div class="grid">
        <label>제목</label>
        <div>${boardVO.subject}</div>

        <label>이름</label>
        <div>${boardVO.memberVO.name}</div>

        <label>첨부파일</label>
        <div>
          <a href="/file/${boardVO.id}/${boardVO.fileList[0].flId}">
            ${boardVO.fileList[0].flNm}
          </a>
        </div>

        <label>조회수</label>
        <div>${boardVO.viewCnt}</div>

        <label>등록일</label>
        <div>${boardVO.crtDt}</div>

        <label>수정일</label>
        <div>${boardVO.mdfyDt}</div>

        <label>내용</label>
        <div>${boardVO.content}</div>
        
        <!-- 회원일뿐만 아니라, 내가 쓴 게시글이면 버튼이 보여야한다. -->
        <c:if test="${not empty sessionScope.__LOGIN_USER__ and 
                        boardVO.email == sessionScope.__LOGIN_USER__.email}">
	        <div class="btn-group">
	          <div class="right-align">
	            <a href="/board/modify/${boardVO.id}">수정</a>
	            <a href="/board/delete/${boardVO.id}">삭제</a>
	          </div>
	        </div>
	    </c:if>
	    
      </div>
    </div>
  </body>
</html>
