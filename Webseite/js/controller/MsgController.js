/**
 * Created by dennis on 18.06.17.
 */
app.controller('MsgController', function ($http, $scope, $location){

    $scope.sendToServer = function () {
        var dataObject = {'from': $('from'), 'to': $('to'), 'date': new Date(), 'text': $('text')};

        $http.put('http://141.19.142.60:5000/send', JSON.stringify(dataObject),
            {
                headers:{
                    'Content-Type': 'application/json; charset=utf-8'
                }
            }).then(function success(response){
                $scope.date = response.date;
                $scope.sequence = response.sequence;
        }), function error(response) {

        }

    }
});