var app = angular.module('AdminProfileApp', ['ngMessages', 'ngRoute']);


app.config(function ($routeProvider) {
    $routeProvider
            .when("/users", {
                templateUrl: "users_list.html",
                controller: 'UsersListCtrl'
            })
            .otherwise({
                redirectTo: '/users'
            });
});


app.controller('UsersListCtrl', ['$scope', function ($scope) {
    }]);