angular.module('aipaisheApp')
    .config(['$locationProvider', '$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider.
            when('/home', {
                template: '<aipaishe-home></aipaishe-home>'
            }).
            when('/myevents', {
                template: '<event-list></event-list>'
            }).
            when('/event/:eventId', {
                template: '<event-detail></event-detail>'
            }).
            when('/signup', {
                template: '<user-registration></user-registration>'
            }).
            otherwise('/home');
        }
    ]);