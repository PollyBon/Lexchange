$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        that.chatContent = ko.observable('');
        that.messageText = ko.observable('');
        that.activePollingXhr = ko.observable(null);

        that.joinChat = function () {
             var form = $("#joinChatForm");
             that.activePollingXhr($.ajax({
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
                        resetUI();
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                }
             }));

             $('#messageText').focus();
             pollForMessages();
        };

        function pollForMessages() {
            var form = $("#joinChatForm");
            that.activePollingXhr($.ajax({
                url: form.attr("action"),
                type: "GET",
                data: $("#chatId"),
                cache: false,
                success: function (messageList) {
                    for (var i = 0; i < messageList.length; i++) {
                        that.chatContent(that.chatContent() + messageList[i]);
                    }
                    keepPolling = false; //WAS ADDED
                },
                error: function (xhr) {
                    if (xhr.statusText != "abort" && xhr.status != 503) {
                        resetUI();
                        console.error("Unable to retrieve chat messages. Chat ended.");
                    }
                },
                complete: pollForMessages
            }));
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
                keepPolling = true; //WAS ADDED
            }
        };

        that.leaveChat = function () {
            that.activePollingXhr(null);
            resetUI();
        };

        function resetUI() {
            keepPolling = false;
            that.activePollingXhr(null);
            that.messageText('');
            that.chatContent('');
        }

    }

    //Activate knockout.js
    ko.applyBindings(new ChatViewModel());

});


