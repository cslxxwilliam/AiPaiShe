var aipaisheApp = angular.module('aipaisheApp');

var uploadComponent = {
    bindings: {
        fileEventId: '&'
    },
    templateUrl: 'fileupload.html',
    controller: UploadComponentController,
    controllerAs: 'vm'
};

aipaisheApp.component('fileUpload', uploadComponent);

UploadComponentController.$inject = ['$scope', '$mdDialog'];

function UploadComponentController($scope, $mdDialog) {

    $scope.filename = '';

    var vm = this;

    vm.$onInit = function() {
        $scope.eventId = vm.fileEventId();
    };

    $scope.uploadFile = function() {
        $scope.processDropzone();
    };

    $scope.reset = function() {
        $scope.resetDropzone();
    };

    $scope.onFileSelect = function($files) {
        Upload.upload({
            url: 'my/upload/url',
            file: $files,
        }).progress(function(e) {}).then(function(data, status, headers, config) {
            // file is uploaded successfully
            console.log(data);
        });
    }

    $scope.$on('uploadcomplete', function(event, data) {
        $mdDialog.show(
            $mdDialog.alert()
            .parent(angular.element(document.querySelector('#popupContainer')))
            .clickOutsideToClose(true)
            .title('Confirmation')
            .textContent(data)
            .ariaLabel('Alert Dialog Demo')
            .ok('Got it!')
            .targetEvent(event)
        );
    });

}