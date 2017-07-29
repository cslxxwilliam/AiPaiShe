var aipaisheApp = angular.module('aipaisheApp');

var headerComponent = {
    bindings: {},
    templateUrl: 'header.html',
    controller: HeaderComponentController,
    controllerAs: 'vm'
};

aipaisheApp.component('aipaisheHeader', headerComponent);

HeaderComponentController.$inject = ['$scope', '$rootScope', '$location', '$mdDialog', '$http', 'AUTH_EVENTS', 'Session'];

function HeaderComponentController($scope, $rootScope, $location, $mdDialog, $http, AUTH_EVENTS, Session) {
    var vm = this;

    vm.username = "Guest";
    vm.isLogin = false;
    vm.loginEmail = "";
    vm.loginPassword = "";

    vm.signUp = function() {
        $location.path('/signup');
    }

    vm.showErrorMessage = function(msg) {
        $mdDialog.show(
            $mdDialog.alert()
            .parent(angular.element(document.body))
            .clickOutsideToClose(true)
            .title('Confirmation')
            .textContent(msg)
            .ariaLabel('Alert Dialog Demo')
            .ok('Got it!')
            .targetEvent()
        );
    }

    vm.login = function(ev) {
        $mdDialog.show({
                controller: DialogController,
                templateUrl: 'login.tmpl.html',
                scope: $scope, // use parent scope in template
                preserveScope: true, // do not forget this if use parent scope
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
            })
            .then(function(msg) {
                console.log(msg);

                // verify the user email and password
                $http.get('user/login?email=' + vm.loginEmail + '&password=' + vm.loginPassword).then(function(response) {
                    var respData = response.data;
                    vm.username = respData.username;
                    vm.isLogin = true;
                    vm.showErrorMessage('Login successful!');

                    console.log(response);

                    Session.create(response.id, respData.userid, 'testuser');

                    $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                }, function(error) {
                    console.log(error);
                    console.log('error when hitting server: ' + error.statusText);
                    vm.isLogin = false;
                    vm.showErrorMessage('Invalid email or password!');

                    Session.destroy();

                    $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                });

            }, function() {
                vm.username = 'Guest';
                vm.isLogin = false;
            });
    }

    vm.logout = function(ev) {
        vm.username = 'Guest';
        vm.isLogin = false;
        vm.showErrorMessage('You have successfully logged out!');
        $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
    }

    DialogController = function($scope, $mdDialog) {
        $scope.hide = function() {
            $mdDialog.hide();
        };

        $scope.cancel = function() {
            $mdDialog.cancel();
        };

        $scope.confirmLogin = function() {
            $mdDialog.hide('login OK!');
        }
    }
}