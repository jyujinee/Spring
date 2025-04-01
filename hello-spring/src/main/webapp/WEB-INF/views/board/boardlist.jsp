<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <!-- 이 페이지의 end-point : http://localhost:8080/board/list 
     http:// <--- Protocol
     localhost <--- Domain
     :8080 <--- Port
     /board/list <--- Path
     ?n=n <--- query string parameter 로 URL은 구성되어있다.

     http://localhost:8080 <--- host 
     현재 페이지의 host와 불려오려는 파일의 host가 같으면, 
     <link href>에서 host는 생략할 수 있다.
     
     -->
    <!-- <link rel="stylesheet" href="http://localhost:8080/common.css"> -->
    <link rel="stylesheet" href="/common.css" type="text/css" />
  </head>
  <body>
    <h1>게시글 목록</h1>
    <p>게시글의 수: ${boardList.boardCnt}</p>
    <p>조회한 게시글 목록의 수: ${boardList.getBoardList().size()}</p>

    <a href="/board/write">게시글 등록하기</a>
  </body>
</html>
