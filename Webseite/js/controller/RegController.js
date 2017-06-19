/**
 * Created by dennis on 18.06.17.
 */
app.controller('RegController', function ($scope, $location, $http) {

    $scope.test = true;
    $scope.goTo = function () {
        $location.path('/login')
    };

    $scope.reg = function (store) {

        var dataObject = {
                user: store.email,
                pseudonym: store.pseudonym,
                password: store.password
        };

        $http({
            method: 'put',
            url: 'http://141.19.142.57:5002/register',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            data : JSON.stringify(dataObject)
        }).then(function(){
            window.alert("User erfolgreich angelegt!");}, function () {
            window.alert("Leider ist ein Fehler aufgetreten");
        });
    };
});