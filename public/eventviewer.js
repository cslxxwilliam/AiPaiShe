angular.module('EventViewerApp', ['ngMaterial'])
    .component('eventDetail', {
        templateUrl: 'eventviewer.html',
        controller: ['$routeParams',
          function EventDetailController($routeParams) {
            this.eventId = $routeParams.eventId;
            this.eventName = 'Dummy Event';
            this.eventDate = new Date();
            this.eventVenue = 'AiPaiShe Office HK';
          }
        ]
      });