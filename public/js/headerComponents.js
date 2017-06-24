var aipaisheApp = angular.module('aipaisheApp');

var headerComponent = {
    bindings: {},
    templateUrl: 'header.html',
    controller: HeaderComponentController,
    controllerAs: 'vm'
};

aipaisheApp.component('aipaisheHeader', headerComponent);

HeaderComponentController.$inject = ['$scope','$location','$mdDialog'];

function HeaderComponentController($scope, $location, $mdDialog) {
    var vm = this;

    this.username = "Guest";
    vm.isLogin = false;

    vm.signUp = function(){
         $location.path('/signup');
    }

    vm.login = function(ev) {
    $mdDialog.show({
          controller: DialogController,
          templateUrl: 'login.tmpl.html',
          parent: angular.element(document.body),
          targetEvent: ev,
          clickOutsideToClose:true,
          fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        })
        .then(function(username) {
          vm.username = username;
        }, function() {
          vm.username = 'Guest';
        });

    }

    DialogController = function ($scope, $mdDialog) {
        $scope.hide = function() {
          $mdDialog.hide();
        };

        $scope.cancel = function() {
          $mdDialog.cancel();
        };

        $scope.answer = function(answer) {
          $mdDialog.hide(answer);
        };

        $scope.confirmLogin = function() {
            $mdDialog.hide('Venus');
            vm.isLogin = true;
        }
      }
}