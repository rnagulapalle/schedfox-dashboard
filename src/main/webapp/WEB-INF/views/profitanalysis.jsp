<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>angular-index.jsp</title>
        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/app.css" />
        <script
        src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-1.6.2.js"></script>
        <script
        src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-ui.js"></script>
        <script
        src="${pageContext.request.contextPath}/resources/lib/d3/d3min.js"></script>
        <script>
            $(document).ready(function () {
                $('.accordion-toggle').click(function () {
                    $(this).toggleClass('minus');
                    var id = $(this).attr('data-target');
                    $(id).slideToggle(1000);
                    $(id).addClass('in');
                });
                $('.navbar-toggle').click(function () {
                    //	alert('asdasdasd');

                    $(this).parent().next('div').toggleClass('in');
                    $(this).parent().next('div').slideToggle(500);
                });
            });
        </script>
    </head>
    <body ng-app="myApp">
        <!-- <ul class="menu">
    <li><a href="#/view1">view1</a></li>
    <li><a href="#/view2">view2</a></li>
  </ul>

  <div ng-view></div>

  <div>Angular seed app: v<span app-version></span></div>
        -->
        <section class="container">
            <div class="col-lg-12 col-sm-12 col-xs-12">
                <div class="reportClass">
                    <!--<p class="date_info col-lg-3 col-sm-6 col-xs-12">(From date : <span id="fd"></span>  To date : <span id="tda"></span>)</p>-->
                    <!--<div class="dateformat text-center col-lg-4 col-sm-12 col-xs-12 pull-right p-0">
                            <label class="col-lg-1 col-sm-1 col-xs-2 p-0 m-b-10">Date:</label> 
                            <div class="col-lg-6 col-sm-6 col-xs-6 m-b-10 date_input text-center pull-left" style="max-width: 170px;min-width: 170px;"><input type="text" class="form-control" id="fromdatepicker"></div>
                            <div class="col-lg-2 col-sm-3 col-xs-6 submitbtndiv m-b-10 text-center p-0"><button id="dfg" class="btn btn-success">Submit</button></div>
                            <div class="col-lg-3 col-sm-3 col-xs-6 submitbtndiv m-b-10 text-left p-0"><button id="resetId" class="btn btn-default">Reset</button></div>
                    </div> -->
                </div>

            </div>
            <div class="col-lg-12 col-sm-12 col-xs-12">
                <div id='dashboard'></div>
            </div>
            <div class="col-lg-12 col-sm-12 col-xs-12 p-0">
                <div class="tabledets">
                    <!--<h3 class="col-lg-12 col-sm-12 col-xs-12 p-0 text-center">Branch 1</h3>-->
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="table-responsive">
                            <!-- <table id="split"  class="table table-bordered" style="border-collapse:collapse;width:500px !important;">
                                    <thead></thead>
                                    <tbody></tbody>
                            </table> -->
                            <h3 id="branchHead"></h3>
                            <div id="tableId"></div>
                        </div>
                        <div class="table-responsive">
                            <h3 id="locateHead"></h3>
                            <div id="table2"></div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- In production use:
  <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.4/angular.min.js"></script>
        -->
        <script src="${pageContext.request.contextPath}/resources/lib/angular/angular.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/services.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/controllers.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/filters.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/directives.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/profitanalysis.js"></script>
    </body>
</html>