angular.module('EventViewerApp', ['ngMaterial'])
    .controller('BasicDemoCtrl', BasicDemoCtrl);

function BasicDemoCtrl($mdPanel, $scope) {
  $scope.readonly=true;
  $scope.eventDate = new Date("2017-06-17");
  $scope.eventName = "Shenzhen APS Party";
  $scope.eventVenue = "Binhe Dadao No.179";
};