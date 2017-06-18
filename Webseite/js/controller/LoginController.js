/**
 * Created by dennis on 14.06.17.
 */
app.controller('LoginController', function ($scope, $location) {
    $scope.goTo = function () {
        $location.path('/registrieren')
    }
});