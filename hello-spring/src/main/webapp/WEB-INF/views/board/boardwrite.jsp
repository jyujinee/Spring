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
    <!-- 폼에 파일이 포함되면 폼 태그에 enctype을 작성해줘야한다. -->
    <!-- enctype="multipart/form-data" 을 작성해야 데이터 전송 가능 -->
    <form class="write-form" enctype="multipart/form-data">
      <div class="grid">
        <label for="subject">제목</label>
        <input type="text" id="subject" name="subject" required />

        <label for="email">이메일</label>
        <input type="text" id="email" name="email" required />

        <label for="file">첨부파일</label>
        <div>
          <!-- 같은 name으로 여러개면 Collection이므로 자바에서는 list가 됨 -->
          <input type="file" id="file1" name="file" />
          <input type="file" id="file2" name="file" />
          <input type="file" id="file3" name="file" />
        </div>

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
