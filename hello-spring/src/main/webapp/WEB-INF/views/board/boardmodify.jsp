<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 수정 페이지</title>
  </head>
  <link rel="stylesheet" href="/css/common.css" type="text/css" />
  <script src="/js/jquery-3.7.1.min.js" type="text/javascript"></script>
  <script src="/js/common.js" type="text/javascript"></script>
  <body>
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

        <label for="email">이메일</label>
        <input
          type="text"
          id="email"
          name="email"
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
  </body>
</html>
