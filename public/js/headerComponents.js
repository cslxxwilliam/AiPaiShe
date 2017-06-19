var aipaisheApp = angular.module('aipaisheApp');

var headerComponent = {
    bindings: {},
    templateUrl: 'header.html',
    controller: HeaderComponentController,
    controllerAs: 'vm'
};

aipaisheApp.component('aipaisheHeader', headerComponent);

HeaderComponentController.$inject = ['$scope', '$location'];

function HeaderComponentController($scope, $location) {
    var vm = this;

    this.username = "Gal Gadot (Wonder Woman)";

    $scope.signUp = function(){
         $location.path('/signup');
    }
}