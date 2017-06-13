angular.module('aipaisheApp')

.controller('AppCtrl', function($scope, $http, $location, $sce, $mdDialog) {
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
            $location.path('/event/' + result);
        }, function() {});
    };

    $scope.showCreateEventPanel = function(event) {
        $mdDialog.show({
                controller: DialogController,
                templateUrl: 'create.panel.template.html',
                scope: $scope,
                preserveScope: true,
                parent: angular.element(document.body),
                targetEvent: event,
                clickOutsideToClose: true,
                fullscreen: false // Only for -xs, -sm breakpoints.
            })
            .then(function(answer) {
                $scope.status = 'Confirming';
                var url = "createevent?owner=1&" + "name=" + $scope.eventName + "&date=" + new Date($scope.eventDate).toISOString() + "&venue=" + $scope.eventVenue;
                $http.get(url).then(function(response) {
                    console.log("success creating event! Event ID is " + response.data.eventId);
                    $location.path('/event/' + response.data.eventId);
                }, function(error) {
                    console.log("error when hitting server");
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