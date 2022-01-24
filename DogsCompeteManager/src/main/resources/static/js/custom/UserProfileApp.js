var app = angular.module('UserProfileApp', ['ngMessages', 'ngRoute']);


app.config(function ($routeProvider) {
    $routeProvider
            .when("/profile", {
                templateUrl: "user_profile.html",
                controller: 'ProfileCtrl'
            })
            .when("/dogs", {
                templateUrl: "user_dogs.html",
                controller: 'DogsCtrl',
            })
            .when("/competitions", {
                templateUrl: "user_competitions.html",
                controller: 'CompetitionsCtrl',
            })
            .otherwise({
                redirectTo: '/dogs'
            });
});


app.controller('ProfileCtrl', ['$scope', function ($scope) {
    }]);
app.controller('DogsCtrl', ['$scope', function ($scope) {
    }]);
app.controller('CompetitionsCtrl', ['$scope', function ($scope) {
    }]);