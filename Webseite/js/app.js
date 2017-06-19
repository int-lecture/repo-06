var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider

        .when("/", {
            templateUrl : "views/home.html"
        })
        .when("/firma", {
            templateUrl : "views/firma.html"
        })
        .when("/produkt", {
            templateUrl : "views/produkt.html"
        })
        .when("/impressum", {
            templateUrl : "views/impressum.html"
        })
        .when("/newsletter", {
            templateUrl : "views/newsletter.html"
        })
        .when("/login", {
            templateUrl : "views/login.html",
            Controller: 'LoginController'
        })
        .when("/registrieren", {
            templateUrl : "views/registrieren.html",
            Controller :'RegController'
        })
        .when("/messenger", {
            templateUrl: "views/messenger",
            Controller: 'MessengerController'
        })
        /*
        .when('/messenger/home', {
            templateUrl: 'home/home.view.html'
        })
        .when('/messenger/login', {
            templateUrl: 'login/login.view.html'
        })

        .when('/messenger/register', {
            templateUrl: 'register/register.view.html'
        })
        */
        .otherwise({ redirectTo: '/' })
});


