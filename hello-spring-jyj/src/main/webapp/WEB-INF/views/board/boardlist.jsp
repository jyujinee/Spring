<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 조회하기</title>
</head>
<body>

게시글 수 : ${boardList.getBoardCnt()}<br/>
조회한 게시글의 수: ${boardList.getBoardList().size()}

</body>
</html>