var app = angular.module('mainModule', ['ngMessages', 'ngRoute']);

app.controller('MainController', ['$scope', 'UserService', function ($scope, UserService) {
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
    }]);

app.controller('GetPasswordController', ['$scope', 'UserService', function ($scope, UserService) {
        $scope.data = {
            error: false,
            message: null
        };
        $scope.getPassword = function () {
            if (!$scope.login) {
                $scope.data.error = true;
                $scope.data.message = "Введите логин пользователя";
            } else {
                UserService.getPassword($scope.login).then(function success(response) {
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
app.controller('RegistrationController', ['$scope', 'UserService', function ($scope, UserService) {
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
                UserService.save(user).then(function success(response) {
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

app.service('UserService', ['$http', function ($http) {
        this.currentUser = function () {
            return $http({
                method: 'GET',
                url: '/users/current',
                headers: 'Accept:application/json'
            });
        }

        this.save = function (user) {
            return $http({
                method: 'POST',
                url: '/users/save',
                headers: 'Accept:application/json',
                data: user
            });
        }

        this.update = function (user) {
            return $http({
                method: 'POST',
                url: '/users/update',
                headers: 'Accept:application/json',
                data: user
            });
        }

        this.updateUserPassword = function (login, password) {
            return $http({
                method: 'POST',
                url: '/users/updatePassword',
                headers: 'Accept:application/json',
                data: {
                    login: login,
                    password: password
                }
            });
        }

        this.getPassword = function (login) {
            return $http({
                method: 'GET',
                url: '/users/getPassword',
                headers: 'Accept:application/json',
                params:
                        {login: login}
            });
        }
    }]);


