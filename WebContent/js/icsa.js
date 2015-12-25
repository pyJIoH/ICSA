(function (root, $, undefined) {
    'use strict';

    $(document).ready(function(){
        setTimeout(function(){
            $(this).scrollTop(0);
        }, 700);
    });

    var icsaApp = angular.module('icsaApp', [])
        .controller('icsaCtrl', function ($scope, $http) {
                $scope.phoneClicked = function (event) {
                    var $anchor = $(event.currentTarget);
                    $('html, body').stop().animate({
                        scrollTop: $($anchor.attr('href')).offset().top
                    }, 1500, 'easeInOutExpo');
                    event.preventDefault();
                    startButton(event);
                };

                $('#tokenfield').tokenfield({
                    autocomplete: {
                        source: ['CorBusiness', 'CorPortfolio', 'CorRisk', 'CorStrategy', 'CorVu NG', 'D3', 'Discover', 'Folio', 'HyperVu', 'LegaSuite', 'NetCure', 'Unidata', 'UniVerse'],
                        delay: 100
                    },
                    showAutocompleteOnFocus: false
                });

                $('#tokenfield')
                    .on('tokenfield:createtoken', function (e) {
                        var value = e.target.value === '' ? '' : e.target.value + ',';
                        var tokens = value + e.attrs.value;
                        jQuery.storeKeyword(e.attrs.value);
                        console.info("==sent to server== " + tokens);
                        $http.get("http://icsa.mybluemix.net/api/docs/" + tokens).
                        then(function (response) {
                            $scope.items = response.data;
                            $('#circularG').show();
                            $('#panel_contact').removeClass('in');
                            window.setTimeout(function () {
                                $(this).showBlocks();
                            }, 500);
                        });
                    })
                    .on('tokenfield:removedtoken', function (e) {
                        var tokens = e.target.value;
                        jQuery.removeStoredKeyword(e.attrs.value);
                        $http.get("http://icsa.mybluemix.net/api/docs/" + tokens).
                        then(function (response) {
                            $scope.items = response.data;
                            $('#circularG').show();
                            $('#panel_contact').removeClass('in');
                            window.setTimeout(function () {
                                $(this).showBlocks();
                            }, 500);
                        });
                    })
                    .tokenfield();

                $('#myModal').on('show.bs.modal', function (e) {
                    var $modal = $(this),
                        data = $(e.relatedTarget).data('item');
                    $('#myModalTitle').html(data.title);
                    $('#myModalBody').html('<pre>' + data.solution + '</pre>');
                });
            }
        );

    jQuery.fn.showBlocks = function () {
        $('.grid-item').each(function () {
            $(this).addClass('in');
        });
        if ($('.grid-item').length > 0)
        {
            $('#panel_contact').addClass('in');
            $('#circularG').hide();
        }
    }

}(this, jQuery));