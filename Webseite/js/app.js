var app = angular.module('app', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider

        .when("/", {
            template : "<div class='container'><h1>Hallo und Herzliche Willkommen bei der Webseite von SecureMessenger</h1></div>"
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
        .when("/registrieren", {
            templateUrl : "views/registrieren.html"
        })
        .when("/messenger", {
            templateUrl : "views/messenger.html"
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
        .otherwise({ redirectTo: '/' });
});
//controller raus ins partial. view stuff ins partial. controller in ordner referenzieren auf ng-controller


