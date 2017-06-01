<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Chat"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <div class="col-md-8">
        <textarea id="textarea" rows="18" readonly="readonly" data-bind="text: chatContent"
                  class="form-control chat-block"></textarea>
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
                    <p>
                        <input type="text" class="form-control input-sm" data-bind="value: tran">
                    </p>
                    <p>
                        <button class="btn btn-sm btn-info width-100"
                                data-bind="click: function () { addNewWord('${learnedLanguage}'); }">
                            <i class="fa fa-plus" aria-hidden="true"></i> <spring:message code="add"/>
                        </button>
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
                                class="btn btn-sm btn-info width-100">${languages[0].getByCode(user.getNativeLanguage())}
                            <i class="fa fa-long-arrow-right" aria-hidden="true"></i></button>
                    </p>
                    <p>
                        <button data-bind="click: function () { translateToLanguage('${learnedLanguage}'); }"
                                class="btn btn-sm btn-info width-100"><i class="fa fa-long-arrow-left" aria-hidden="true"></i>
                            ${languages[0].getByCode(learnedLanguage.toUpperCase())}</button>
                    </p>
                </td>
            </tr>
        </table>
    </div>
    <form id="postMessageForm" method="POST" action="chat">
        <input id="chatId" name="chatId" type="hidden" value="${chatId}"/>
        <div class="col-md-8">
            <textarea id="messageText" name="messageText" type="text" data-bind="value: messageText"
                      class="form-control message-field" rows="2" spellcheck="false"></textarea>
        </div>
        <div class="col-md-4">
            <button id="post" class="btn btn-submit" type="submit" data-bind="click: postMessage">
                <spring:message code="post"/></button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

<script type="text/javascript" src="resources/js/chat.js"></script>

</body>

