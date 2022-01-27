var app = angular.module('userModule', ['ngMessages', 'ngRoute', 'mainModule']);


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
                redirectTo: '/profile'
            });
});


app.controller('ProfileCtrl', ['$scope', 'UserService', function ($scope, UserService) {
        $scope.data = {
            newPassword: null,
            newPasswordRepeat: null,
            error: false,
            message: null
        };

        $scope.currentUser = null;
        UserService.currentUser()
                .then(function success(response) {
                    console.info(response);
                    if (response !== undefined) {
                        $scope.currentUser = response.data;
                    }
                },
                        function error(response) {
                            console.info('Ошибка при получении пользователя')
                        }
                );

        $scope.savePersonalData = function () {
            UserService.update($scope.currentUser).then(function success(response) {
                if (response.data.result === true) {
                    $scope.data.error = false;
                    $scope.data.message = response.data.message;
                } else {
                    $scope.data.error = true;
                    $scope.data.message = response.data.message;
                }
            },
                    function error() {
                        $scope.data.error = true;
                        $scope.data.message = "Ошибка сервера";
                    }
            );
        }

        $scope.updatePassword = function () {
            if ($scope.newPassword === $scope.newPasswordRepeat) {
                UserService.updateUserPassword($scope.currentUser.login, $scope.newPassword).then(function success(response) {
                    if (response.data.result === true) {
                        $scope.data.error = false;
                        $scope.data.message = response.data.message;
                    } else {
                        $scope.data.error = true;
                        $scope.data.message = response.data.message;
                    }
                },
                        function error() {
                            $scope.data.error = true;
                            $scope.data.message = "Ошибка сервера";
                        }
                );
            } else {

                $scope.data.error = true;
                $scope.data.message = "Введенные пароли не совпадают!"

            }

        }
    }]);


app.controller('DogsCtrl', ['$scope', function ($scope) {
    }]);
app.controller('CompetitionsCtrl', ['$scope', function ($scope) {
    }]);