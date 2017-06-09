var uploadApp = angular.module('fileApp');

//var uploadTemplate = require('../fileupload.html');

var uploadComponent = {
    //    bindings: {
    //        foo: ‘=’,
    //        value: ‘@’,
    //        oneWay: ‘<’,
    //        twoWay: ‘=’,
    //        callback: ‘&’
    //    },
    //    template: uploadTemplate,
    templateUrl: 'fileupload.html',
    controller: UploadComponentController,
    controllerAs: 'uploadCtrl'
};

uploadApp.component('fileUpload', uploadComponent);

UploadComponentController.$inject = ['$scope'];

function UploadComponentController($scope) {
    $scope.partialDownloadLink = 'http://localhost:8080/download?filename=';
    $scope.filename = '';

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