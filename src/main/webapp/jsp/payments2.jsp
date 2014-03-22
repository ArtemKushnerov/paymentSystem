<%@ include file="header.jsp"%>

<div class="container">
	<p>
		<fmt:message key="select_service" />:
	</p>
	<form id="pStartForm" action="Controller" method="post">
		<input type="hidden" name="command" value="startPayment"> 
		<select	name="Service">
			<c:forEach var="service" items="${services}">
				<option title="${service.name}" value="${service.id}">${service.name}</option>
			</c:forEach>
		</select> 
		<br />
		<button class="btn btn-warning" form="pStartForm" type="submit">
			<fmt:message key="next" />
		</button>
	</form>
</div>
</body>
</html>