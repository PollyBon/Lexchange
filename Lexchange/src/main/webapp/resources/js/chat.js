$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        that.userName = ko.observable('');
        that.chatId = ko.observable(0);
        that.chatContent = ko.observable('');
        that.messageText = ko.observable('');
        that.activePollingXhr = ko.observable(null);

        var keepPolling = false;

        that.joinChat = function () {
            keepPolling = true;
            pollForMessages();
        };

        function pollForMessages() {
            if (!keepPolling) {
                return;
            }
            var form = $("#joinChatForm");
            that.activePollingXhr($.ajax({
                url: form.attr("action"),
                type: "GET",
                dataType: "json",
                data: "chatId=1",  //$("#joinChatForm input[name=chatId]")
                cache: false,
                success: function (messageList) {
                    for (var i = 0; i < messageList.length; i++) {
                        that.chatContent(that.chatContent() + JSON.stringify(messageList[i]) + "\n");
                    }
                    keepPolling = false;
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
                    data: form.serialize(),
                    error: function (xhr) {
                        console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
                    }
                });
                that.messageText('');
                keepPolling = true;
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


