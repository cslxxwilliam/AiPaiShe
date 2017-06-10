var uploadApp = angular.module('fileApp');

//var uploadTemplate = require('../fileupload.html');

var uploadComponent = {
    bindings: {
        fileId: '&',
    },
    templateUrl: 'fileupload.html',
    controller: UploadComponentController,
    controllerAs: 'uploadCtrl'
};

uploadApp.component('fileUpload', uploadComponent);

UploadComponentController.$inject = ['$scope'];

function UploadComponentController($scope) {

    $scope.filename = '';

    var vm = this;

    vm.$onInit = function() {
        $scope.eventId = vm.fileId();
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
}