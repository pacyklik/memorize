
var app = angular.module('memorizeApp', ['ui.router', 'ui.bootstrap']);

// by not sending a “WWW-Authenticate” header in a 401 response
app.config(function ($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});

