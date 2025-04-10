<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>


<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

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
        
        <%-- 회원일뿐만 아니라, 내가 쓴 게시글이면 버튼이 보여야한다. --%>
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
      
	    <ul class="reply-list-wrapper" data-id="${boardVO.id}">
	      
	    </ul>
	    
	    <div class="reply-writer-wrapper" data-id="${boardVO.id}" 
	                               data-endpoint="" data-reply-id="">
	       <textarea class="reply-content" placeholder="댓글을 입력해보세요!"></textarea>
	       <button class="reply-writer-button" type="button">등록</button>
	    </div>
	    
	    
	    <template class="reply-item-template">
	     <li>
               <div class="reply-item-writer">
                   <span></span>
                   <span></span>
                   <span></span>
               </div>
               
               <div class="reply-item-content">
                   
               </div>
               <div class="reply-item-actions">
                   <span class="reply-item-modify">수정</span>
                   <span class="reply-item-delete">삭제</span>
                   <span class="reply-item-recommend">추천</span>
                   <span class="reply-item-recommend-count"></span>
                   <span class="reply-item-write">답글 달기</span>
               </div>
           </li>
	    </template>
	  

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
