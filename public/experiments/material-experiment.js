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

  $scope.showAdvanced= function(event){
   $mdDialog.show({
        controller: DialogController,
        templateUrl: 'create.panel.tmpl.html',
        parent: angular.element(document.body),
        targetEvent: event,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      })
      .then(function(answer) {
        $scope.status = 'You said the information was "' + answer + '".';
      }, function() {
        $scope.status = 'You cancelled the dialog.';
      });
  }

    function DialogController($scope, $mdDialog) {
      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };

      $scope.answer = function(answer) {
        $mdDialog.hide(answer);
      };
    }
});