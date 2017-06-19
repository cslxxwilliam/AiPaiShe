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

    vm.galleriaAlbum = [];

    Galleria.loadTheme('galleria/themes/classic/galleria.classic.min.js');
    Galleria.run('.galleria', {
        dataSource: vm.galleriaAlbum
    });

    console.log(Galleria.get(0));

    $http.get('get-event-by-id-json?id=' + $routeParams.eventId).then(function(response) {
        vm.eventData = response.data;
        // convert the returned date from millisecond to date type
        vm.eventDate = new Date(vm.eventData.eventDate);
    }, function(error) {
        console.log('error when hitting server' + error);
    });

    // load the event photos for the first time
    $http.get('photos?eventId=' + $routeParams.eventId).then(function(response) {
        vm.photoLocations = response.data;
        vm.refreshGallery();
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
            vm.photoLocations = response.data;
            vm.refreshGallery();
        }, function(error) {
            console.log('error when hitting server' + error);
        });
    });

    // function for refreshing the Gallery
    vm.refreshGallery = function() {
        console.log('refreshing gallery...');
        var count = 0;

        vm.galleriaAlbum.splice(0,vm.galleriaAlbum.length);

        vm.photoLocations.forEach(function(locationData) {
            count = count + 1;
            var jsonData = {};
            var locationPath = locationData.location;
            jsonData['image'] = locationPath;
            jsonData['thumb'] = locationPath;
            jsonData['big'] = locationPath;
            jsonData['title'] = 'Event Title ' + count;
            jsonData['description'] = 'Photo Description ' + count;
            vm.galleriaAlbum.push(jsonData);
        });

        Galleria.get(0).load(vm.galleriaAlbum);
    }

    Galleria.ready(function(options) {
        console.log('Galleria is ready...');

        // 'this' is the gallery instance
        // 'options' is the gallery options
        var gallery = this;

        gallery.bind('image', function(e) {
            Galleria.log('Now viewing ' + e.imageTarget.src);

            // lets make galleria open a lightbox when clicking the main image:
            $(e.imageTarget).click(gallery.proxy(function() {
                // Toggle Fullscreen Mode
                // gallery.toggleFullscreen();
                // Open a Lightbox
                gallery.openLightbox();
            }));
        });

    });

    Galleria.on('data',function(){
        var gallery = this;
        window.setTimeout(function(){
            gallery.lazyLoadChunks(3);
        },10);
    });
}