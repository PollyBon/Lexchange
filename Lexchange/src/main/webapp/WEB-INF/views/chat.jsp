<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Chat"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <div class="col-md-8">
        <textarea id="textarea" rows="16" readonly="readonly" data-bind="text: chatContent"
                  class="form-control chat-block"></textarea>
        <form id="postMessageForm" method="POST" action="chat">
            <p>
                <input id="messageText" name="messageText" type="text" data-bind="value: messageText"/>
                <input id="chatId" name="chatId" type="hidden" value="${chatId}"/>
                <button id="post" type="submit" data-bind="click: postMessage"><spring:message
                        code="post"/></button>
            </p>
        </form>
    </div>
    <div class="col-md-4">
        <table class="table chat-block">
            <tr>
                <td>
                    <div class="words scrollbar" data-bind="foreach: words">
                        <a data-bind="click: $parent.showInformation"><p data-bind="text: value"></p></a>
                    </div>
                </td>
                <td>
                    <div class="word-description">
                        <p data-bind="text: currentWordValue"></p>
                        <p data-bind="text: currentTranslation"></p>
                    </div>
                    <p>
                        <input type="text" class="form-control input-sm" id="commentId"
                               data-bind="text: currentComment, value: currentComment"/>
                    </p>
                    <p>
                        <button data-bind="click: changeComment" class="btn btn-sm btn-info width-100"><i
                                class="fa fa-pencil"></i> <spring:message code="change"/></button>
                    </p>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="dictionary scrollbar">
                        <c:forEach items="${user.dictionaries}" var="dic">
                            <p>
                                <a data-bind="click: function () { showWords('${dic.getWordsAsJSON()}');}">
                                        ${languages[0].getByCode(user.getNativeLanguage())}
                                    - ${languages[0].getByCode(dic.language)}
                                </a>
                            </p>
                        </c:forEach>
                    </div>
                </td>
                <td>
                    <p>
                        <input id="newWord" type="text" class="form-control input-sm" data-bind="value: newWord">
                    </p>
                    <p>
                        <button data-bind="click: function () { translateToLanguage('${user.nativeLanguage}'); }"
                                class="btn btn-sm btn-info float-left">${languages[0].getByCode(user.getNativeLanguage())}</button>
                        <button data-bind="click: function () { translateToLanguage('${learnedLanguage}'); }"
                                class="btn btn-sm btn-info float-right">${languages[0].getByCode(learnedLanguage.toUpperCase())}</button>
                    </p>
                    <p>
                        <input type="text" class="form-control input-sm" data-bind="value: tran">
                    </p>
                    <p>
                        <button class="btn btn-sm btn-info"
                                data-bind="click: function () { addNewWord('${learnedLanguage}'); }">Add
                        </button>
                    </p>
                </td>
            </tr>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

<script type="text/javascript" src="resources/js/chat.js"></script>

</body>

