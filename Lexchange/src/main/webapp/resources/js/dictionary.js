$(document).ready(function () {

    function DictionaryViewModel() {

        var that = this;
        that.words = ko.observableArray();

        that.showWords = function () {
            //that.words = $("#dictionaryWords");
            that.words = [
                { name: "Pingvi"},
                { name: "Pingvi"},
                { name: "Pingvi"},
                { name: "Pingvi"},
                { name: "Pingvi"},
            ];
        };

    }

    //Activate knockout.js
    ko.applyBindings(new DictionaryViewModel());
});


