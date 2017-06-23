var aipaisheApp = angular.module('aipaisheApp');

var userRegistrationComponent = {
    bindings: {},
    templateUrl: './registration/userregistration.html',
    controller: UserRegistrationController,
    controllerAs: 'vm'
};

aipaisheApp.component('userRegistration', userRegistrationComponent);

UserRegistrationController.$inject = ['$http', '$routeParams', '$scope', '$mdDialog', '$location'];

function UserRegistrationController($http, $routeParams, $scope, $mdDialog, $location) {
    var vm = this;
    vm.firstName=null;
    vm.lastName=null;
    vm.email=null;
    vm.password=null;

    vm.confirm = function(){
        var user ={"firstName": vm.firstName, "lastName":vm.lastName, "email":vm.email, "password":vm.password};
        $http.post("/user/registration", user).then(function(response){
            console.log("successfully signup!")
             $mdDialog.show({
                            controller: DialogController,
                            templateUrl: './registration/signup.confirmation.html',
                            scope: $scope,
                            preserveScope: true,
                            parent: angular.element(document.body),
                            targetEvent: event,
                            clickOutsideToClose: true,
                            fullscreen: false // Only for -xs, -sm breakpoints.
                        })
                        .then(function(answer) {
                            $scope.status = 'OK';
                            $location.path('/home');
                        }, function(error) {
                            $scope.status = 'You cancelled the dialog.';
                        });
            }, function(error){console.log("failed to signup...")})
    };

    vm.reset = function(){
        vm.firstName=null;
        vm.lastName=null;
        vm.email=null;
        vm.password=null;
    };


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

}