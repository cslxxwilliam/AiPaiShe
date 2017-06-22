var aipaisheApp = angular.module('aipaisheApp');

var userRegistrationComponent = {
    bindings: {},
    templateUrl: './registration/userregistration.html',
    controller: UserRegistrationController,
    controllerAs: 'vm'
};

aipaisheApp.component('userRegistration', userRegistrationComponent);

UserRegistrationController.$inject = ['$http', '$routeParams', '$scope'];

function UserRegistrationController($http, $routeParams, $scope) {
    var vm = this;
    vm.firstName=null;
    vm.lastName=null;
    vm.email=null;
    vm.password=null;

    vm.confirm = function(){
        var user ={"firstName": vm.firstName, "lastName":vm.lastName, "email":vm.email, "password":vm.password};
        $http.post("/user/registration", user).then(function(){console.log("successfully signup!")}, function(){console.log("failed to signup...")})
    };

    vm.reset = function(){
        vm.firstName=null;
        vm.lastName=null;
        vm.email=null;
        vm.password=null;
    };
}