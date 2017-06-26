/**
 * Created by dennis on 18.06.17.
 */
app.controller('MsgController', function ($http, $scope){

    authentification();
    getContact();

    var sequenceNr;

    $scope.messages =[{
        text: "Hallo wie gehts dir"
    },{
        text: "test testtest"
    }];

    function authentification() {

        var dataObject = {
            token: $scope.myData.token,
            pseudonym : $scope.myData.pseudonym
        }

        $http({
            method: 'post',
            url: 'http://'+$scope.myData.url+':5001/auth',
            headers : {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(dataObject)
        }).then(function (response) {

        }, function (response) {
            window.alert("Nicht geklappt");
        })
    };


    function getContact () {
        var dataObject = {
            'token': $scope.myData.token,
            'getownprofile': $scope.myData.pseudonym
        }


        $http({
            method: 'post',
            url: 'http://'+$scope.myData.url+':5002/profil',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(dataObject)
        }).then(function (response) {
            $scope.myData.contact = response.data.contact;
        }, function (response) {
            window.alert("Leider ist ein Fehler bei den Profilen passiert =(")
        })
    }


    $scope.recieveMsg = function () {
        

        $http({
            method: 'get',
            url: 'http://'+$scope.myData.url+':5000/messages/'+$scope.myData.pseudonym+'',
            headers : {
                'Authorization': $scope.myData.token,
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            $scope.messages = response.data.text;
        })

    };

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
            url: 'http://'+ $scope.myData.url+'/:5000/send',
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