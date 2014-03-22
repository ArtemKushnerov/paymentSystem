<%@  page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="lang" value="${sessionScope.language}" />
<fmt:setLocale value="${lang}_${fn:toUpperCase(lang)}" />
<fmt:setBundle basename="i18n.Resource" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title><fmt:message key="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script>
	function checkInput(ob) {
		var invalidChars = /[^0-9.]/gi
		if (invalidChars.test(ob.value)) {
			ob.value = ob.value.replace(invalidChars, "");
		}
	}
</script>
<link
	href="<%=request.getContextPath()%>/css/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	padding-top: 60px;
	/* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link href="../css/bootstrap-responsive.css" rel="stylesheet">
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="Controller?command=home"><fmt:message key="title" /></a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li><a href="Controller?command=home"><fmt:message
									key="gohome" /></a></li>
						<li><a href="Controller?command=topupaccount"><fmt:message key="topupaccount" /></a></li>
						<li><a href="Controller?command=paymentsshow"><fmt:message key="payments" /></a></li>
						<li><a href="Controller?command=paymentshistory&resultsPerPage=5&page=1"><fmt:message key="paymentshistory" /></a></li>
						<li><a href="Controller?command=accountblock"><fmt:message key="accountlock" /></a></li>
						<li><a href="Controller?command=logout"> <fmt:message key="logout" /></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>