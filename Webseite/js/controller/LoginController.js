/**
 * Created by dennis on 14.06.17.
 */
app.controller('LoginController', function ($scope, $location, $http) {
    $scope.goTo = function () {
        $location.path('/registrieren')
    };
    $scope.login = function (store) {

        var dataObject = {
            email: store.email,
            password: store.password
        };

        $http({
            method: 'post',
            url: 'http://141.19.142.60:5001/login',
            headers: {
                'Content-Type': 'application/json'
            },
            data : dataObject
        }).then(function(){
            window.alert("Success, dies dient nur zum Debuggen")}, function () {
            window.alert("Leider ist ein Fehler aufgetreten!");
        })
    };
});