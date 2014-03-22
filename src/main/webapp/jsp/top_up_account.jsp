<%@ include file="header.jsp"%>

<body>
	<div class="container">
		<fmt:message key="GOING_TO_TOP_UP_ACCOUNT_MESSAGE" />
		<br>
		<fmt:message key="select_account" />
		<form id="topUpForm" action="Controller" method="post">
			<input type="hidden" name="command" value="topupaccount2"> 
			<select	name="Account">
				<c:forEach var="account" items="${requestScope.accounts}">
					<option title="${account.id}" value="${account.id}">${account.id}</option>
				</c:forEach>
			</select><br>
			<fmt:message key="enter_amount" />:
			 <input form="topUpForm" type="text" name="sum"
				onkeyup="checkInput(this)"><br>
			<button class="btn btn-warning" form="topUpForm" type="submit">
				<fmt:message key="top_up" />
			</button>

		</form>
	</div>
</body>
</html>