<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${param.locale}" scope="session"/>

<fmt:setBundle basename="resources"/>

<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<c:redirect url="${header.referer}"/>