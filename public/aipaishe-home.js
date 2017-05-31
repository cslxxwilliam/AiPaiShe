angular.module('aipaisheHomeDirective', [])
.directive('aipaisheHome',['$http', function($http){

  function link (scope){
      var $grid = $('.grid').masonry({
        itemSelector: '.grid-item',
        percentPosition: true,
        columnWidth: '.grid-sizer'
      });
      // layout Isotope after each image loads
      $grid.imagesLoaded().progress( function() {
        $grid.masonry();
      }
  );
  }

  return {
    restrict: 'E',
    templateUrl: 'aipaishe-home.html',
    link: link
  };
}])

.directive( 'goClick', function ( $location ) {
  return function ( scope, element, attrs ) {
    var path;

    attrs.$observe( 'goClick', function (val) {
      path = val;
    });

    element.bind( 'click', function () {
      scope.$apply( function () {
        $location.path( path );
      });
    });
  };
});

;