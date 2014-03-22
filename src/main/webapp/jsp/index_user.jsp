
<%@ include file="header.jsp"%>
<div class="container">
<c:set var="user" value="${sessionScope.user}" />
	<h1><fmt:message key="welcom" /> <c:out value="${user.firstName} ${user.lastName}" />!</h1>
	<p>
		<fmt:message key="CARD_BALANCE_MESSAGE" />
		:
	</p>
	<div class="container">
		<table class="table" border="1" >
			<tr>
				<td><fmt:message key="card_number" /></td>
				<td><fmt:message key="available" /></td>
			</tr>
			<c:forEach var="card" items="${user.cards}">
				<tr>
					<td>${card.number}</td>
					<c:set var="accountuser" value="${card.account}" />
					<td><fmt:formatNumber currencyCode="USD">${accountuser.balance}</fmt:formatNumber>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</div>
</html>
