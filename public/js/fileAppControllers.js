angular.module('fileApp').controller('fileCtrl', ['$scope',
    function ($scope) {

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
          }).progress(function(e) {
          }).then(function(data, status, headers, config) {
            // file is uploaded successfully
            console.log(data);
          });
         }

    }

    ]);