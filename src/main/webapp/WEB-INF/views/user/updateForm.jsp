<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
	<input type="hidden" id="id" value="${principal.user.id}"/>
		<div class="form-group">
				<!-- value를 넣어두면 변경 불가능  -->
			<label for="username">Username</label> <input type="email" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
		<c:if test="${empty principal.user.oauth }"> <!-- oauth값이 있으면 사라지게한다. -->
		<div class="form-group">
			<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
	</c:if>
		<div class="form-group">
			<label for="email">Email</label> <input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email" readonly>
		</div>
		
		
<!-- 		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox"> Remember me</label>
		</div> -->
	</form>
		<button id="btn-update" class="btn btn-primary">저장</button>

</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>



