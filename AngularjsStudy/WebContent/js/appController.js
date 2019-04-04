// CONTROLLER
weatherApp.controller('homeController',['$scope', '$location', 'cityService', 
                                        function($scope, $location, cityService){
	$scope.city = cityService.city;
	
	$scope.$watch('city', function(){
		cityService.city = $scope.city;
	});
	
	$scope.submit = function() {
		$location.path("/forecast");
	};
}]);

weatherApp.controller('forecastController',['$scope','cityService', 'weatherService','$routeParams',
                                            function($scope, cityService, weatherService, $routeParams){
	$scope.city = cityService.city;
	$scope.isRouteParamsDaysValid = typeof($routeParams.days)!='undefined';
	$scope.days = $routeParams.days || 3; // means default value is 3
	
	$scope.weatherResult = weatherService.getWeather($scope.city);
	
	$scope.formatPM25 = function(pm25) {
		return "PM2.5指数： " + pm25;
	};
	
	$scope.formatDescription = function(description ) {
		return "建议： " + description;
	};
	
	$scope.$watch('city', function(){
		cityService.city = $scope.city;
	});
}]);