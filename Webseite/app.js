var app = angular.module('app', ['ngRoute'])
        .config(config)
        .run(run);

//controller raus ins partial. view stuff ins partial. controller in ordner referenzieren auf ng-controller

config.$inject = ['$routeProvider', '$locationProvider'];
function config($routeProvider)
{
    $routeProvider
        .when('/messenger/home', {
            templateUrl: 'home/home.view.html',
        })

        .when('/messenger/login', {
            templateUrl: 'login/login.view.html'
        })

        .when('/messenger/register', {
            templateUrl: 'register/register.view.html'
        })
        .when("/", {
            template : "<div class='container'><h1>Hallo und Herzliche Willkommen bei der Webseite von SecureMessenger</h1></div>"
        })
        .when("/firma", {
            templateUrl : "Partials/firma.html"
        })
        .when("/produkt", {
            templateUrl : "Partials/produkt.html"
        })
        .when("/impressum", {
            templateUrl : "Partials/impressum.html"
        })
        .when("/registrieren", {
            templateUrl : "Partials/registrieren.html"
        })
        .when("/messenger", {
            templateUrl : "Partials/messenger.html"
        })

        .otherwise({ redirectTo: '/' });
}

run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
function run($rootScope, $location, $cookies, $http) {
    // keep user logged in after page refresh
    /*$rootScope.globals = $cookies.getObject('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
            $location.path('/login');
        }
    });*/
}