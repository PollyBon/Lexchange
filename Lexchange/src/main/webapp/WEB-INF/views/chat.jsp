<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Chat"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <div class="col-md-8">
        <h1>Chat</h1>
        <form id="joinChatForm" method="GET" action="chat">
            <input id="chatId" name="chatId" type="hidden" value="${1}"/>
            <button id="start" type="submit" data-bind="click: joinChat">Join chat</button>
        </form>

        <div>
            <textarea rows="20" cols="60" readonly="readonly" data-bind="text: chatContent"
                      class="form-control"></textarea>
        </div>

        <form id="postMessageForm" method="POST" action="chat">
            <p>
                <input id="messageText" name="messageText" type="text" data-bind="value: messageText"/>
                <input id="chatId" name="chatId" type="hidden" value="${1}"/>
                <button id="post" type="submit" data-bind="click: postMessage">Post</button>
            </p>
        </form>
    </div>
    <div class="col-md-4">
        <table class="table table-bordered">
            <tr>
                <td rowspan="2">
                    <div class="word" data-bind="foreach: words">
                        <a data-bind="click: $parent.showInformation"><p data-bind="text: value"></p></a>
                    </div>
                </td>
                <td>
                    <p data-bind="text: currentWordValue"></p>
                    <p data-bind="text: currentTranslation"></p>
                    <p>
                        <input type="text" id="commentId" data-bind="text: currentComment, value: currentComment"/>
                        <button data-bind="click: changeComment">Change</button>
                    </p>
                </td>
            </tr>
            <tr>
                <td>
                    <%--<button data-bind="click: addNewWord">Add</button>--%>
                    <button>Translate</button>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="dictionary">
                        <c:forEach items="${user.dictionaries}" var="dic">
                            <p>
                                <a data-bind="click: function () { showWords('${dic.getWordsAsJSON()}');}">
                                        ${user.getNativeLanguage().toUpperCase()}-${dic.language}
                                </a>
                            </p>
                        </c:forEach>
                    </div>
                </td>
                <td>
                    <p>
                        <input type="text" data-bind="value: newWord">
                    </p>
                    <p>
                        <input type="text" data-bind="value: tran">
                        <button data-bind="click: function () { translateToLanguage('${user.nativeLanguage}'); }">${user.getNativeLanguage().toUpperCase()}</button>
                        <button data-bind="click: function () { translateToLanguage('${learnedLanguage}'); }">${learnedLanguage.toUpperCase()}</button>
                    </p>
                </td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>

