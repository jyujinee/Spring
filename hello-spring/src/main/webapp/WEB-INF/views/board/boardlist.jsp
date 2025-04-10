<!-- page directive -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>


<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

    <!-- 이 페이지의 end-point : http://localhost:8080/board/list 
     http:// <--- Protocol
     localhost <--- Domain
     :8080 <--- Port
     /board/list <--- Path
     ?n=n <--- query string parameter 로 URL은 구성되어있다.

     http://localhost:8080 <--- host 
     현재 페이지의 host와 불려오려는 파일의 host가 같으면, 
     <link href>에서 host는 생략할 수 있다.
     <link rel="stylesheet" href="http://localhost:8080/common.css"> -->
     

<!--    <h1>게시글 목록</h1>
    <p>게시글의 수: ${boardList.boardCnt}</p>
    <p>조회한 게시글 목록의 수: ${boardList.getBoardList().size()}</p> -->

    <%-- Controller Endpoint를 해당 위치에 보여주는 방법
    1. c import --%>
    <%-- <c:import url="/member/loginstatus"/> --%>
    <%--2.  jsp를 보여주기
    <jsp:include page="/WEB-INF/views/member/loginstatus.jsp"/>  --%>
    
    <div class="search-area">
        <label for="writer-name">작성자</label>
        <input type="text" id="writer-name" value="${pagination.writerName}"/>
        
        <label for="writer-email">이메일</label>
        <input type="text" id="writer-email" value="${pagination.writerEmail}"/>
        
        <label for="subject">제목</label>
        <input type="text" id="subject" value="${pagination.subject}"/>
        
        <label for="content">내용</label>
        <input type="text" id="content" value="${pagination.content}"/>
        <%-- 이벤트를 주기 위해 클래스가 두개 board-search-button --%>
        <button type="button" class="search-button board-search-button">검색</button>
    </div>
    
    <div class="right-align">총 ${boardList.boardCnt}건의 게시글이 검색되었습니다.</div>

    <table class="grid">
        <colgroup>
            <col width="80" />
            <col />
            <col width="250" />
            <col width="80" />
            <col width="120" />
            <col width="120" />
        </colgroup>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>이름</th>
                <th>조회수</th>
                <th>등록일</th>
                <th>수정일</th>
            </tr>
            
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty boardList.boardList}">
                <c:forEach items="${boardList.boardList}" var="board">
                    <tr>
		                <td class="center-align">${board.id}</td>
		                <td>
		                    <a href="/board/view/${board.id}">${board.subject}</a>
		                </td>    
		                <td>${board.memberVO.name}</td>
		                <td class="center-align">${board.viewCnt}</td>
		                <td class="center-align">${board.crtDt}</td>
		                <td class="center-align">${board.mdfyDt}</td>
                    </tr>
                 </c:forEach>
            </c:when>
            <c:otherwise>
                <tr class="empty">
                    <td colspan="6" class="empty">게시글이 비어있습니다. 첫 게시글의 주인공이 되어보세요!</td>
                </tr>                 
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    
    
    <ul class="paginator bord-paginator">
        <c:if test="${pagination.hasNextGroup}">
            <li data-page-no="0">
                <a href="javascript:void(0);">처음</a>
            </li>
            
            
            <li data-page-no="${pagination.prevGroupStartPageNo}">
                <a href="javascript:void(0);">이전</a>
            </li>
        </c:if>
    
        <c:forEach begin="${pagination.groupStartPageNo}"
                   end="${pagination.groupEndPageNo}"
                   step="1"
                   var="p">
            <li data-page-no="${p}" class="${pagination.pageNo == p ? 'active' : ''}">
                <a href="javascript:void(0);">
                    ${p+1}
                </a>
            </li>
                   
        </c:forEach>
        
        <c:if test="${pagination.hasPrevGroup}">
            <li data-page-no="${pagination.nextGroupStartPageNo}">
                <a href="javascript:void(0);">다음</a>
            </li>
            
            <li data-page-no="${pagination.pageCount - 1}">
                <a href="javascript:void(0);">마지막</a>
            </li>
        </c:if>
        
        <li>
            <!-- listSize 노출할 게시글 수 선택하기 -->
		    <select id="list-Size">
		       <option value="10" ${10 == pagination.listSize ? 'selected' : ''}>10</option>
		       <option value="20" ${20 == pagination.listSize ? 'selected' : ''}>20</option>
		       <option value="30" ${30 == pagination.listSize ? 'selected' : ''}>30</option>
		       <option value="50" ${50 == pagination.listSize ? 'selected' : ''}>50</option>
		       <option value="100" ${100 == pagination.listSize ? 'selected' : ''}>100</option>
		    </select>
        </li>
        
    </ul>
    
    
    
    <c:if test="${not empty sessionScope.__LOGIN_USER__}">
        <a href="/board/write">게시글 등록하기</a>
    </c:if>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

