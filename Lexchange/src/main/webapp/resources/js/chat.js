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
            that.currentWordValue(param.value);
            that.currentTranslation(param.translation);
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

        that.translateToLanguage = function (language) {
            $.ajax({
                url: 'https://translate.yandex.net/api/v1.5/tr.json/translate',
                type: "GET",
                data: "key=trnsl.1.1.20160927T094359Z.bc33daa2e80b1e99.5b15b6bbb76f58f6e1f035b9395f0d4d50a82aa2" +
                "&text=" + that.newWord() + "&lang=" + language,
                cache: false,
                success: function (answer) {
                    that.tran(answer.text);
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve translation");
                    }
                }
            });

        };

        function getNewMessages() {
            $.ajax({
                url: 'newMessages',
                type: "GET",
                data: "chatId=" + $('#chatId').val(),
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
        }


        that.postMessage = function () {
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
            $('#textarea').scrollTop($('#textarea')[0].scrollHeight);
        };

        that.addNewWord = function (language) {
            $.ajax({
                url: "addNewWord",
                type: "POST",
                data: "value=" + that.newWord() + "&translation=" + that.tran() + "&language=" + language,
                cache: false,
                error: function (xhr) {
                    console.error("Error adding new word: status=" + xhr.status + ", statusText=" + xhr.statusText)
                }
            });
        };

        window.onload = function () {
            $.ajax({
                url: "chat",
                type: "GET",
                data: "chatId=" + $('#chatId').val(),
                cache: false,
                success: function (messageList) {
                    for (var i = 0; i < messageList.length; i++) {
                        that.chatContent(that.chatContent() + messageList[i]);
                    }
                    $('#textarea').scrollTop($('#textarea')[0].scrollHeight);
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                }
            });
            getNewMessages();
        };

        window.onselect = function () {
            $('#newWord').val(window.getSelection().toString());
        }

    }

    //Activate knockout.js
    ko.applyBindings(new ChatViewModel());

});


