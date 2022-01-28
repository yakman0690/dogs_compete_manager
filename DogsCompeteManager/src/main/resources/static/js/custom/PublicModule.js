var app = angular.module('publicModule', ['ngMessages', 'ngRoute']);

app.controller('MainController', ['$scope', 'PublicUserService', function ($scope, PublicUserService) {
        $scope.currentUser = null;
        PublicUserService.currentUser()
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
    }]);

app.controller('RestorePasswordController', ['$scope', 'PublicService', function ($scope, PublicService) {
        $scope.data = {
            error: false,
            message: null
        };
        $scope.restorePassword = function () {
            if (!$scope.login) {
                $scope.data.error = true;
                $scope.data.message = "Введите логин пользователя";
            } else {
                PublicService.restorePassword($scope.login).then(function success(response) {
                    if (response.data.result === true) {
                        $scope.data.error = false;
                        $scope.data.message = response.data.message;
                    } else {
                        $scope.data.error = true;
                        $scope.data.message = response.data.message;
                    }
                },
                        function error() {
                            console.info('Ошибка при попытке восстановления пароля пользователя')
                        }
                );
            }

        }

    }]);

app.controller('RegistrationController', ['$scope', 'PublicUserService', function ($scope, PublicUserService) {
        $scope.data = {
            login: null,
            password: null,
            error: false,
            message: null
        };

        clearFields = function () {
            $scope.data.login = null;
            $scope.data.password = null;
            $scope.data.password_repeat = null;
        }

        $scope.save = function () {
            if ($scope.data.password === $scope.data.password_repeat) {
                var user = {
                    login: $scope.data.login,
                    password: $scope.data.password,
                    role: 'USER'
                };
                PublicUserService.add(user).then(function success(response) {
                    if (response.data.result === true) {
                        $scope.data.error = false;
                        $scope.data.message = response.data.message;
                        clearFields();
                    } else {
                        $scope.data.error = true;
                        $scope.data.message = response.data.message;
                        clearFields();
                    }
                },
                        function error() {
                            console.info('Ошибка при сохранении пользователя')
                        }
                );
            } else {

                $scope.data.error = true;
                $scope.data.message = "Введенные пароли не совпадают!"
                clearFields();

            }

        }
    }]);

app.service('PublicService', ['$http', function ($http) {
        this.restorePassword = function (login) {
            return $http({
                method: 'GET',
                url: '/public/restorePassword',
                headers: 'Accept:application/json',
                params:
                        {login: login}
            });
        }
    }]);



app.service('PublicUserService', ['$http', function ($http) {
        this.currentUser = function () {
            return $http({
                method: 'GET',
                url: '/public/user/current',
                headers: 'Accept:application/json'
            });
        }

        this.add = function (user) {
            return $http({
                method: 'POST',
                url: '/public/user/add',
                headers: 'Accept:application/json',
                data: user
            });
        }


    }]);




