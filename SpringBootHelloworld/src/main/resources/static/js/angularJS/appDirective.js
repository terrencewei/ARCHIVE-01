// DIRECTIVES
mySpringBootApp.directive('weatherTitle', function(){
	return {
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

mySpringBootApp.directive('chooseDays', function(){
	return {
		templateUrl: 'pages/directives/chooseDays.html',
		replace: true,
		scope: {
			days: "@",
			isShowTips: "="
		}
	}
});

mySpringBootApp.directive('weatherReport', function(){
	return {
		templateUrl: 'pages/directives/weatherReport.html',
		replace: true,
		scope: {
			weatherDay: "="
		}
	}
});

mySpringBootApp.directive('lifeReport', function(){
	return {
		templateUrl: 'pages/directives/lifeReport.html',
		replace: true,
		scope: {
			lifeDay: "=",
			formattedDescription: "&"
		}
	}
});