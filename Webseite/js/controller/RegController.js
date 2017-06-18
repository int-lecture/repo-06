/**
 * Created by dennis on 18.06.17.
 */
app.controller('RegController', function ($scope, $location) {
    $scope.goTo = function () {
        $location.path('/login')
    }

    $scope.reg = function () {
        var dataObject = {'pseudonym': $("#pseudonym"), 'password': $("#pseudonym"),
        'name': $("#name")}

        $http.put('http://141.19.142.60/register',
            JSON.stringify(dataObject),
            {
                headers:{
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }
        ).then(function success() {
            $location.path('/login');
        }, function error(response) {

        })
    }
});