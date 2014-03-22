<%@ include file="header.jsp" %>
    <div class="container">
        <table class="table table-hover"  border="1">
            <tr>
                <td>
                    <fmt:message key="account_number"/>
                </td>
                <td>
                    <fmt:message key="amount"/>
                </td>
                <td align="center" >
                    <fmt:message key="operation"/>
                </td>
            </tr>
            <c:forEach var="account" items="${requestScope.accounts}">
                <tr>
                    <td>${account.id}</td>
                    <td>
                        <fmt:formatNumber currencyCode="USD">${account.balance}</fmt:formatNumber>    
                        </td>
                        <td align="center">
                        <c:choose>
                            <c:when test="${account.blocked}">
                                <fmt:message key="locked"/>
                            </c:when>
                            <c:otherwise>
                                <form name="${account.id}" action="Controller" method="post">
                                    <input type="hidden" name="command" value="accountblock2">
                                    <input type="hidden" name="accountid" value="${account.id}">
                                    <button class="btn btn-danger" type="submit">
                                        <fmt:message key="lock"/>
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>  
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>