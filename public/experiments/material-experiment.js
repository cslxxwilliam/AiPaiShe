angular.module('MyApp',['ngMaterial'])

.controller('DialogControl', function($scope, $mdDialog) {
  $scope.status = '  ';

  $scope.showPrompt = function(ev) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.prompt()
      .title('What is the name for your event?')
      .textContent('For example, Little Quack\'s Awesome Wedding ')
      .placeholder('Wedding Name')
      .ariaLabel('Dog name')
      .initialValue('Little Quack')
      .targetEvent(ev)
      .ok('Okay')
      .cancel('Cancel');

    $mdDialog.show(confirm).then(function(result) {
      $scope.status = 'You have created an event: ' + result + '.';
    }, function() {
      $scope.status = 'You didn\'t create any event';
    });
  };
});