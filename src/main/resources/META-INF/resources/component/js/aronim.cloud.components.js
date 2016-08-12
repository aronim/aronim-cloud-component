!function () {
    "use strict";

    define(["jquery",
            "angular",
            "common/js/aronim.cloud.common",
            "text!component/template/aronim.cloud.components.html"],

        function ($, angular, kdc, kdcComponentsTemplate) {

            angular.module("aronim.cloud.components", [])
                .factory("acComponentsService", acComponentsService)
                .directive("acComponents", acComponents);

            function acComponentsService($q, $http) {
                return {
                    list: function () {

                        var deferred = $q.defer();

                        $http
                            .get("/api/components")
                            .success(function (data, status, headers, config) {
                                deferred.resolve(data);
                            })
                            .error(function (data, status, headers, config) {
                                deferred.reject(data.message);
                            });

                        return deferred.promise;
                    },
                    restart: function (instance) {

                        var deferred = $q.defer();

                        $http
                            .post("/api/components/restart?hostname=" + instance.ipAddr + "&port=" + instance.port.$, {})
                            .success(function (data, status, headers, config) {
                                deferred.resolve(data);
                            })
                            .error(function (data, status, headers, config) {
                                deferred.reject(data.message);
                            });

                        return deferred.promise;
                    },
                    shutdown: function (instance) {

                        var deferred = $q.defer();

                        $http
                            .post("/api/components/shutdown?hostname=" + instance.ipAddr + "&port=" + instance.port.$, {})
                            .success(function (data, status, headers, config) {
                                deferred.resolve(data);
                            })
                            .error(function (data, status, headers, config) {
                                deferred.reject(data.message);
                            });

                        return deferred.promise;
                    }
                }
            };

            function acComponents(acComponentsService) {

                return {
                    restrict: "E",
                    template: kdcComponentsTemplate,
                    controller: function ($scope) {

                        $scope.restart = function (instance) {

                            acComponentsService
                                .restart(instance)
                                .then(function () {
                                    refreshComponents();
                                }, function () {

                                });
                        };

                        $scope.shutdown = function (instance) {
                            acComponentsService
                                .shutdown(instance)
                                .then(function () {
                                    refreshComponents();
                                }, function () {

                                });
                        };

                        var refreshComponents = function () {
                            acComponentsService
                                .list()
                                .then(function (components) {
                                    $scope.components = components;
                                }, function (errorMessage) {
                                    $scope.errorMessage = errorMessage;
                                });
                        };

                        refreshComponents();
                    }
                }
            }
        }
    );
}();