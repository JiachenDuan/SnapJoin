(function(){

    var app = angular.module('store-products',[]);
    //Product panel Directive
    app.directive('productPanel',function(){
        return{
            restrict: 'E',
            templateUrl: 'product-panel.html',
            controller:function(){
                this.tab = 1; //same as doing ng-init="tab = 1" on HTML, but better do it here
                this.selectTab = function (setTab) {
                this.tab = setTab;
            }; //ng-click="panel.selectTab(1)"
            this.isSelected = function (checkTab) {
                return this.tab === checkTab;
            };  
          },
          controllerAs:'panel'
        };
    });

    //Review Form Directive Controller
    app.directive('productReviewForm', function(){
        return {
            restrict: 'E',
            templateUrl: 'product-review-form.html',
            controller:function(){
              // ng-controller="ReviewController as reviewCtrl" 
               //better to create a empty variable, than to use review without declaration on HTML
            this.review = {};
            this.addReview = function (product) {
            //add current review into product.reviews
            this.review.createdOn = Date.now();
            product.reviews.push(this.review);
            //NOTE: need to set this.review to brand new object, other wiese
            //the form still has all previous values
            this.review = {}; //Clear out the review, so the form will reset
             };
           }, 
            controllerAs: 'reviewCtrl'
        };
    });

    //NOTE:Custom Directives: dash in HTML translates to ...camelCase in JavaScript
    app.directive('productTitle', function () {
        return {
            // restrict: 'A', //A for Attribute, type of Directive, add 'product-title' to tag attribute
            restrict: 'E',   //E for Element, type of Dirctive
            templateUrl: 'product-title.html' //URL of the template

        };
    });
    
})();