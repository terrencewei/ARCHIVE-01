// DIRECTIVES
weatherApp.directive('weatherTitle', function(){
	return {
		restrict: 'E',
		templateUrl: 'pages/directives/weatherTitle.html',
		replace: true,
		scope: {
			cityName: "@",
			result: "=",
			formattedP: "&",
			dateFormat: "@",
			timeZone: "@"
		}
	}
});

weatherApp.directive('chooseDays', function(){
	return {
		restrict: 'E',
		templateUrl: 'pages/directives/chooseDays.html',
		replace: true,
		scope: {
			days: "@",
			isShowTips: "="
		}
	}
});

weatherApp.directive('weatherReport', function(){
	return {
		restrict: 'E',
		templateUrl: 'pages/directives/weatherReport.html',
		replace: true,
		scope: {
			weatherDay: "="
		}
	}
});

weatherApp.directive('lifeReport', function(){
	return {
		restrict: 'E',
		templateUrl: 'pages/directives/lifeReport.html',
		replace: true,
		scope: {
			lifeDay: "=",
			formattedDescription: "&"
		}
	}
});