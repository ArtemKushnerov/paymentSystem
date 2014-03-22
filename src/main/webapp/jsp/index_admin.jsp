<%@ include file="header_admin.jsp" %>
 <c:set var="user" value="${sessionScope.user}"/>
    <div class="container">
        <h1><fmt:message key="welcom" /> <c:out value="${user.firstName} ${user.lastName}" /></h1>
        <p><fmt:message key="ACCOUNTS_USER_MESSAGE"/>:</p>
        <table class="table">
            <tr>
                <th><fmt:message key="account_number"/></th>
                <th><fmt:message key="locked"/></th>
            </tr>
            <c:forEach var="account" items="${requestScope.accounts}">
                <tr>
                    <td>${account.id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${account.blocked == false}">
                                <fmt:message key="unlocked"/>
                            </c:when>
                            <c:when test="${account.blocked}">
                                <form name="${account.id}" action="Controller" method="post">
                                    <input type="hidden" name="command" value="releaseblock">
                                    <input type="hidden" name="accountid" value="${account.id}">
                                    <button class="btn-info" type="submit">
                                        <fmt:message key="unlock"/>
                                    </button>
                                </form>
                            </c:when>
                        </c:choose>
                    </td> 
                </tr>
            </c:forEach>	
        </table>
    </div>
</body>
</html>