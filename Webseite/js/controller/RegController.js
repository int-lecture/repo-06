/**
 * Created by dennis on 18.06.17.
 */
app.controller('RegController', function ($scope, $location, $http) {


    $scope.goTo = function () {
        $location.path('/login')
    };

    $scope.reg = function (store) {

        $scope.myData.url = store.loginIp;

        var dataObject = {
                user: store.email,
                pseudonym: store.pseudonym,
                password: store.password
        };

        $http({
            method: 'jsonp',
            url: 'http://'+$scope.myData.url+':5002/register',
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