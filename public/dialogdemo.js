angular.module('dialogDemo', ['ngMaterial'])

.controller('AppCtrl', function($scope, $http,  $mdDialog) {
    $scope.eventDate = null;
    $scope.eventName = null;
    $scope.eventVenue = null;

  $scope.status = '  ';
  $scope.customFullscreen = false;

  $scope.showPrompt = function(ev) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.prompt()
      .title('Which event do you want to join?')
      .textContent('Enter the Event ID.')
      .placeholder('Event ID')
      .ariaLabel('Event ID')
      .initialValue('')
      .targetEvent(ev)
      .ok('Join!')
      .cancel('Cancel');

    $mdDialog.show(confirm).then(function(result) {
      $scope.status = 'You decided to name your dog ' + result + '.';
    }, function() {
      $scope.status = 'You didn\'t name your dog.';
    });
  };

$scope.showCreateEventPanel= function(event){
 $mdDialog.show({
      controller: DialogController,
      templateUrl: 'create.panel.template.html',
      scope: $scope,
      preserveScope: true,
      parent: angular.element(document.body),
      targetEvent: event,
      clickOutsideToClose:true,
      fullscreen: false // Only for -xs, -sm breakpoints.
    })
    .then(function(answer) {
      $scope.status = 'Confirming';
      $http.jsonp("http://localhost:8080/createevent?ownerId=1&"+"eventName="+$scope.eventName+"&eventDate="+$scope.eventDate
        +"&eventVenue="+$scope.eventVenue).success(function(data){
            $scope.status="submitted request to server!"
      });
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