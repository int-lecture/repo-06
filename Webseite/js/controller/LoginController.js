/**
 * Created by dennis on 14.06.17.
 */
app.controller('LoginController', function ($scope, $location, $http) {

    $scope.goTo = function () {
        $location.path('/registrieren')
    };
    $scope.login = function (store) {

        var url = store.loginIp;

        var dataObject = {
            user: store.user,
            pseudonym: store.pseudonym,
            password: store.password
        };

        $http({
            method: 'post',
            url: 'http://'+url+'/login',
            headers: {
                'Content-Type': 'application/json'
            },
            data : JSON.stringify(dataObject)
        }).then(function(response){
            $scope.myData.token = response.data.token;
            $scope.myData.pseudonym = response.data.pseudonym;
            $scope.myData.expires = response.data['expire-date'];
            $location.path('/messenger');
        }, function () {
            window.alert("Leider ist ein Fehler aufgetreten!");
        })
    };
});