!function () {
    "use strict";

    define(["jquery",
            "angular",
            "common/js/kdc.common",
            "text!component/template/kdc.components.html"],

        function ($, angular, kdc, kdcComponentsTemplate) {

            var module = angular.module("kdc.components", []);

            module.factory("kdcComponentsService",
                function ($q, $http) {

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
                }
            );

            module.directive("kdcComponents",
                function ($window, $log, kdcComponentsService) {

                    return {
                        restrict: "E",
                        template: kdcComponentsTemplate,
                        controller: function ($scope) {

                            $scope.restart = function (instance) {

                                kdcComponentsService
                                    .restart(instance)
                                    .then(function () {
                                        refreshComponents();
                                    }, function () {

                                    });
                            };

                            $scope.shutdown = function (instance) {
                                kdcComponentsService
                                    .shutdown(instance)
                                    .then(function () {
                                        refreshComponents();
                                    }, function () {

                                    });
                            };

                            var refreshComponents = function () {
                                kdcComponentsService
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
            );
        }
    );
}();