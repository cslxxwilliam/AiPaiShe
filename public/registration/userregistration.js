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
}