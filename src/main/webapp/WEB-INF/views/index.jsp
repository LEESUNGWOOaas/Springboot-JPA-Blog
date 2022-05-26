<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp" %>

	<div class="container">

	<c:forEach var="board" items="${boards.content}"><!-- 페이징 시 content를 적어줘야한다 . -->
	
		<div class="card m-2">
			<!-- <img class="card-img-top" src="img_avatar1.png" alt="Card image"> -->
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>
	
<ul class="pagination justify-content-center">
	<!-- 처음일때 비활성화 -->
	<c:choose>
		<c:when test="${boards.first}">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>	
		</c:otherwise>
	</c:choose>
  
  
  <!-- 페이지 끝일때 비활성화 -->
  <c:choose>
		<c:when test="${boards.last}">
			 <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
		</c:when>
		<c:otherwise>
			 <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
		</c:otherwise>
	</c:choose>
 
</ul>

	
<%@ include file="layout/footer.jsp" %>



