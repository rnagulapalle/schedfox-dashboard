<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8">
    <title><decorator:title/></title>
    <decorator:head/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/jquery.ui.datepicker.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet">
    
    
    <link href="${pageContext.request.contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/awesome-bootstrap-checkbox.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css" />
	
	<!-- jsslider styles -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jsslider//jslider.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jsslider//jslider.blue.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jsslider//jslider.plastic.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jsslider//jslider.round.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jsslider//jslider.round.plastic.css" type="text/css">
	
	<script src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-1.11.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/lib/jquery/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/resources/lib/d3/d3min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/lib/moment/moment.js"></script>
	
	<!-- jsslider js files -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/jshashtable-2.1_src.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/jquery.numberformatter-1.2.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/tmpl.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/jquery.dependClass-0.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/draggable-0.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/jsslider/jquery.slider.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/transition.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/collapse.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
<script>
	
	$(document).ready(function() {
	
		$('.accordion-toggle').click(function() {
			$(this).toggleClass('minus');
			var id = $(this).attr('data-target');
			$(id).slideToggle(1000);
			$(id).addClass('in');
		});
		$('.navbar-toggle').click(function() {
			//	alert('asdasdasd');

			$(this).parent().next('div').toggleClass('in');
			$(this).parent().next('div').slideToggle(500);
		});
		
	});
</script>
</head>
<body <decorator:getProperty property="body.ng-app" writeEntireProperty="true"/>>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Schedfox Dashboard</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </div>

    <div class="container" style="padding-top: 60px">
        <!-- <div class="starter-template">
            <h1>Bootstrap starter template</h1>
            <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>
        </div>
 -->
        <div class="body">
            <decorator:body />
        </div>

    </div><!-- /.container -->

</body>
</html>