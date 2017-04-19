angular.
  module('aipaisheApp').
  config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
      $locationProvider.hashPrefix('!');

      $routeProvider.
        when('/home', {
          template: '<aipaishe-home></aipaishe-home>'
        }).
        when('/profile/:profileId', {
          template: '<profile-detail></profile-detail>'
        }).
        otherwise('/home');
    }
  ]);