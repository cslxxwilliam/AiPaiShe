var aipaisheApp = angular.module('aipaisheApp');

var eventDetailComponent = {
    bindings: {},
    templateUrl: 'eventviewer.html',
    controller: EventDetailController,
    controllerAs: 'vm'
};

aipaisheApp.component('eventDetail', eventDetailComponent);

EventDetailController.$inject = ['$http', '$routeParams', '$scope'];

function EventDetailController($http, $routeParams, $scope) {
    var vm = this;
    vm.readonly = true;
    $http.get('get-event-by-id-json?id=' + $routeParams.eventId).then(function(response) {
        vm.eventData = response.data;
        // convert the returned date from millisecond to date type
        vm.eventDate = new Date(vm.eventData.eventDate);
    }, function(error) {
        console.log('error when hitting server' + error);
    });

    // load the event photos for the first time
    $http.get('photos?eventId=' + $routeParams.eventId).then(function(response) {
        vm.photoLocations = response.data
    }, function(error) {
        console.log('error when hitting server' + error);
    });

    var $grid = $('.grid').masonry({
        itemSelector: '.grid-item',
        percentPosition: true,
        columnWidth: '.grid-sizer'
    });
    // layout Isotope after each image loads
    $grid.imagesLoaded().progress(function() {
        $grid.masonry();
    });

    vm.getEventId = function() {
        return $routeParams.eventId;
    }

    // refresh the photos after file upload
    $scope.$on('uploadcomplete', function(event, data) {
        $http.get('photos?eventId=' + $routeParams.eventId).then(function(response) {
            vm.photoLocations = response.data
        }, function(error) {
            console.log('error when hitting server' + error);
        });
    });

    // initialize Galleria
    (function() {
        Galleria.loadTheme('galleria/themes/classic/galleria.classic.min.js');
        Galleria.run('.galleria');
    }());
}
