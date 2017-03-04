define(['angular','common/module-name'], function (angular,moduleName) {
    'use strict';

    angular.module(moduleName)
        .run(['$templateCache', function ($templateCache) {
            $templateCache.put('menu-link.tmpl.html',
            '<md-button flex ui-sref="{{section.sref}}" ng-click="selectMenuItem()" class="nav-btn" \n ' +
            'ng-class="{\'selected\' : section === selectedMenuItem, \'nav-btn\': selected !== selectedMenuItem }"  > \n' +
            '<div class="layout-padding-left-8 menu-link"><ng-md-icon md-icon icon="{{section.icon}}"  class="{{sectionClass()}}"></ng-md-icon> \n '+
            '<md-tooltip md-direction="right" ng-if="isCollapsed()">{{section.text}}</md-tooltip>'+
            '<span style="padding-left:10px;" ng-if="!isCollapsed()">{{section.text}}</span> '
            + '</div>'
            + '</md-button>\n '+
                '');
        }])
        .directive('menuLink', function () {
            return {
                scope: {
                    section: '='
                },
                require: '^accordionMenu',
                templateUrl: 'menu-link.tmpl.html',
                link: function ($scope, $element,attrs,controller) {

                    $scope.selectMenuItem = function () {
                        // set flag to be used later when
                        // $locationChangeSuccess calls openPage()
                        controller.autoFocusContent = true;

                    };
                    $scope.isCollapsed = function(){
                        return controller.isCollapsed();
                    }
                    $scope.sectionClass = function() {
                        if($scope.section == controller.currentSection ) {
                            return 'selected';
                        }
                        else {
                            return 'nav-btn';
                        }
                    }
                }
            };
        })
});
