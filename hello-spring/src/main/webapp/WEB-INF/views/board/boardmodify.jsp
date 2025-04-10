<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<jsp:include page="/WEB-INF/views/layout/header.jsp"/>


    <h1>게시글 수정</h1>
    <form class="modify-form">
      <input type="hidden" name="id" value="${boardVO.id}" />

      <div class="grid">
        <label for="subject">제목</label>
        <input
          type="text"
          id="subject"
          name="subject"
          value="${boardVO.email}"
          required
        />

        <label for="content">내용</label>
        <textarea id="content" name="content" required>
${boardVO.content}</textarea
        >

        <div class="btn-group">
          <div class="right-align">
            <button type="button" class="modify-save">저장</button>
          </div>
        </div>
      </div>
    </form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>  