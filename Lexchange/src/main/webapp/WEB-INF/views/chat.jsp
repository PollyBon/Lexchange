<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Chat</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="resources/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="resources/js/knockout-2.0.0.js"></script>
    <script type="text/javascript" src="resources/js/chat.js"></script>

</head>
<body>
<h1>Chat</h1>

<form id="joinChatForm" method="GET" action="chat" data-bind="visible: activePollingXhr() == null">
    <input id="chatId" name="chatId" type="hidden" value="${1}"/>
    <button id="start" type="submit" data-bind="click: joinChat">Join chat</button>
</form>

<form id="leaveChatForm" action="chat" data-bind="visible: activePollingXhr() != null">
    <p>
        You're chatting as <strong>${user.firstName}</strong>
        <button id="leave" type="submit" data-bind="click: leaveChat">Leave Chat</button>
    </p>
</form>

<div data-bind="visible: activePollingXhr() != null">
    <textarea rows="20" cols="80" readonly="readonly" data-bind="text: chatContent"></textarea>
</div>

<form id="postMessageForm" method="POST" action="chat" data-bind="visible: activePollingXhr() != null">
    <p>
        <input id="messageText" name="messageText" type="text" data-bind="value: messageText"/>
        <input id="chatId" name="chatId" type="hidden" value="${1}"/>
        <button id="post" type="submit" data-bind="click: postMessage">Post</button>
    </p>
</form>
</body>

</html>
