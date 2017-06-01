angular.module('aipaisheApp')
.config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/home', {
            template: '<aipaishe-home></aipaishe-home>'
        }).
        when('/event', {
            templateUrl: 'eventviewer.html'
        }).
        when('/event/:eventId', {
            templateUrl: 'eventviewer.html'
        }).
        otherwise('/home');
    }
]);