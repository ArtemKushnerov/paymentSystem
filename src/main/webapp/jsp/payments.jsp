<%@ include file="header.jsp"%>

<c:set var="companies" value="${requestScope.companies}" />
<br />
<div class="container">
	<p>
		<fmt:message key="select_company" />:
	</p>
	<form id="sServiceForm" action="Controller" method="post">
		<input type="hidden" name="command" value="selectService"> 
		<select	name="Company">
			<c:forEach var="company" items="${companies}">
				<option title="${company.name}" value="${company.id}">${company.name}</option>
			</c:forEach>
		</select> 
		<br />
		<button class="btn btn-warning" form="sServiceForm" type="submit">
			<fmt:message key="next" />
		</button>
	</form>
</div>
</body>
</html>