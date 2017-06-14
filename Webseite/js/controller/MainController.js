/**
 * Created by dennis on 13.06.17.
 */
app.controller('MainController', ['$scope', function($scope){
    $scope.impressum ={
        bild: 'impressum.png'
    };

    $scope.mannheim = {
        bild: 'mannheim.png'
    };
    $scope.produkt ={
        bild: 'produkt.png'
    };
    $scope.sicherheit ={
        bild: 'sicherheit.png'
    };
}]);