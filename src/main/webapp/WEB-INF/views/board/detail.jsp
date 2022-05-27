<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

	<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}"> <!--로그인 인증 요청한 id일때 생성  -->
	<a href="/board/${board.id}/updateForm" class="btn btn-warning" id="btn-update">수정</a>
	<button class="btn btn-danger" id="btn-delete">삭제</button>
	</c:if>
	<br/><br/> 
	<div>
		글 번호 :<span id="id"><i>${board.id} </i></span>
		작성자 :<span><i>${board.user.username} </i></span>
	</div>
	<br/> 
		<div class="form-group">
			<label for="title">Title</label>
			<h3>${board.title }</h3>
		</div>
		
		
		<div>
  			<hr /> <!-- 선 긋기 -->
 				 <div> 
 				 ${board.content }
 				 </div>
		</div>
		<hr />

	</div>
	
<script src="/js/board.js"></script>	
<%@ include file="../layout/footer.jsp" %>



