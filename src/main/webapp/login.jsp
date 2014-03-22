<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<c:set var="language"
	value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
	scope="session" />
<fmt:setLocale value="${language}_${fn:toUpperCase(language)}" />
<fmt:setBundle basename="i18n.Resource" />
<html>
<head>
<link
	href="<%=request.getContextPath()%>/css/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<title>login page</title>
</head>
<body>
	<form>
		<select id="language" name="language" onchange="submit()">
			<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
			<option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
		</select>
	</form>
	<div style="width: 800px; margin: 0 auto;">
		<div align=center class="row">
			<div class="span4 offset4">
				<div class="well">
					<form method="POST" action="<%=request.getContextPath()%>/Controller" accept-charset="UTF-8">
						<input name="login" class="span3" placeholder=<fmt:message key="login" /> type="text"> 
						<input name="password" class="span3" placeholder=<fmt:message key="password" /> type="password">
						<input type="hidden" name="command" value="login" />
						<button class="btn-primary" type="submit">
							<fmt:message key="signin" />
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
