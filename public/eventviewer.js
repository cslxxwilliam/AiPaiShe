angular.module('EventViewerApp', ['ngMaterial'])
    .component('eventDetail', {
        templateUrl: 'eventviewer.html',
        controller: ['$http','$routeParams','$scope',
          function EventDetailController($http,$routeParams, $scope) {
            var self = this;
            self.readonly = true;
            $http.get('get-event-by-id-json?id=' + $routeParams.eventId).then(function(response){
                        self.eventData = response.data;
                        // convert the returned date from millisecond to date type
                        self.eventDate = new Date(self.eventData.eventDate);
                  }, function(error){
                  console.log('error when hitting server' + error);
                  });

            $http.get('photos?eventId=' + $routeParams.eventId).then(function(response){
                        self.photoLocations = response.data
                  }, function(error){
                  console.log('error when hitting server' + error);
                  });

            var $grid = $('.grid').masonry({
                  itemSelector: '.grid-item',
                  percentPosition: true,
                  columnWidth: '.grid-sizer'
                });
            // layout Isotope after each image loads
            $grid.imagesLoaded().progress( function() {
                    $grid.masonry();
            });

            self.getEventId = function () {
                return $routeParams.eventId;
            }

            function refreshPhotos(){

            }
          }
        ]
      });