<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
  </head>
  <link rel="stylesheet" href="/common.css" type="text/css" />
  <script src="/jquery-3.7.1.min.js" type="text/javascript"></script>
  <script src="/common.js" type="text/javascript"></script>
  <body>
    <h1>게시글 작성</h1>
    <form class="write-form">
      <div class="grid">
        <label for="subject">제목</label>
        <input type="text" id="subject" name="subject" required />

        <label for="email">이메일</label>
        <input type="text" id="email" name="email" required />

        <label for="content">내용</label>
        <textarea id="content" name="content" required></textarea>

        <div class="btn-group">
          <div class="right-align">
            <button type="button" class="write-save">저장</button>
          </div>
        </div>
      </div>
    </form>
  </body>
</html>
