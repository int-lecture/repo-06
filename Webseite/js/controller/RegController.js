/**
 * Created by dennis on 18.06.17.
 */
app.controller('RegController', function ($scope, $location, $http) {


    $scope.goTo = function () {
        $location.path('/login')
    };

    $scope.reg = function (store) {

        var registrationIP = store.loginIp;

        var dataObject = {
                user: store.email,
                pseudonym: store.pseudonym,
                password: store.password
        };

        $http({
            method: 'jsonp',
            url: 'http://'+registrationIP+'/register',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data : JSON.stringify(dataObject)
        }).then(function(){
            window.alert("User erfolgreich angelegt!")}, function () {
            window.alert("Leider ist ein Fehler aufgetreten");
        });
    };
});