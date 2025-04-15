<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

    <h1>게시글 작성</h1>
    
    <!-- 폼에 파일이 포함되면 폼 태그에 enctype을 작성해줘야한다. -->
    <!-- enctype="multipart/form-data" 을 반드시 작성해야 서버에 데이터 전송 가능 -->
    <form:form modelAttribute="boardWriteRequestVO" class="write-form" 
               enctype="multipart/form-data">
               
      <div class="grid">
      <label for="subject">제목</label>
      <div>     
	        <input type="text" id="subject" name="subject" value="${userWriteBoard.subject}" required />
		    <!-- 에러의 path는 <input>의 name을 넣는다. -->
	        <form:errors path="subject" element="div" cssClass="error"/>
      </div>
      
 
	    <label for="file">첨부파일</label>
        <div>
          <!-- input 타입 파일은 value 속성이 없다.
            사용자가 첨부한 파일 정보를 작성해줄 수 없다. -->
          <!-- 같은 name으로 여러개면 Collection이므로 자바에서는 list가 됨 -->
          <input type="file" id="file1" name="file" />
          <input type="file" id="file2" name="file" />
          <input type="file" id="file3" name="file" />
        </div>

	    <label for="content">내용</label>
        <div>
	        <textarea id="content" name="content" required>${userWriteBoard.content}</textarea>
	        <form:errors path="content" element="div" cssClass="error"/>
        </div>
        
        
        <div class="btn-group">
          <div class="right-align">
            <button type="button" class="write-save">저장</button>
          </div>
        </div>
        
      </div>
    </form:form>

    
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>