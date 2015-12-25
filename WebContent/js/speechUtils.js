var ALCH_API_ENDPOINT = "http://gateway-a.watsonplatform.net/calls/text/TextGetRankedKeywords",
    ALCH_API_KEY = "734491f234fa8194eeb11a9ec505b715edb81771";//"6d4cf4a4b8b76977be05e23c18ecda35f269a5f8";
var storedKeywords = [];
var increment = 0;

(function(root, $, undefined) {
    'use strict';

    jQuery.removeStoredKeyword = function(keyword) {
        var i = storedKeywords.indexOf(keyword);
        if (i > -1) {
            storedKeywords.splice(i, 1);
        }
    };

    jQuery.storeKeyword = function(keyword) {
        if (storedKeywords.indexOf(keyword) === -1) {
            storedKeywords.push(keyword);
        }
    }

    jQuery.receiveTokens = function(text) {
        $.get("http://icsa.mybluemix.net/api/dict/", function(data, status) {
            var result = text;
            $.each(data.toLowerCase().split(','), function (index, value) {
                var lowerCase = result.toLowerCase();
                result = lowerCase.replace(new RegExp("([^a-zA-Z]|^)"+value+"([^a-zA-Z]|$)", 'g'), ' Corvu NG ');
            });
            $.post(
                ALCH_API_ENDPOINT,
                {
                    apikey: ALCH_API_KEY,
                    text: result,
                    outputMode: "json"
                },
                function (data, status) {
                    var res = [];
                    if (data.status !== "ERROR") {
                        $.each(data.keywords, function (index, value) {
                            res[index] = value.text;
                        });
                    } else {
                        console.error("Alchemy API hasn't sent tokens!");
                    }
                    if (res.length > 0) {
                        $.get("http://icsa.mybluemix.net/api/dictionary/", function(data, status) {
                            $.each(res, function(index, value) {
                                if (data.indexOf(value) > -1 && storedKeywords.indexOf(value) === -1) {
                                    storedKeywords.push(value);
                                }
                            });
                            if (increment === 2) {
                                jQuery.storeKeyword("corvu ng");
                            }
                            $('#tokenfield').tokenfield('setTokens', storedKeywords);
                            increment++;
                        });
                    }
                });
        });
    };

} (this, jQuery));