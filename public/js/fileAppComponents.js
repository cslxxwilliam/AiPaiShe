var aipaisheApp = angular.module('aipaisheApp');

var uploadComponent = {
    bindings: {
        fileEventId: '&',
        refreshPhotos: '&'
    },
    templateUrl: 'fileupload.html',
    controller: UploadComponentController,
    controllerAs: 'uploadCtrl'
};

aipaisheApp.component('fileUpload', uploadComponent);

UploadComponentController.$inject = ['$scope','$mdDialog'];

function UploadComponentController($scope,$mdDialog) {

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

    $scope.showConfirmation = function(msg) {
        // Appending dialog to document.body to cover sidenav in docs app
        // Modal dialogs should fully cover application
        // to prevent interaction outside of dialog
        $mdDialog.show(
          $mdDialog.alert()
            .parent(angular.element(document.querySelector('#popupContainer')))
            .clickOutsideToClose(true)
            .title('Confirmation')
            .textContent(msg)
            .ariaLabel('Alert Dialog Demo')
            .ok('Got it!')
            .targetEvent()
        );
    }

    $scope.refreshPhotos = function(){
    vm.refreshPhotos();
    }
}