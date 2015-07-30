var firstDate = "";
	var	 toDate = "";
	var lName;
	
	
		var toDate = new Date();
        toDate = toDate.toISOString().substring(0, 10);
		var cv = new Date(toDate);
		cv.setDate(cv.getDate() - 21);
		var startDate = cv.getFullYear() +"-"+("0"+(cv.getMonth()+1)).slice(-2)+"-"+("0" + cv.getDate()).slice(-2);
		
		firstDate = startDate;
		toDate = toDate;
		
	
function dashboard(id, fData){
        var barRed = "#E84C3D";
		var barGreen = "#2ECD71";
		var barYellow = "#F2C40F";
        var barColor = '#3598DC';
		var pieMouse;
		$('#fd').text(firstDate);
		$('#tda').text(toDate);
    function segColor(c){ return {bill:"#F39C12",paid:"#41ab5d"}[c]; }
    
    
	fData.forEach(function(d){d.total=(d.values.branchPercentage);});
	
    function wrap(text, width) {
  text.each(function() {
    var text = d3.select(this),
        words = text.text().split(/\s+/).reverse(),
        word,
        line = [],
        lineNumber = 0,
        lineHeight = 1.1, // ems
        y = text.attr("y"),
        dy = parseFloat(text.attr("dy")),
        tspan = text.text(null).append("tspan").attr("x", -25).attr("y", 3).attr("dy", dy + "em");
    	text.attr("transform", "rotate(-65)" );
    while (word = words.pop()) {
      line.push(word);
      tspan.text(line.join(" "));
      if (tspan.node().getComputedTextLength() > width) {
        line.pop();
        tspan.text(line.join(" "));
        line = [word];
        tspan = text.append("tspan").attr("x", -25).attr("y", 3).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
      }
    }
});
}
    
    // function to handle histogram.
    function histoGram(fD){
	
		var colorred = d3.scale.ordinal().range([barRed]);
        var colorgreen = d3.scale.ordinal().range([barGreen]);
        var coloryellow = d3.scale.ordinal().range([barYellow]); 
  
        //var hG={},    hGDim = {t: 60, r: 0, b: 180, l: 0};
		var hG={},    hGDim = {t: 20, r: 0, b: 250, l: 0};
        hGDim.w = 1000 - hGDim.l - hGDim.r, 
        hGDim.h = 400 - hGDim.t - hGDim.b;
            
        //create svg for histogram.
        var hGsvg = d3.select(id).append("svg")
			.attr("class", "barchartsvg")
            .attr("width", hGDim.w + hGDim.l + hGDim.r)
			//.attr("width", 1600)
            .attr("height", 270).append("g")
            .attr("transform", "translate(" + hGDim.l + "," + hGDim.t + ")");

        // create function for x-axis mapping.
        var x = d3.scale.ordinal().rangeRoundBands([0, hGDim.w], 0.1)
                .domain(fD.map(function(d) { return d[0]; }));
		
		var insertLinebreaks = function (d) {
		var el = d3.select(this);
		var words = d.split(' ');
		el.text('');
		for (var i = 0; i < words.length; i++) {
		  var tspan = el.append('tspan').text(words[i]);
		  if (i > 0)
			tspan.attr('x', 0).attr('dy', '15');
		 }
		};
		
        // Add x-axis to the histogram svg.
        hGsvg.append("g").attr("class", "x axis")
            .attr("transform", "translate(0," + hGDim.h + ")")
            .call(d3.svg.axis().scale(x).orient("bottom"))
			.selectAll(".tick text")
			.call(wrap, x.rangeBand());
			
 
        // Create function for y-axis map.
        var y = d3.scale.linear().range([hGDim.h, 0])
                .domain([0, d3.max(fD, function(d) { return d[1]; })]);

        // Create bars for histogram to contain rectangles and freq labels.
        var bars = hGsvg.selectAll(".bar").data(fD).enter()
                .append("g").attr("class", "bar");
        
        //create the rectangles.
        bars.append("rect")
            .attr("x", function(d) { return x(d[0]); })
            .attr("y", function(d) { return y(d[1]); })
            //.attr("width", x.rangeBand())
			.attr("width", x.rangeBand())
            .attr("height", function(d) { return hGDim.h - y(d[1]); })
           
		   .attr("fill", function(d, i) { 
					if(d[1] < 64)
					     return colorgreen(i%1);
					else if(d[1] < 68 && d[1] > 64)
					     return coloryellow(i%1);
					else if(d[1] > 68)
					     return colorred(i%1); 
		  })
		   
            .on("mouseover",mouseover)// mouseover is defined below.
            .on("mouseout",mouseout);// mouseout is defined below.
            
        //Create the frequency labels above the rectangles.
        bars.append("text").text(function(d){ 
		//console.log(d);
		//console.log("------");
		return d3.format(",")(d[1])+"%"})
            .attr("x", function(d) { return x(d[0])+x.rangeBand()/2; })
            .attr("y", function(d) { return y(d[1])-5; })
            .attr("text-anchor", "middle");
        
        function mouseover(d){  
		//alert('dfd');
		
		// utility function to be called on mouseover.
            // filter for selected state.
            var st = fData.filter(function(s){  return s.key == d[0];})[0],
                nD = d3.keys(st.values).map(function(s){ 
				//if(s != 'paidpercentage')
				return {type:s, values:st.values[s]};});
               //console.log(nD);
            // call update functions of pie-chart and legend.    
            //pC.update(nD);
            //leg.update(nD);
			
			$('#table2').hide();
		$('#locateHead').hide();
			drawTable(d[0],firstDate,toDate);
        }
        
        function mouseout(d){    // utility function to be called on mouseout.
            // reset the pie-chart and legend.    
			//$('#split').hide();
            //pC.update(tF);
            //leg.update(tF);
        }
        
        // create function to update the bars. This will be used by pie-chart.
        hG.update = function(nD, color,pieMouse){
		//console.log(nD)
            // update the domain of the y-axis map to reflect change in frequencies.
            y.domain([0, d3.max(nD, function(d) { return d[1]; })]);
            
            // Attach the new data to the bars.
            var bars = hGsvg.selectAll(".bar").data(nD);
            
            // transition the height and color of rectangles.
            bars.select("rect").transition().duration(500)
                .attr("y", function(d) {return y(d[1]); })
                .attr("height", function(d) { return hGDim.h - y(d[1]); })
                //.attr("fill", function(d, i) { if(pieMouse){} else{return color;} } );
				 .attr("fill", function(d, i) {
					if(pieMouse){
						if(d[1] < 64)
							 return colorgreen(i%1);
						else if(d[1] < 68 && d[1] > 64)
							 return coloryellow(i%1);
						else if(d[1] > 68)
							 return colorred(i%1); 
					}else{
						return color;
					}
		  }); 

            // transition the frequency labels location and change value.
            bars.select("text").transition().duration(500)
                .text(function(d){ return d3.format(",")(d[1])})
                .attr("y", function(d) {return y(d[1])-5; });            
        }        
        return hG;
    }
    
    // function to handle pieChart.
    function pieChart(pD){
	
        var pC ={},    pieDim ={w:200, h: 250};
        pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;
                
        // create svg for pie chart.
        var piesvg = d3.select(id).append("svg")
			.attr("class", "piechartsvg")
            .attr("width", pieDim.w).attr("height", 250).append("g")
            .attr("transform", "translate("+pieDim.w/2+","+pieDim.h/2+")");
        
        // create function to draw the arcs of the pie slices.
        var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

        // create a function to compute the pie slice angles.
		
        var pie = d3.layout.pie().sort(null).value(function(d) { 
		  //console.log(d);
		//if(d.type != 'paidPercentage')
		return d.values; 
		});
	
        // Draw the pie slices.
        piesvg.selectAll("path").data(pie(pD)).enter().append("path").attr("d", arc)
            .each(function(d) { this._current = d; })
            .style("fill", function(d) { return segColor(d.data.type); })
            .on("mouseover",mouseover).on("mouseout",mouseout);

        // create function to update pie-chart. This will be used by histogram.
        pC.update = function(nD){
            piesvg.selectAll("path").data(pie(nD)).transition().duration(500)
                .attrTween("d", arcTween);
        }        
        // Utility function to be called on mouseover a pie slice.
        function mouseover(d){
		$('#split').hide();
            // call the update function of histogram with new data.
            /*hG.update(fData.map(function(v){ 
                return [v.key,v.values[d.data.type]];}),segColor(d.data.type));*/
        }
        //Utility function to be called on mouseout a pie slice.
        function mouseout(d){
		$('#split').hide();
            // call the update function of histogram with all data.
           /* hG.update(fData.map(function(v){
                return [v.key,v.total];}), barColor,pieMouse = 1);*/
        }
        // Animating the pie-slice requiring a custom function which specifies
        // how the intermediate paths should be drawn.
        function arcTween(a) {
            var i = d3.interpolate(this._current, a);
            this._current = i(0);
            return function(t) { return arc(i(t));    };
        }    
        return pC;
    }
    
    // function to handle legend.
    function legend(lD){
	//console.log(lD);
	
        var leg = {};
            
        // create table for legend.
        var legend = d3.select(id).append("table").attr('class','legend');
        
        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(lD).enter().append("tr");
            
        // create the first column for each segment.
        tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
            .attr("width", '16').attr("height", '16')
			.attr("fill",function(d){  return segColor(d.type); });
			
            
        // create the second column for each segment.
        tr.append("td").text(function(d){ return d.type;});

        // create the third column for each segment.
        tr.append("td").attr("class",'legendFreq')
            .text(function(d){ return d3.format(",")(parseFloat(d.values).toFixed(2));});

        // create the fourth column for each segment.
        tr.append("td").attr("class",'legendPerc')
            .text(function(d){  return getLegend(d,lD);});

        // Utility function to be used to update the legend.
        leg.update = function(nD){
            // update the data attached to the row elements.
            var l = legend.select("tbody").selectAll("tr").data(nD);

            // update the frequencies.
            l.select(".legendFreq").text(function(d){ return d3.format(",")(parseFloat(d.values).toFixed(2));});

            // update the percentage column.
            l.select(".legendPerc").text(function(d){ return getLegend(d,nD);});        
        }
        
        function getLegend(d,aD){ // Utility function to compute percentage.
		//console.log(d);
		
            return d3.format("%")(d.values/d3.sum(aD.map(function(v){ return parseFloat(v.values).toFixed(2); })));
        }

        return leg;
		
    }
    
    // calculate total frequency by segment for all state.
    var tF = ['paid','bill'].map(function(d){ 
        return {type:d, values: d3.sum(fData.map(function(t){  return t.values[d];}))}; 
    });    
     
    // calculate total frequency by state for all segment.
    var sF = fData.map(function(d){return [d.key,d.total];});

    var hG = histoGram(sF); // create the histogram.
        //pC = pieChart(tF); // create the pie-chart.
        
		 //var leg= legend(tF);  // create the legend.
}
/* Button Click*/
	 d3.select('#dfg').on('click', function() {
	 $('#table2').hide();
	 $('#tableId').hide();
	 $('#locateHead').hide();
	 $('#branchHead').hide();
	 $('#dashboard').empty();
	 var dateTo = $('#fromdatepicker').datepicker({ dateFormat: 'yy-mm-dd' }).val();
	
	 dateTo = dateTo ? dateTo : toDate;
	 var cv = new Date(dateTo);
		cv.setDate(cv.getDate() - 21);
		var startDate = cv.getFullYear() +"-"+("0"+(cv.getMonth()+1)).slice(-2)+"-"+("0" + cv.getDate()).slice(-2);
	 dateFirst = startDate ? startDate :firstDate;
	firstDate = dateFirst;
		toDate = dateTo;
		 
		$('#fd').text(firstDate);
	$('#tda').text(toDate);
	  d3.json("resources/js/sheet.json", function(error, data){
	  
	        var d = FormJsonData(data,dateFirst,dateTo);
	  if(d.length > 0)
           dashboard('#dashboard',d);
	  
				
	  });
	
					
		});
	d3.select('#resetId').on('click', function() {
		$('#fromdatepicker').val('');
		//location.reload();
		var toDate1 = new Date();
        toDate1 = toDate1.toISOString().substring(0, 10);
		var cv = new Date(toDate1);
		cv.setDate(cv.getDate() - 21);
		var startDate = cv.getFullYear() +"-"+("0"+(cv.getMonth()+1)).slice(-2)+"-"+("0" + cv.getDate()).slice(-2);
		
		firstDate = startDate;
		toDate = toDate1;
		
		$('#dfg').trigger('click');
	});
	/* End*/
	function dateValueSel(data,firstDate,toDate){
			 
		return data.filter(function (el) {
			return firstDate <= el.mydate && toDate >= el.mydate;
				});
		}
