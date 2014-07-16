// Wrapping your Javascript in a closure is a good habit
//Directives- HTML annotations that trigger Javascript behaviors
//Modules - Where our application components live
//Controllers - Where we add application behavior
//Expressions - How values get displayed within the page

(function () {
    // add denpendency store-products into store
    var app = angular.module('store', ['store-products']);


    //Gallery controller
    app.controller('GalleryController', function () {
        this.current = 1;
        this.setCurrent = function (currentGallery) {
            if (this.current === 0) {
                this.current = 1;
            } else {
                this.current = 0;
            }
            //  this.current = currentGallery || 0;
        };
    });
    //Panel controller
    // app.controller('PanelController', function () {
    //     this.tab = 1; //same as doing ng-init="tab = 1" on HTML, but better do it here
    //     this.selectTab = function (setTab) {
    //         this.tab = setTab;
    //     }; //ng-click="panel.selectTab(1)"
    //     this.isSelected = function (checkTab) {
    //         return this.tab === checkTab;
    //     };
    // });
    //this controller is attached inside our app
    app.controller('StoreController',['$http', function ($http) {
        //product is a property of our controller, you can add any property you want, like Ki
         this.products = gems;
      //  var store = this; // inorder to set databack to store from Service
        //NOTES: service call may take a while, to avoid erro when the page
        //loaded, better to set empty array to store first. Later on when the data get back
        //back from request, the data will be automaticly update to the page.
        // We need to initialize products to an empty array, since the page will
        // render before our data returns from out get request.
     //   store.products = [ ]; 
        //#### SERVICE####
        // Services give your Contrller additional functionality, like...
        //1)Fetching JSON data from a web service with $http
        //2)Logging messages to the JavaScript console with $log
        //3)Filtering an array with $filter

        //e.g. It is using direct injection to use Services in controller
        // app.controller('StoreController',['$http','$log', function ($http,$log) {}]);
        // first $http-Storecontroller need the $http service
        // second $http-so $http gets injected as an argument

        //$http Service is how we make an async request to a server
        //-$http({method: 'GET', url:'/products.json'});
        //-or using one of the shortcut methods
        //   -$http.get('/products.json',{ apikey: 'myApiKey' });
        //-Both return a Promise object with .success() and .error()
        //-If we tell $http to fetch JSON, the result will be automatically
        //decoded into JavaScript objects and arrays

        // NOTES: other syntax 
        // 1) $http.post('/path/to/resource.json', {param: 'value'});
        // 2) $http.delete('/path/to/resource.json');
        // or any other HTTP method by using a config object
        // 1) $http({method: 'OPTIONS', url: '/path/to/resurce.json'});
        // 2) $http({method: 'PATH', url: '/path/to/resource.json'});
        // 3) $http({method: 'TRACE', url: '/path/to/resource/json'});

        //Fetch the contents of product.json
        // $http.get('/assets/bootstrap/products.json').success(function(data){
        // store.products  = data;
        // });  
    }]);

    var gems = [
        {
            name: 'Dodecahedron',
            price: 2.95,
            description: '. . .',
            canPurchase: true,
            soldOut: false,
            recipe: '1404862035883',
            images: [
                {
                    full: 'img/pic-01-full.png',
                    thumb: 'img/pic-01-thumb.png'
                },
                {
                    full: 'img/pic-02-full.png',
                    thumb: 'img/pic-01-thumb.png'
                }
            ],
            reviews: [
                {
                    stars: 5,
                    body: "I love this product!",
                    author: "joe@thomas.com",
                    createdOn: 1404862035883
                },
                {
                    stars: 1,
                    body: "This product sucks",
                    author: "tim@hater.com",
                    createdOn: 1404862035883
                }
            ],
        },
        {
            name: 'Pentagonal Gem',
            price: 5.95,
            description: '. . .',
            canPurchase: true,
            soldOut: false,
            recipe: '1404862239143',
            images: [
                {
                    full: 'img/pic-01-full.png',
                    thumb: 'img/pic-01-thumb.png'
                },
                {
                    full: 'img/pic-02-full.png',
                    thumb: 'img/pic-01-thumb.png'
                }
            ],
            reviews: [
                {
                    stars: 5,
                    body: "I love this product!",
                    author: "joe@thomas.com",
                    createdOn: 1404862035883
                },
                {
                    stars: 1,
                    body: "This product sucks",
                    author: "tim@hater.com",
                    createdOn: 1404862035883
                }
            ],
        }
    ];
    // var gem = {
    // 	name: 'Dodecahedron',
    // 	price: 2.95,
    // 	description: '. . .',
    // 	canPurchase: true,
    // 	soldOut: false
    // }
})();