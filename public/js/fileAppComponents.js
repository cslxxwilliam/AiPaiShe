var uploadApp = angular.module('fileApp');

//var uploadTemplate = require('../fileupload.html');

var uploadComponent = {
        bindings: {
            eventId: '@'
        },
    //    template: uploadTemplate,
    templateUrl: 'fileupload.html',
    controller: UploadComponentController,
    controllerAs: 'uploadCtrl'
};

uploadApp.component('fileUpload', uploadComponent);

UploadComponentController.$inject = ['$scope'];

function UploadComponentController($scope) {
    console.log("eventId: "+$scope.eventId);
    $scope.filename = '';
    // hard-coded event ID for demo only
    $scope.eventId = 1;
    // $scope.eventId = this.eventId;

    // TODO: need to pass the event ID into this controller
//    alert('UploadComponentController eventId = ' + this.eventId);

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