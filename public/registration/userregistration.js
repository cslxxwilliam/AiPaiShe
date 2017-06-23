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
    vm.loading=false;

    vm.confirm = function(){
        vm.loading=true;
        var user ={"firstName": vm.firstName, "lastName":vm.lastName, "email":vm.email, "password":vm.password};
        $http.post("/user/registration", user).then(function(response){
            console.log("successfully signup!")
             vm.loading=false;
             $mdDialog.show({
                            controller: DialogController,
                            templateUrl: './registration/signup.confirmation.html',
                            scope: $scope,
                            preserveScope: true,
                            parent: angular.element(document.body),
                            targetEvent: event,
                            clickOutsideToClose: false,
                            fullscreen: false // Only for -xs, -sm breakpoints.
                        })
                        .then(function(answer) {
                            $scope.status = 'OK';
                            $location.path('/home');
                        }, function(error) {
                            $scope.status = 'You cancelled the dialog.';
                        });
            }, function(error){
                console.log("failed to signup...");
                vm.loading=false;

                })
    };

    vm.reset = function(){
        vm.firstName=null;
        vm.lastName=null;
        vm.email=null;
        vm.password=null;
        vm.loading=false;
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