(function() {
	
	var app = angular.module('events',[]);

    app.directive('eventsTitle',function(){
    	return {
        restrict:'E',
    	templateUrl: 'events-title.html'
     };
    });

    app.controller('eventsController',['$http',function($http){

      var events = this;
      events.myevents = [];
      $http.get('/events/creator/53bb83c444ae82a2e2a0549b').success(function(data){
       events.myevents = data;
    
      });
    }]);

    // app.controller('eventsController',function(){

    //   // var events = this;
    //   // events.myevents = [];
    //   // $http.get('/events/creator/53bb83c444ae82a2e2a0549b').success(function(data){
    //   //  events.myevents = data;
    //   this.myevents = gems;
      
    //   });
  
   //  var gems = [
   //   {
 		// id: '53bc7e5344aeb1d29ac28f3c'
   //   },
   //   {
   //      id: '53bc7e5344aeb1d29ac28f3c'
   //   }
   //  ];
})();