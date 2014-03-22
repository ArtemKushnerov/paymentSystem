<%@ include file="header.jsp"%>

<div class="container">
	<table class="table table-hover">
		<caption>
			<fmt:message key="payments" />
		</caption>
		<tr>
			<th><fmt:message key="date" /></th>
			<th><fmt:message key="summ" /></th>
			<th><fmt:message key="note" /></th>
		</tr>
		<c:forEach var="payment" items="${requestScope.payments}">
			<tr>
				<td><fmt:formatDate value="${payment.date}" /></td>
				<td><fmt:formatNumber currencyCode="BYR">${payment.amount}</fmt:formatNumber>
				</td>
				<td>${payment.paymentDetails}</td>
			</tr>
		</c:forEach>
	</table>
	<%--  <div class="pagination">
		<ul>
			<c:if test="${requestScope.currentPage != 1}">
				<li><a href="Controller?command=paymentshistory&resultsPerPage=5&page=${requestScope.currentPage-1}">«</a></li>
			</c:if>
			<c:forEach begin="1" end="${requestScope.numOfPages}" var="i">
				<c:choose>
					<c:when test="${requestScope.currentPage eq i}">
						<li class="active"><a href="#">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="Controller?command=paymentshistory&resultsPerPage=5&page=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${requestScope.currentPage lt requestScope.numOfPages}">
				<li><a href="Controller?command=paymentshistory&resultsPerPage=5&page=${requestScope.currentPage+1}">»</a></li>
			</c:if>
		</ul>
	</div>  --%>
</div>
</body>
</html>
