var aipaisheApp = angular.module('aipaisheApp');

var headerComponent = {
    bindings: {},
    templateUrl: 'header.html',
    controller: HeaderComponentController,
    controllerAs: 'vm'
};

aipaisheApp.component('aipaisheHeader', headerComponent);

HeaderComponentController.$inject = ['$scope'];

function HeaderComponentController($scope) {
    var vm = this;

    this.username = "Please login...";

}