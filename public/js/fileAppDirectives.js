angular.module('fileApp').directive('dropzone', function() {
    return {
        restrict: 'C',
        link: function(scope, element, attrs) {

            var config = {
                url: 'http://localhost:8080/upload',
                acceptedFiles: 'image/*',
                maxFilesize: 100,
                paramName: "uploadfile",
                maxThumbnailFilesize: 10,
                parallelUploads: 1,
                autoProcessQueue: false,
                sending: function(file, xhr, formData) {
                   formData.append("event_id", scope.eventId);  //name and value
                   formData.append("event_name", 'aipaishe'); //name and value
               }
           };

           var eventHandlers = {
            'addedfile': function(file) {
                scope.file = file;
                if (this.files[1]!=null) {
                    this.removeFile(this.files[0]);
                }
                scope.$apply(function() {
                    scope.fileAdded = true;
                });
            },

            'success': function (file, response) {
            },

            'complete': function (file) {
                if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
                    console.log('Upload completed. ');
                    location.reload();
                }
                this.removeFile(file);
            }

            };

            dropzone = new Dropzone(element[0], config);

            angular.forEach(eventHandlers, function(handler, event) {
                dropzone.on(event, handler);
            });

            scope.processDropzone = function() {
                dropzone.processQueue();
            };

            scope.resetDropzone = function() {
                dropzone.removeAllFiles();
            }
        }
    }
});