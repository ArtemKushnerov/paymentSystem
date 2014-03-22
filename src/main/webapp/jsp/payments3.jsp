<%@ include file="header.jsp"%>

<c:set var="user" value="${sessionScope.user}" />
<c:set var="service" value="${sessionScope.selectedService}" />
<div class="container">
	<p><fmt:message key="GOING_TO_PAY_MESSAGE" />: ${service.name}<br>
	<fmt:message key="in_company" />: ${service.company.name}</p>
	<fmt:message key="select_card" />
	<form id="payForm" action="Controller" method="post">
		<input type="hidden" name="command" value="makePayment"> <select
			name="Card">
			<c:forEach var="card" items="${user.cards}">
				<option title="${card.number}" value="${card.id}">${card.number}</option>
			</c:forEach>
		</select><br>
		<fmt:message key="enter_amount" />:
		 <input form="payForm" type="text" name="sum"
			onkeyup="checkInput(this)"><br>
		<button class="btn btn-warning" form="payForm" type="submit">
			<fmt:message key="pay" />
		</button>
	</form>
</div>
</body>
</html>
