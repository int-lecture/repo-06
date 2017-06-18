/**
 * Created by dennis on 14.06.17.
 */
app.controller('LoginController', function ($scope, $location, $http) {
    $scope.goTo = function () {
        $location.path('/registrieren')
    };
    $scope.login = function () {

        var dataObject ={'user': $("#email").val(), 'password': $("#pwd").val()};
        $http.post('http://141.19.142.60/login',
            JSON.stringify(dataObject),
            {
                headers:{
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }
        ).then(function success(response) {
            $scope.token = response.token;
            $scope.exDate = response.exDate;
            $location.path('/messenger');
        }, function error(response) {

        });
    }
});