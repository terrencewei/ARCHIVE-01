// CONFIG
weatherApp.config(function ($routeProvider, $sceDelegateProvider){
	$routeProvider
	.when('/',{
		templateUrl: 'pages/home.html',
		controller: 'homeController'
	})
	.when('/forecast',{
		templateUrl: 'pages/forecast.html',
		controller: 'forecastController'
	})
	.when('/forecast/:days',{
		templateUrl: 'pages/forecast.html',
		controller: 'forecastController'
	});
//	$sceDelegateProvider.resourceUrlWhitelist([
//	   // Allow same origin resource loads.
//	   'self',
//	   // Allow loading from our assets domain.  Notice the difference between * and **.
//	   'http://srv*.assets.example.com/**'
//	 ]);
//	
//	 // The blacklist overrides the whitelist so the open redirect here is blocked.
//	 $sceDelegateProvider.resourceUrlBlacklist([
//	   'http://myapp.example.com/clickThru**'
//	 ]);
	$sceDelegateProvider.resourceUrlWhitelist(['**']);
});