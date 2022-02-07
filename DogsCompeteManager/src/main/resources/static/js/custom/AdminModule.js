var app = angular.module('adminModule', ['ngMessages', 'ngRoute']);


app.config(function ($routeProvider) {
    $routeProvider
            .when("/users", {
                templateUrl: "admin_users.html",
                controller: 'AdminUsersController'
            })
            .when("/sendAll", {
                templateUrl: "admin_mail.html",
                controller: 'AdminMailController'
            })
            .when("/events", {
                templateUrl: "admin_events.html",
                controller: 'AdminEventController'
            })
            .otherwise({
                redirectTo: '/events'
            });
});

app.service('AdminMailService', ['$http', function ($http) {
        this.sendMail = function (address, text) {
            return $http({
                method: 'POST',
                url: '/admin/mail/send',
                headers: 'Accept:application/json',
                params: {
                    address: address,
                    name: name
                },
                data: text
            });
        }


    }]);

app.service('AdminUserService', ['$http', function ($http) {
        this.all = function () {
            return $http({
                method: 'GET',
                url: '/admin/users/all',
                headers: 'Accept:application/json'
            });
        }


    }]);

app.controller('AdminUsersController', ['$scope', 'AdminUserService', function ($scope, AdminUserService) {
        AdminUserService.all()
                .then(function success(response) {
                    $scope.users = response.data;
                },
                        function error(response) {
                            console.info('Ошибка при получении списка пользователей')
                        }
                );

        $scope.changePassword = function () {
            console.info('Функционал временно недоступен');
            //TODO: do it
        }
    }]);

app.controller('AdminMailController', ['$scope', 'AdminUserService', 'AdminMailService', function ($scope, AdminUserService, AdminMailService) {

        $scope.users = [];
        AdminUserService.all()
                .then(function success(response) {
                    $scope.users = response.data;
                },
                        function error(response) {
                            console.info('Ошибка при получении списка пользователей')
                        }
                );

        $scope.sendDocument = function () {

            for (var i = 0; i < $scope.users.length; i++) {
                if ($scope.users[i].personalData && $scope.users[i].personalData.email) {
                    var email = $scope.users[i].personalData.email;
                    var text = "Уважаемый пользователь! \n\
                    Вы получили это письмо, так как Ваш почтовый адрес указан как контактный для " + $scope.users[i].personalData.firstName + ".\n\
                    \n\
                    " + $scope.data.text + "\n\
                    \n\
                    CanisService";
                    AdminMailService.sendMail(email, text)
                            .then(function success(response) {
                                $scope.data.text = "";
                            },
                                    function error(response) {
                                        console.info('Ошибка при рассылке')
                                    }
                            );
                }

            }
        }
    }]);

app.controller('AdminEventController', ['$scope', 'AdminEventService', function ($scope, AdminEventService) {
        $scope.events = [];
        $scope.data = {
            error: false,
            message: null
        }


        clearForm = function () {
            $scope.currentEvent = {
                name: null,
                date: null
            }
        }
        clearForm();
        AdminEventService.getEvents().then(function success(response) {
            $scope.events = response.data;
        },
                function error(response) {}
        );

        $scope.addEvent = function () {
            if ($scope.currentEvent.date === null || $scope.currentEvent.name === null) {
                $scope.data = {
                    error: true,
                    message: 'Заполните поля формы!'
                }
            } else
                AdminEventService.addEvent($scope.currentEvent).then(function success(response) {
                    AdminEventService.getEvents().then(function success(response2) {
                        $scope.events = response2.data;
                        clearForm();
                    },
                            function error(response2) {}
                    );
                },
                        function error(response) {
                            $scope.data = {
                                error: true,
                                message: 'Ошибка добавления мероприятия!'
                            }
                        }
                );
        };

        $scope.deleteEvent = function (id) {
            AdminEventService.deleteEvent(id).then(function success(response) {
                AdminEventService.getEvents().then(function success(response2) {
                    $scope.events = response2.data;
                },
                        function error(response2) {}
                );
            },
                    function error(response) {
                        $scope.data = {
                            error: true,
                            message: 'Ошибка удаления мероприятия!'
                        }
                    }
            );
        };
    }]);
//TODO: check errors and messaging

app.service('AdminEventService', ['$http', function ($http) {
        this.addEvent = function (event) {
            return $http({
                method: 'POST',
                url: '/admin/event/add',
                headers: 'Accept:application/json',
                data: event
            });
        }
        this.deleteEvent = function (id) {
            return $http({
                method: 'GET',
                url: '/admin/event/delete',
                headers: 'Accept:application/json',
                params: {
                    id: id
                }
            });
        }

        this.getEvents = function () {
            return $http({
                method: 'GET',
                url: '/admin/event/all',
                headers: 'Accept:application/json'
            });
        }


    }]);