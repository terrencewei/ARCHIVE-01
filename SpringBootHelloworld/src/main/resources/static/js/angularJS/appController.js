// CONTROLLER
mySpringBootApp.controller('homeController',['$scope', '$location',
                                        function($scope, $location){
	$scope.submit = function() {
		$location.path("/forecast");
	};
}]);

mySpringBootApp.controller('forecastController',['$scope','$routeParams',
                                            function($scope,  $routeParams){
	$scope.isRouteParamsDaysValid = typeof($routeParams.days)!='undefined';
	$scope.days = $routeParams.days || 3; // means default value is 3

	//$scope.weatherResult = weatherService.getWeather($scope.city);

	$scope.formatPM25 = function(pm25) {
		return "PM2.5指数： " + pm25;
	};

	$scope.formatDescription = function(description ) {
		return "建议： " + description;
	};

}]);