$( "#fromdatepicker" ).datepicker({ dateFormat: 'yy-mm-dd' });

//TODO: read this data from AJAX via this URL
//http://localhost.paypal.com:8080/schedfox-dashboard/profitanalysis?startDate=2015-04-01&endDate=2015-07-28
//start date and end should be driven from UI data picker
d3.json("resources/js/sheet.json", function(error, jData){
var d = FormJsonData(jData,firstDate,toDate);
dashboard('#dashboard',d);
});
function FormJsonData(data,firstDate,toDate){
	data = data;//dateValueSel(data,firstDate,toDate);
	
	
	/* Form data*/
	var expenseMetrics = d3.nest()
	  .key(function(d) {  return d.branchname; })
		
		.rollup(function(v) { return {
			
			
			paid: (d3.sum(v, function(d) {return parseFloat(d.paidamt);})),
			bill: (d3.sum(v, function(d) {return parseFloat(d.billamt);})),
			branchPercentage :(d3.max(v, function(d) {return parseFloat(d.branchPercentage);}))  ,
			
	  }; })				
	  .entries(data);

	 var a = JSON.stringify(expenseMetrics);
	 var c = JSON.parse(a);
	 //console.log(c);
	 return c;
	 /* End */
}
function drawTable(branchName){
$('#tableId').show();
$('#branchHead').show();
$('#tableId').empty();
$('#branchHead').text('Profit analysis of Branch-'+branchName);
d3.json("resources/js/sheet.json", function(error, jData){

//data = dateValueSel(jData,firstDate,toDate)
tabledata = branchNameSel(jData,branchName)
finalData = groupData(tabledata)
finalData.forEach(function(d){d.location=d.values.location;});
finalData.forEach(function(d){d.branchname=d.values.branchname;});
finalData.forEach(function(d){d.paid=d.values.paid;});
finalData.forEach(function(d){d.bill=d.values.bill;});
finalData.forEach(function(d){d.percentage=d.values.locpercentage;});

var columns = ["location","paid","bill","percentage"];
var obj = JSON.stringify(finalData);
for(var i = 0; i < finalData.length; i++) {
    //delete tabledata[i]['branchname'];
	delete finalData[i]['key'];
	delete finalData[i]['values'];
	//delete tabledata[i]['percentage'];
} 
//console.log(finalData);

var table = d3.select("#tableId").append("table").attr("class","table table-bordered table-striped table-hover").attr("style","border-collapse:collapse;"),
        thead = table.append("thead"),
        tbody = table.append("tbody");

    // append the header row
    thead.append("tr")
        .selectAll("th")
        .data(columns)
        .enter()
        .append("th")
            .text(function(column) { return column; });

			
    // create a row for each object in the data
    var rows = tbody.selectAll("tr")
        .data(finalData)
        .enter()
		
        .append("tr");

    // create a cell in each row for each column
    var cells = rows.selectAll("td")
        .data(function(row) {
            return columns.map(function(column) {
                return {column: column, value: row[column]};
            });
        })
        .enter()
		
        .append("td")
		 .on('click', function(d,i){
		 
		//if(i == 0)if(1){
		if(i == 0){
  var lName = $(this).text();
  }else if(i == 1){
  var lName = $(this).prev().text();
  }else if(i == 2){
  var lName = $(this).prev().prev().text();
  }
		
		 $('.highlight_td').removeClass('highlight_td');
			 $(this).closest('tr').addClass('highlight_td');
			//var lName = $(this).text();
			
			var bName = $('#branchHead').text();//$(this).prev().text();
			
			bName =  bName.split("-");
			
			
			
			return subTable(lName,bName[1],firstDate,toDate);
			
		 
		 })
            .text(function(d) { return d.value; });
			
 
});
}
/* For Table Create*/
 
  function branchNameSel(data,selectValue){
		
		return data.filter(function (el) {
				 return selectValue === el.branchname;
				 });
	}
	function locationNameSel(data,lName)	{
	    return data.filter(function (el) {
				 return lName === el.location;
				 });
	}
	
	function removeData(tabledata){
		return tabledata.filter(function (el) {
				return el.branchname;
				 });
		}
	function subTable(lName,bName,firstDate,toDate){
		$('#table2').show();
		$('#locateHead').show();
		$('#table2').empty();
		$('#locateHead').text('Employee details of Location-'+lName);
			d3.json("resources/js/sheet.json", function(error, jData){
			
				data = jData;//dateValueSel(jData,firstDate,toDate)
				tabledata = branchNameSel(data,bName);
				locationData = locationNameSel(tabledata,lName);
				//locationData = paidCheck(locationData);
				//locationData = billCheck(locationData);
			
				/* Delete Unwanted Data from jSON*/
					var obj = JSON.stringify(locationData);
					for(var i = 0; i < locationData.length; i++) {
						delete locationData[i]['branchname'];
						delete locationData[i]['branchid'];
						delete locationData[i]['mydate'];
						delete locationData[i]['percentage'];
						delete locationData[i]['emp_address'];
						delete locationData[i]['emp_email']; 
						/*if(locationData[i]['paidamt']){
							locationData[i]['paidamt'] = locationData[i]['paidamt'];
						}else{
							locationData[i]['paidamt'] = "===";
						}
						if(locationData[i]['billamt']){
							locationData[i]['billamt'] = locationData[i]['billamt'];
						}else{
							locationData[i]['billamt'] = "===";
						} */
					}
				/* End */
				 console.log(locationData);
				var columns = ["emp_name","paidamt","billamt","empPercentage"];
				/* Draw Table*/
				 var table = d3.select("#table2").append("table").attr("class","table table-striped table-hover table-bordered").attr("style","border-collapse:collapse;"),
				thead = table.append("thead"),
				tbody = table.append("tbody");
 
			// append the header row
			thead.append("tr")
				.selectAll("th")
				.data(columns)
				.enter()
				.append("th")
					.text(function(column) { return column; });

			// create a row for each object in the data
			var rows = tbody.selectAll("tr")
				.data(locationData)
				.enter()
				.append("tr");

			// create a cell in each row for each column
			var cells = rows.selectAll("td")
				.data(function(row) {
					return columns.map(function(column) {
						return {column: column, value: row[column]};
					});
				})
				.enter()
				.append("td")
					.text(function(d) { return d.value; });
				/* End */
			});
				
	}
	function groupData(tabledata){
			
		var expenseMetrics = d3.nest()
		  .key(function(d) {  return d.location; })
			
			.rollup(function(v) {  return {
				
				branchname: (d3.max(v, function(d) {return (d.branchname);})),
				location: (d3.max(v, function(d) {return (d.location);})),
				locpercentage: (d3.max(v, function(d) {return (d.locPercentage);})),
				//paid: (d3.sum(v, function(d) {return parseFloat(d.paidamt);})).toFixed(2),
				paid: (d3.max(v, function(d) {return parseFloat(d.locpaidamt);})),
				bill: (d3.max(v, function(d) {return parseFloat(d.locbillamt);})),
				//bill: (d3.sum(v, function(d) {return parseFloat(d.billamt);})).toFixed(2),
				//paidPercentage :(d3.sum(v, function(d) {return ((parseFloat(d.paidamt) / parseFloat(d.billamt)) * 100/ v.length );})).toFixed(2)  ,
				
		  }; })				
		  .entries(tabledata);

		 var a = JSON.stringify(expenseMetrics);
		 var c = JSON.parse(a);
		//console.log(c);
		 return c;
	 
	}