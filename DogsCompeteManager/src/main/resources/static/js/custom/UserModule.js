var app = angular.module('userModule', ['ngMessages', 'ngRoute']);


app.config(function ($routeProvider) {
    $routeProvider
            .when("/profile", {
                templateUrl: "user_profile.html",
                controller: 'UserProfileController'
            })
            .when("/dogs", {
                templateUrl: "user_dogs.html",
                controller: 'UserDogsController',
            })
            .when("/events", {
                templateUrl: "user_events.html",
                controller: 'UserEventsController',
            })
            .otherwise({
                redirectTo: '/profile'
            });
});

app.service('UserService', ['$http', function ($http) {
        this.currentUser = function () {
            return $http({
                method: 'GET',
                url: '/user/current',
                headers: 'Accept:application/json'
            });
        }

        this.update = function (user) {
            return $http({
                method: 'POST',
                url: '/user/update',
                headers: 'Accept:application/json',
                data: user
            });
        }

        this.updateUserPassword = function (login, password) {
            return $http({
                method: 'POST',
                url: '/user/updatePassword',
                headers: 'Accept:application/json',
                data: {
                    login: login,
                    password: password
                }
            });
        }


    }]);





app.controller('UserProfileController', ['$scope', 'UserService', function ($scope, UserService) {
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


app.controller('UserDogsController', ['$scope', 'UserDogsService', function ($scope, UserDogsService) {
        $scope.dogs = [];
        $scope.data = {
            error: false,
            message: null
        }


        clearForm = function () {
            $scope.currentDog = {
                name: null,
                breed: null,
                birthday: null,
                gender: null,
                pedigree: null,
                tatoo: null,
                owner: null
            }
        }
        clearForm();
        UserDogsService.getDogs().then(function success(response) {
            $scope.dogs = response.data;
        },
                function error(response) {}
        );

        $scope.addDog = function () {
            if ($scope.currentDog.name === null || $scope.currentDog.tatoo === null) {
                $scope.data = {
                    error: true,
                    message: 'Заполните поля формы!'
                }
            } else
                UserDogsService.addDog($scope.currentDog).then(function success(response) {
                    UserDogsService.getDogs().then(function success(response2) {
                        $scope.dogs = response2.data;
                        clearForm();
                    },
                            function error(response2) {}
                    );
                },
                        function error(response) {
                            $scope.data = {
                                error: true,
                                message: 'Ошибка добавления собаки!'
                            }
                        }
                );
        };

        $scope.deleteDog = function (id) {
            UserDogsService.deleteDog(id).then(function success(response) {
                UserDogsService.getDogs().then(function success(response2) {
                    $scope.dogs = response2.data;
                },
                        function error(response2) {}
                );
            },
                    function error(response) {
                        $scope.data = {
                            error: true,
                            message: 'Ошибка удаления собаки!'
                        }
                    }
            );
        };
    }]);


app.service('UserDogsService', ['$http', function ($http) {
        this.addDog = function (dog) {
            return $http({
                method: 'POST',
                url: '/user/dog/add',
                headers: 'Accept:application/json',
                data: dog
            });
        }
        this.deleteDog = function (id) {
            return $http({
                method: 'GET',
                url: '/user/dog/delete',
                headers: 'Accept:application/json',
                params: {
                    id: id
                }
            });
        }

        this.getDogs = function () {
            return $http({
                method: 'GET',
                url: '/user/dog/all',
                headers: 'Accept:application/json'
            });
        }


    }]);

//TODO: check errors and messaging

app.controller('UserEventsController', ['$scope', 'UserEventService', 'UserDogsService', function ($scope, UserEventService, UserDogsService) {

        $scope.events = [];
        $scope.myEvents = [];
        $scope.data = {
            error: false,
            message: null
        }
        init = function () {
            UserEventService.getAllEvents().then(function success(response) {
                $scope.events = response.data;
            },
                    function error(response) {}
            );
            UserEventService.getMyEvents().then(function success(response) {
                $scope.myEvents = response.data;
            },
                    function error(response) {}
            );
            $scope.currentEvent = null;
            $scope.currentDogId = null;
            $('#addDogModal').modal('hide');
        }

        init();

        $scope.signUp = function (event_id) {
            $('#addDogModal').modal('show');
            $scope.currentEvent = event_id;
        }

        UserDogsService.getDogs().then(function success(response) {
            $scope.dogs = response.data;
        },
                function error(response) {}
        );
        $scope.signUpCommit = function () {
            if ($scope.currentDogId) {
                UserEventService.signUp($scope.currentEvent, $scope.currentDogId).then(function success(response) {
                    init();
                },
                        function error(response) {}
                );
            }
        }


    }]);


app.service('UserEventService', ['$http', function ($http) {


        this.getAllEvents = function () {
            return $http({
                method: 'GET',
                url: '/user/event/all',
                headers: 'Accept:application/json'
            });
        }
        this.getMyEvents = function () {
            return $http({
                method: 'GET',
                url: '/user/event/my',
                headers: 'Accept:application/json'
            });
        }
        this.signUp = function (eventId, dogId) {
            return $http({
                method: 'POST',
                url: '/user/event/signUp',
                headers: 'Accept:application/json',
                params: {
                    eventId: eventId,
                    dogId: dogId
                }
            });
        }


    }]);