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

		<div class='col-lg-3 col-sm-3 col-xs-3'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker6'>
					<input id="input-start-date" type='text' class="form-control"
						placeholder="Start Date" /> <span class="input-group-addon">
						<span class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>
			</div>
		</div>
		<div class='col-lg-3 col-sm-3 col-xs-3'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker7'>
					<input id="input-end-date" type='text' class="form-control"
						placeholder="End Date (Optional)" /> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span>
				</div>

			</div>
			<script type="text/javascript">
				$(function() {
					var d = new Date();

					var month = d.getMonth() + 1;
					var day = d.getDate();

					var output = d.getFullYear() + '/'
							+ (month < 10 ? '0' : '') + month + '/'
							+ (day < 10 ? '0' : '') + day;

					$('#input-start-date').val(output + " 00:01:00");

					$('#datetimepicker6').datetimepicker({
						format : 'YYYY-MM-DD'
					});
					$('#datetimepicker7').datetimepicker({
						format : 'YYYY-MM-DD'
					});

					$("#datetimepicker6").on("dp.change", function(e) {
						$('#datetimepicker7').data("DateTimePicker");
						console.log($('#input-start-date').val());
						$(this).datepicker('hide');
					});

					$("#datetimepicker7").on("dp.change", function(e) {
						$('#datetimepicker6').data("DateTimePicker");
						console.log('change');
						$(this).datepicker('hide');
					});
				});
			</script>
		</div>
		<div class='col-lg-2 col-sm-2 col-xs-2'>
			<div class="form-group">
				<div class='input-group date' id='datetimepicker-submit'>
					<button id="dfg" class="btn btn-success">Refresh Data</button>
				</div>
				<!-- <input id="input-start-date" type='text' class="form-control" placeholder="Start Date" /> <span
						class="input-group-addon"> <span
						class="glyphicon glyphicon-calendar"></span>
					</span> -->
			</div>
		</div>
	</div>
	<div class="container">
		<div class="col-lg-6 col-sm-6 col-xs-6">

			<fieldset>
				<legend>Choose profit type to filter out location table </legend>

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
	</div>
	<br>
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
			<div id='dashboard'></div>
		</div>
		<div class="col-lg-12">
			<ul class="nav nav-tabs" id="profiAnalysisTab">
				<li class="active"><a href="#sectionA">Profit analysis by location</a></li>
				<li><a href="#sectionB">Profit analysis by employee</a></li>
			</ul>
		</div>
		<!-- tab 1 data -->
		<div class="col-lg-12 col-sm-12 col-xs-12 p-0">
			<div class="tab-content">
				<div id="sectionA" class="tabledets tab-pane fade in active">
					<!--<h3 class="col-lg-12 col-sm-12 col-xs-12 p-0 text-center">Branch 1</h3>-->
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div class="table-responsive">
							<h3 id="branchHead"></h3>
							<div id="tableId" class="filter-table"></div>
						</div>
						<div class="table-responsive">
							<h3 id="locateHead"></h3>
							<div id="table2"></div>
						</div>
					</div>
				</div>

				<div id="sectionB" class="tab-pane fade">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div class="table-responsive">
							<h3 id="locateHead2"></h3>
							<div id="table3" class="filter-table"></div>
						</div>
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