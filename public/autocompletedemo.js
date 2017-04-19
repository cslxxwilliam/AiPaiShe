(function () {
  'use strict';
  angular
      .module('autocompleteDemo', ['ngMaterial'])
      .controller('DemoCtrl', DemoCtrl);

  function DemoCtrl ($timeout, $q, $log) {
    var self = this;

    self.simulateQuery = false;
    self.noCache = true;
    self.isDisabled    = false;

    // list of `city` value/display objects
    self.cities        = loadAll();
    self.querySearch   = querySearch;
    self.selectedItemChange = selectedItemChange;
    self.searchTextChange   = searchTextChange;

    self.newCity = newCity;

    function newCity(city) {
      alert("Sorry! You'll need to create a Constitution for " + city + " first!");
    }

    // ******************************
    // Internal methods
    // ******************************

    /**
     * Search for cities... use $timeout to simulate
     * remote dataservice call.
     */
    function querySearch (query) {
      var results = query ? self.cities.filter( createFilterFor(query) ) : self.cities,
          deferred;
      if (self.simulateQuery) {
        deferred = $q.defer();
        $timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
        return deferred.promise;
      } else {
        return results;
      }
    }

    function searchTextChange(text) {
      $log.info('Text changed to ' + text);
    }

    function selectedItemChange(item) {
      $log.info('Item changed to ' + JSON.stringify(item));
    }

    /**
     * Build `cities` list of key/value pairs
     */
    function loadAll() {
      var allCities = 'Beijing, Shanghai, Guangzhou, Shenzhen, Dalian, Wuxi';

      return allCities.split(/, +/g).map( function (city) {
        return {
          value: city.toLowerCase(),
          display: city
        };
      });
    }

    /**
     * Create filter function for a query string
     */
    function createFilterFor(query) {
      var lowercaseQuery = angular.lowercase(query);

      return function filterFn(city) {
        return (city.value.indexOf(lowercaseQuery) === 0);
      };

    }
  }
})();