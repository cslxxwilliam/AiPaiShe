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
            template: '<event-detail></event-detail>'
        }).
        otherwise('/home');
    }
]);