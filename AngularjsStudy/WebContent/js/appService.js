// SERVICE
weatherApp.service('cityService', function(){
	this.city = "成都";
});

weatherApp.service('weatherService', ['$resource', function($resource){
	this.getWeather = function(city) {
		var weatherAPI = $resource("http://api.map.baidu.com/telematics/v3/weather", {
			//callback: 'JSON_CALLBACK'
			// 在高版本的angularjs该值已经废弃:
			// https://docs.angularjs.org/error/$http/badjsonp
		},{
			get: {
				method: 'JSONP'
			}
		});
		
		return weatherAPI.get({ 
			location: city,
			output: "json",
			ak: "ebSC2cxGppZ5qEUlcmv0AqesbI9BaGN5"// app key is from http://lbsyun.baidu.com/apiconsole/key
		});
	};
	
}]);