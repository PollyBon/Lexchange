$(document).ready(function () {

    function ChatViewModel() {

        var that = this;

        that.userName = ko.observable('');
        that.chatId = ko.observable(1);
        that.chatContent = ko.observable('');
        that.messageText = ko.observable('');
        that.activePollingXhr = ko.observable(null);

        var keepPolling = true;

        that.joinChat = function () {
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
                data: $("#joinChatForm input[name=chatId]").val(),
                cache: false,
                success: function (messages) {
                    for (var i = 0; i < messages.length; i++) {
                        that.chatContent(that.chatContent() + messages[i] + "\n");
                    }
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
            }
        };

        that.leaveChat = function () {
            that.activePollingXhr(null);
            resetUI();
            this.userName('');
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


