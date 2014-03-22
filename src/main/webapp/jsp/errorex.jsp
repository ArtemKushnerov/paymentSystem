<%@  page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="lang" value="${sessionScope.language}" />
<fmt:setLocale value="${lang}_${fn:toUpperCase(lang)}" />
<fmt:setBundle basename="i18n.Resource" />
<!DOCTYPE html >
<html>
<head>
<link
	href="<%=request.getContextPath()%>/css/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error</title>
</head>
<body>
	<fmt:message key="ERROR_MESSAGE" />
	<br />
	<fmt:message key="${requestScope.errorMessage}" />
	<br />
</body>
</html>