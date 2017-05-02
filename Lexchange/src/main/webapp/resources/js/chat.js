$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        that.chatContent = ko.observable('');
        that.messageText = ko.observable('');

        that.words = ko.observableArray();
        that.currentWord = ko.observable('');
        that.currentWordValue = ko.observable('');
        that.currentTranslation = ko.observable('');
        that.currentComment = ko.observable('');
        that.newWord = ko.observable('');

        that.tran = ko.observable('');

        that.showWords = function (param) {
            that.words(JSON.parse((param)));
        };

        that.showInformation = function (param) {
            that.currentWord(param);
            that.currentWordValue("Word: " + param.value);
            that.currentTranslation("Translation: " + param.translation);
            that.currentComment(param.comment);
        };

        that.changeComment = function () {
            var newComment = $("#commentId").val();
            that.currentComment(newComment);

            $.ajax({
                url: "newComment",
                type: "POST",
                data: "comment=" + newComment +
                "&wordId=" + that.currentWord().id +
                "&dictionaryId=" + that.currentWord().dictionaryId,
                error: function (xhr) {
                    console.error("Error while changing the comment=" + xhr.status + ", statusText=" + xhr.statusText);
                }
            });
        };

        that.translateToNative = function () {
            $.ajax({
                url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
                type: "GET",
                data: "key=trnsl.1.1.20160927T094359Z.bc33daa2e80b1e99.5b15b6bbb76f58f6e1f035b9395f0d4d50a82aa2" +
                "&text=" + that.newWord() + "&lang=ru",
                cache: false,
                success: function (answer) {
                    that.tran(JSON.stringify(answer));
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve translation");
                    }
                }
            });

        };

        that.joinChat = function () {
            var form = $("#joinChatForm");
            $.ajax({
                url: form.attr("action"),
                type: "GET",
                data: $("#chatId"),
                cache: false,
                success: function (messageList) {
                    for (var i = 0; i < messageList.length; i++) {
                        that.chatContent(that.chatContent() + messageList[i]);
                    }
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                }
            });
            getNewMessages();
            $('#messageText').focus();
        };

        function getNewMessages() {
            $.ajax({
                url: 'newMessages',
                type: "GET",
                data: $("#chatId"),
                cache: false,
                success: function (messageList) {
                    for (var i = 0; i < messageList.length; i++) {
                        that.chatContent(that.chatContent() + messageList[i]);
                    }
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve new messages. Chat ended.");
                    }
                },
                complete: getNewMessages
            });
            $('#messageText').focus();
        }


        that.postMessage = function () {
            if (that.messageText().trim() != '') {
                var form = $("#postMessageForm");
                $.ajax({
                    url: form.attr("action"),
                    type: "POST",
                    data: $('#postMessageForm :input'),
                    error: function (xhr) {
                        console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
                that.messageText('');
            }
        };

    }

    //Activate knockout.js
    ko.applyBindings(new ChatViewModel());

});


