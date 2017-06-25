/**
 * Created by dennis on 18.06.17.
 */
app.controller('MsgController', function ($http, $scope){

    var sequenceNr;



    $scope.recieveMsg = function () {
        var url = store.msgServer;

        $http({
            method: 'get',
            url: 'http://'+url+'/messages/'+$scope.myData.pseudonym,
            headers : {
                'Authorization': $scope.myData.token,
                'Content-Type': 'application/json'
            }
        })

    }

    $scope.sendToServer = function (store) {
        var dataObject = {
            form: $scope.myData.pseudonym,
            to: store.to,
            date: new Date(),
            token: $scope.myData.token,
            text: store.text
        };

        $http({
            method: 'put',
            url: 'http://142.19.141.60/send',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(dataObject)
        }).then(function (response) {
            sequenceNr = response.data.sequence;
            $scope.myData.serverdate = response.data.date;
        })
    };
});