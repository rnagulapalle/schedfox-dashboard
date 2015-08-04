<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<!-- refresh the page every 2 hours -->
<meta http-equiv="refresh" content="7200">
<title>angular-index.jsp</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/app.css" />
</head>
<body ng-app="myApp">
	<div id="spinner"></div>
	<div class="container">

		<div class="col-lg-12 col-sm-12 col-xs-12">
			<h2>Profit Analysis Report</h2>
		</div>

	</div>
	<div class="container">

		<div class='col-lg-4 col-sm-4 col-xs-4'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker6'>
					<input id="input-start-date" type='text' class="form-control" placeholder="Start Date" /> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class='col-lg-4 col-sm-4 col-xs-4'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker7'>
					<input id="input-end-date" type='text' class="form-control" placeholder="End Date (Optional)" /> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>

			</div>
			<script type="text/javascript">
				$(function() {
					var d = new Date();

					var month = d.getMonth()+1;
					var day = d.getDate();

					var output = d.getFullYear() + '/' +
					(month<10 ? '0' : '') + month + '/' +
					(day<10 ? '0' : '') + day;

					$('#input-start-date').val(output + " 00:01:00");
					
					$('#datetimepicker6').datetimepicker({format: 'YYYY-MM-DD'});
					$('#datetimepicker7').datetimepicker({format: 'YYYY-MM-DD'});
					
					$("#datetimepicker6").on(
							"dp.change",
							function(e) {
								$('#datetimepicker7').data("DateTimePicker");
								console.log($('#input-start-date').val());
								$(this).datepicker('hide');
							});
					
					$("#datetimepicker7").on(
							"dp.change",
							function(e) {
								$('#datetimepicker6').data("DateTimePicker");
								console.log('change');
								$(this).datepicker('hide');
							});
				});
			</script>
		</div>
		<div class='col-lg-4 col-sm-4 col-xs-4'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker-submit'>
				<button id="dfg" class="btn btn-success">Refresh Chart</button></div>
					<!-- <input id="input-start-date" type='text' class="form-control" placeholder="Start Date" /> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span> -->
				</div>
			</div>
		</div>
	<section class="container">
		<div class="col-lg-11 col-sm-11 col-xs-11">
			<!-- <div class="clearfix visible-sm"></div>
 -->
			<div class="alert alert-success alert-dismissible">
				<i class="fa fa-exclamation-circle"></i> <strong>Profit
					Analysis Report:</strong>&nbsp;from <em><span id="from"></span></em>
				to&nbsp;<em><span id="to"></span></em>.
			</div>

		</div>
		<div class="col-lg-12 col-sm-12 col-xs-12">
			<div class="reportClass">
				<!-- <p class="date_info col-lg-3 col-sm-6 col-xs-12">(From date : <span id="fd"></span>  To date : <span id="tda"></span>)</p> -->
				<!-- <div class="dateformat text-center col-lg-4 col-sm-12 col-xs-12 pull-right p-0">
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
		<div class="col-lg-12 col-sm-12 col-xs-12">
			<fieldset>
				<legend> Select filter </legend>

				<div class="checkbox checkbox-success checkbox-inline">
					<input type="checkbox" class="styled" id="inlineCheckbox1"
						value="high"> <label for="inlineCheckbox1"> High
						Profit </label>
				</div>
				<div class="checkbox checkbox-warning checkbox-inline">
					<input type="checkbox" class="styled" id="inlineCheckbox2"
						value="med"> <label for="inlineCheckbox2"> Medium
						Profit </label>
				</div>
				<div class="checkbox checkbox-danger checkbox-inline">
					<input type="checkbox" class="styled" id="inlineCheckbox3"
						value="low"> <label for="inlineCheckbox3"> Low
						Profit </label>
				</div>
			</fieldset>
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
	<script
		src="${pageContext.request.contextPath}/resources/lib/angular/angular.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/services.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/controllers.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/filters.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/directives.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/js/profitanalysis.js"></script>
</body>
</html>