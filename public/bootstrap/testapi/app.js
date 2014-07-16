
var myApp = angular.module('myApp', []);

myApp.controller('AlbumCtrl', function($scope,$timeout,$filter,$http) {

    var defcounter = 0;
    var mytimeout;
    const START_TEXT = 'Start';
    const STOP_TEXT = 'Stop';
    const RESET_TEXT = 'Reset';
    const LAP_TEXT = 'Lap';
    var currentTime; //to store the current time on main stop watch
    $scope.laps = [];
    $scope.counter = defcounter;
    $scope.counterSec = defcounter;
    $scope.stopped = false;
    $scope.startted = false;
    $scope.lapbuttonText= LAP_TEXT;
    $scope.startbuttonText= START_TEXT;

    $scope.greeting;

   $scope.hello = function(){
    $http.get('http://rest-service.guides.spring.io/greeting').
        success(function(data) {
            $scope.greeting = data;
        });
   }

  var apiurl = 'https://maps.googleapis.com/maps/api/timezone/json?';

   $scope.timezone = function(){
   //  // $http.get(apiurl,{params: {location: '39.6034810,-119.6822510',timestamp:'1331161200',key=:'AIzaSyD9AtmXZIHU25uwMvOLOgcRReEb256A-wA'}}.
   //  //     success(function(data) {
   //  //         $scope.greeting = data;
   //  //     });

    $http({
      url: apiurl,
      method: "GET",
      params: {location: '39.6034810,-119.6822510',timestamp:'1331161200',key:'AIzaSyD9AtmXZIHU25uwMvOLOgcRReEb256A-wA'},
      headers: {"X-Requested-With":'access-control-allow-origin'}
    })
    . success(function(data) {
            $scope.greeting = data;
        });

   }






    //---------Functions--------
    $scope.onTimeout = function(){

        $scope.counter++;
        $scope.counterSec++;

        mytimeout = $timeout($scope.onTimeout,10);

    }


   $scope.startBtnEvent = function(){
        if(!$scope.startted){
            $scope.start();
         }else{
            $scope.stop();
        }
    }

 $scope.pauseBtnEvent = function(){
    //only active Pause button when timer has already started
    if($scope.startted){
      $scope.lap();
    }else{
      $scope.reset();
    }
  }

 $scope.start = function(){
          $scope.startted = !$scope.startted;
          $scope.startbuttonText = STOP_TEXT;
          $scope.lapbuttonText= LAP_TEXT;
     mytimeout = $timeout($scope.onTimeout,10);
 }

  $scope.stop = function(){
    $timeout.cancel(mytimeout);
             $scope.lapbuttonText= RESET_TEXT;
             $scope.startbuttonText = START_TEXT;
             $scope.startted = !$scope.startted;
  }


    $scope.lap = function(){

            currentTime = $filter('formatTimer')($scope.counterSec);
            $scope.laps.push(currentTime);
            $scope.counterSec = defcounter;

               }

    $scope.reset = function(){
        $timeout.cancel(mytimeout);
            $scope.lapbuttonText= LAP_TEXT;
            $scope.startbuttonText = START_TEXT;
            $scope.counter = defcounter;
            $scope.counterSec = defcounter;
            $scope.startted = false;
            $scope.laps = [];
    }



    //.................

   //  $scope.reset = function(){
   //      $timeout.cancel(mytimeout);
   //          $scope.lapbuttonText='Pause';
   //          $scope.startbuttonText = 'Start';
   //          $scope.counter = defcounter;
   //          $scope.startted = false;
   //  }


   //  $scope.resume = function(){
   //       mytimeout = $timeout($scope.onTimeout,10);
   //       $scope.lapbuttonText='Pause';
   //  }
   //  $scope.pause = function(){
   //  //only active Pause button when timer has already started
   //  if($scope.startted){
   //      if(!$scope.stopped){
   //          $timeout.cancel(mytimeout);
   //          $scope.lapbuttonText='Resume';
   //      }
   //      else
   //      {
   //        $scope.resume();
   //      }
   //          $scope.stopped=!$scope.stopped;
   //  }
   // }
});

//-----Filter-----
myApp.filter('formatTimer', function() {
  return function(input)
    {
        function z(n) {return (n<10? '0'  : '') + n;}
        var milsecs = input%100;
        var seconds = Math.floor(input/100)%60;
        var minutes = Math.floor(input/6000)%60;

        // var seconds = input% 60;
        // var minutes = Math.floor(input / 60)%60;
        // var hours = Math.floor(input / 3600)%24;
         var result ;
         //Display two digits when hours is 0, otherwise display three digits
         result =  (z(minutes) +':'+z(seconds)+'.'+z(milsecs));
        //Update the currentTime for lap record;
         currentTime = result;
         return result;
    };
});



