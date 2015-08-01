var firstDate = "";
var toDate = "";
var lName;

var lowColor = 64;
var highColor = 68;

var toDate = new Date();
toDate = toDate.toISOString().substring(0, 10);
var cv = new Date(toDate);
cv.setDate(cv.getDate() - 21);
var startDate = cv.getFullYear() + "-" + ("0" + (cv.getMonth() + 1)).slice(-2)
		+ "-" + ("0" + cv.getDate()).slice(-2);

firstDate = startDate;
toDate = toDate;

var apiUrl = "profitanalysis?startDate=" + startDate + "&endDate=" + toDate;

// ajax loader functions
var spinnerVisible = false;
function showProgress() {
	if (!spinnerVisible) {
		$("div#spinner").fadeIn("fast");
		spinnerVisible = true;
	}
};
function hideProgress() {
	if (spinnerVisible) {
		var spinner = $("div#spinner");
		spinner.stop();
		spinner.fadeOut("fast");
		spinnerVisible = false;
	}
};

function dashboard(id, fData) {
	var barRed = "#E84C3D";
	var barGreen = "#2ECD71";
	var barYellow = "#F2C40F";

	$('#fd').text(firstDate);
	$('#tda').text(toDate);
	function segColor(c) {
		return {
			bill : "#F39C12",
			paid : "#41ab5d"
		}[c];
	}

	function wrap(text, width) {
		text
				.each(function() {
					var text = d3.select(this), words = text.text()
							.split(/\s+/).reverse(), word, line = [], lineNumber = 0, lineHeight = 1.1, // ems
					y = text.attr("y"), dy = parseFloat(text.attr("dy")), tspan = text
							.text(null).append("tspan").attr("x", -25).attr(
									"y", 3).attr("dy", dy + "em");
					text.attr("transform", "rotate(-65)");
					while (word = words.pop()) {
						line.push(word);
						tspan.text(line.join(" "));
						if (tspan.node().getComputedTextLength() > width) {
							line.pop();
							tspan.text(line.join(" "));
							line = [ word ];
							tspan = text.append("tspan").attr("x", -25).attr(
									"y", 3).attr("dy",
									++lineNumber * lineHeight + dy + "em")
									.text(word);
						}
					}
				});
	}

	// function to handle histogram.
	function histoGram(fD) {
		var colorred = d3.scale.ordinal().range([ barRed ]);
		var colorgreen = d3.scale.ordinal().range([ barGreen ]);
		var coloryellow = d3.scale.ordinal().range([ barYellow ]);

		var hG = {}, hGDim = {
			t : 20,
			r : 0,
			b : 250,
			l : 0
		};
		hGDim.w = 1000 - hGDim.l - hGDim.r, hGDim.h = 400 - hGDim.t - hGDim.b;

		// create svg for histogram.
		var hGsvg = d3.select(id).append("svg").attr("class", "barchartsvg")
				.attr("width", hGDim.w + hGDim.l + hGDim.r)
				// .attr("width", 1600)
				.attr("height", 270).append("g").attr("transform",
						"translate(" + hGDim.l + "," + hGDim.t + ")");

		// create function for x-axis mapping.
		var x = d3.scale.ordinal().rangeRoundBands([ 0, hGDim.w ], 0.1).domain(
				fD.map(function(d) {
					return d.branchName;
				}));

		var insertLinebreaks = function(d) {
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
		hGsvg.append("g").attr("class", "x axis").attr("transform",
				"translate(0," + hGDim.h + ")").call(
				d3.svg.axis().scale(x).orient("bottom"))
				.selectAll(".tick text").call(wrap, x.rangeBand());

		// Create function for y-axis map.
		var y = d3.scale.linear().range([ hGDim.h, 0 ]).domain(
				[ 0, d3.max(fD, function(d) {
					return d.percent;
				}) ]);

		// Create bars for histogram to contain rectangles and freq labels.
		var bars = hGsvg.selectAll(".bar").data(fD).enter().append("g").attr(
				"class", "bar");

		// create the rectangles.
		bars.append("rect").attr("x", function(d) {
			return x(d.branchName);
		}).attr("y", function(d) {
			return y(d.percent);
		})
		// .attr("width", x.rangeBand())
		.attr("width", x.rangeBand()).attr("height", function(d) {
			return hGDim.h - y(d.percent);
		})

		.attr(
				"fill",
				function(d, i) {
					if ((d.percent * 100.0) < lowColor)
						return colorgreen(i % 1);
					else if ((d.percent * 100.0) < highColor
							&& (d.percent * 100.0) > lowColor)
						return coloryellow(i % 1);
					else if ((d.percent * 100.0) > highColor)
						return colorred(i % 1);
				})

		.on("mouseover", mouseover)// mouseover is defined below.
		.on("mouseout", mouseout);// mouseout is defined below.

		// Create the frequency labels above the rectangles.
		bars.append("text").text(
				function(d) {
					return d3.format(",")(
							parseFloat(d.percent * 100.0).toFixed(2))
							+ "%"
				}).attr("x", function(d) {
			return x(d.branchName) + x.rangeBand() / 2;
		}).attr("y", function(d) {
			return y(d.percent) - 5;
		}).attr("text-anchor", "middle");

		function mouseover(d) {
			$('#table2').hide("slow");
			$('#locateHead').hide("slow");
			//before draw table reset filters
			SCHEDFOX.filters.resetFilters();
			drawTable(d, firstDate, toDate);
			LOCATION_ROWS = $('div#tableId table tr').not('thead tr');

			console.log(LOCATION_ROWS);
		}

		function mouseout(d) { // utility function to be called on mouseout.
			// reset the pie-chart and legend.
			// $('#split').hide();
			// pC.update(tF);
			// leg.update(tF);
		}

		// create function to update the bars. This will be used by pie-chart.
		hG.update = function(nD, color, pieMouse) {
			// console.log(nD)
			// update the domain of the y-axis map to reflect change in
			// frequencies.
			y.domain([ 0, d3.max(nD, function(d) {
				return d.percent;
			}) ]);

			// Attach the new data to the bars.
			var bars = hGsvg.selectAll(".bar").data(nD);

			// transition the height and color of rectangles.
			bars.select("rect").transition().duration(500).attr("y",
					function(d) {
						return y(d.percent);
					}).attr("height", function(d) {
				return hGDim.h - y(d.percent);
			})
			// .attr("fill", function(d, i) { if(pieMouse){} else{return color;}
			// } );
			.attr(
					"fill",
					function(d, i) {
						if (pieMouse) {
							if ((d.percent * 100.0) < lowColor)
								return colorgreen(i % 1);
							else if ((d.percent * 100.0) < highColor
									&& (d.percent * 100.0) > lowColor)
								return coloryellow(i % 1);
							else if ((d.percent * 100.0) > highColor)
								return colorred(i % 1);
						} else {
							return color;
						}
					});

			// transition the frequency labels location and change value.
			bars.select("text").transition().duration(500).text(function(d) {
				return d3.format(",")(d.percent)
			}).attr("y", function(d) {
				return y(d.percent) - 5;
			});
		}
		return hG;
	}

	// function to handle pieChart.
	function pieChart(pD) {

		var pC = {}, pieDim = {
			w : 200,
			h : 250
		};
		pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;

		// create svg for pie chart.
		var piesvg = d3.select(id).append("svg").attr("class", "piechartsvg")
				.attr("width", pieDim.w).attr("height", 250).append("g").attr(
						"transform",
						"translate(" + pieDim.w / 2 + "," + pieDim.h / 2 + ")");

		// create function to draw the arcs of the pie slices.
		var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

		// create a function to compute the pie slice angles.

		var pie = d3.layout.pie().sort(null).value(function(d) {
			// console.log(d);
			// if(d.type != 'paidPercentage')
			return d.values;
		});

		// Draw the pie slices.
		piesvg.selectAll("path").data(pie(pD)).enter().append("path").attr("d",
				arc).each(function(d) {
			this._current = d;
		}).style("fill", function(d) {
			return segColor(d.data.type);
		}).on("mouseover", mouseover).on("mouseout", mouseout);

		// create function to update pie-chart. This will be used by histogram.
		pC.update = function(nD) {
			piesvg.selectAll("path").data(pie(nD)).transition().duration(500)
					.attrTween("d", arcTween);
		}
		// Utility function to be called on mouseover a pie slice.
		function mouseover(d) {
			$('#split').hide();
			// call the update function of histogram with new data.
			/*
			 * hG.update(fData.map(function(v){ return
			 * [v.key,v.values[d.data.type]];}),segColor(d.data.type));
			 */
		}
		// Utility function to be called on mouseout a pie slice.
		function mouseout(d) {
			$('#split').hide();
			// call the update function of histogram with all data.
			/*
			 * hG.update(fData.map(function(v){ return [v.key,v.total];}),
			 * barColor,pieMouse = 1);
			 */
		}
		// Animating the pie-slice requiring a custom function which specifies
		// how the intermediate paths should be drawn.
		function arcTween(a) {
			var i = d3.interpolate(this._current, a);
			this._current = i(0);
			return function(t) {
				return arc(i(t));
			};
		}
		return pC;
	}

	// function to handle legend.
	function legend(lD) {
		// console.log(lD);

		var leg = {};

		// create table for legend.
		var legend = d3.select(id).append("table").attr('class', 'legend');

		// create one row per segment.
		var tr = legend.append("tbody").selectAll("tr").data(lD).enter()
				.append("tr");

		// create the first column for each segment.
		tr.append("td").append("svg").attr("width", '16').attr("height", '16')
				.append("rect").attr("width", '16').attr("height", '16').attr(
						"fill", function(d) {
							return segColor(d.type);
						});

		// create the second column for each segment.
		tr.append("td").text(function(d) {
			return d.type;
		});

		// create the third column for each segment.
		tr.append("td").attr("class", 'legendFreq').text(function(d) {
			return d3.format(",")(parseFloat(d.values).toFixed(2));
		});

		// create the fourth column for each segment.
		tr.append("td").attr("class", 'legendPerc').text(function(d) {
			return getLegend(d, lD);
		});

		// Utility function to be used to update the legend.
		leg.update = function(nD) {
			// update the data attached to the row elements.
			var l = legend.select("tbody").selectAll("tr").data(nD);

			// update the frequencies.
			l.select(".legendFreq").text(function(d) {
				return d3.format(",")(parseFloat(d.values).toFixed(2));
			});

			// update the percentage column.
			l.select(".legendPerc").text(function(d) {
				return getLegend(d, nD);
			});
		}

		function getLegend(d, aD) { // Utility function to compute percentage.
			// console.log(d);
			return d3.format("%")(d.values / d3.sum(aD.map(function(v) {
				return parseFloat(v.values).toFixed(2);
			})));
		}
		return leg;
	}

	var hG = histoGram(fData); // create the histogram.
}
// end of dashboard function

/* Button Click */
d3.select('#dfg').on(
		'click',
		function() {
			$('#table2').hide();
			$('#tableId').hide();
			$('#locateHead').hide();
			$('#branchHead').hide();
			$('#dashboard').empty();
			var dateTo = $('#fromdatepicker').datepicker({
				dateFormat : 'yy-mm-dd'
			}).val();

			dateTo = dateTo ? dateTo : toDate;
			var cv = new Date(dateTo);
			cv.setDate(cv.getDate() - 21);
			var startDate = cv.getFullYear() + "-"
					+ ("0" + (cv.getMonth() + 1)).slice(-2) + "-"
					+ ("0" + cv.getDate()).slice(-2);
			dateFirst = startDate ? startDate : firstDate;
			firstDate = dateFirst;
			toDate = dateTo;

			$('#fd').text(firstDate);
			$('#tda').text(toDate);
			d3.json("resources/js/sheet.json", function(error, data) {
				var d = FormJsonData(data, dateFirst, dateTo);
				if (d.length > 0) {
					dashboard('#dashboard', d);
				}

			});

		});

d3.select('#resetId').on(
		'click',
		function() {
			$('#fromdatepicker').val('');
			// location.reload();
			var toDate1 = new Date();
			toDate1 = toDate1.toISOString().substring(0, 10);
			var cv = new Date(toDate1);
			cv.setDate(cv.getDate() - 21);
			var startDate = cv.getFullYear() + "-"
					+ ("0" + (cv.getMonth() + 1)).slice(-2) + "-"
					+ ("0" + cv.getDate()).slice(-2);

			firstDate = startDate;
			toDate = toDate1;

			$('#dfg').trigger('click');
		});
/* End */
function dateValueSel(data, firstDate, toDate) {

	return data.filter(function(el) {
		return firstDate <= el.mydate && toDate >= el.mydate;
	});
}
$("#fromdatepicker").datepicker({
	dateFormat : 'yy-mm-dd'
});

// ajax loader start
showProgress();

var jqxhr = $.getJSON(apiUrl, function(error, data) {
}).done(function(data) {
	dashboard('#dashboard', data);
}).fail(function(error) {
}).always(function() {
	// ajax loader stop
	hideProgress();
});

/**
 * This function is responsible for drawing out the locations table once a
 * branch has been moused over. D is the data returned by the server w/o
 * translation as represented by the Branch object in java with it's
 * corresponding locations and employees nested in collections
 * 
 * @param Object
 *            d
 * @returns {undefined}
 */
function drawTable(d) {
	$('#tableId').show();
	$('#branchHead').show();
	$('#tableId').empty();
	$('#branchHead').text('Profit analysis of - ' + d.branchName);

	var columns = [ "location", "paid", "bill", "percent" ];

	var table = d3.select("#tableId").append("table").attr("class",
			"table table-bordered table-hover").attr("style",
			"border-collapse:collapse;"), thead = table.append("thead"), tbody = table
			.append("tbody");

	thead.append("tr").selectAll("th").data(columns).enter().append("th").attr(
			"class", function(data) {
				if (data === "location") {
					return "col-xs-9 col-sm-6 col-lg-8";
				} else {
					return "col-xs-4 col-sm-2 col-lg-3";
				}
			}).text(function(column) {
		return column;
	});

	// create a row for each object in the data
	var rows = tbody.selectAll("tr").data(d.locations).enter().append("tr")
			.attr(
					"class",
					function(data) {
						if ((data.percentage * 100.0) > highColor) {
							return 'highRow';
						} else if ((data.percentage * 100.0) < highColor
								&& (data.percentage * 100.0) > lowColor) {
							return 'mediumRow'
						} else {
							return 'lowRow';
						}
					});

	// create a cell in each row for each column
	var cells = rows.selectAll("td").data(function(row) {
		return columns.map(function(column) {
			if (column === 'location') {
				return {
					column : column,
					value : row.locationName,
					data : row
				};
			} else if (column === 'paid') {
				return {
					column : column,
					value : parseFloat(row.paidAmount).toFixed(2),
					data : row
				};
			} else if (column === 'bill') {
				return {
					column : column,
					value : parseFloat(row.billAmount).toFixed(2),
					data : row
				};
			} else {
				return {
					column : column,
					value : parseFloat(row.percentage * 100.0).toFixed(2),
					data : row
				};
			}
		});
	}).enter().append("td").on('click', function(row, c, i) {
		var location = row.data;
		$('.highlight_td').removeClass('highlight_td');
		$(this).closest('tr').addClass('highlight_td');
		return employeeSubTable(location);
	}).text(function(d) {
		return d.value;
	});
}

function branchNameSel(data, selectValue) {

	return data.filter(function(el) {
		return selectValue === el.branchname;
	});
}
function locationNameSel(data, lName) {
	return data.filter(function(el) {
		return lName === el.location;
	});
}

function removeData(tabledata) {
	return tabledata.filter(function(el) {
		return el.branchname;
	});
}
function employeeSubTable(locationData) {
	$('#table2').show();
	$('#locateHead').show();
	$('#table2').empty();
	$('#locateHead').text('Employees @ ' + locationData.locationName);

	var columns = [ "name", "paid", "bill", "percent" ];
	/* Draw Table */
	var table = d3.select("#table2").append("table").attr("class",
			"table table-hover table-bordered").attr("style",
			"border-collapse:collapse;"), thead = table.append("thead"), tbody = table
			.append("tbody");

	// append the header row
	thead.append("tr").selectAll("th").data(columns).enter().append("th").text(
			function(column) {
				return column;
			});

	// create a row for each object in the data
	var rows = tbody.selectAll("tr").data(locationData.employeeMetricsList)
			.enter().append("tr").attr(
					"class",
					function(data) {
						if ((data.percentage * 100.0) > highColor) {
							return 'highRow';
						} else if ((data.percentage * 100.0) < highColor
								&& (data.percentage * 100.0) > lowColor) {
							return 'mediumRow'
						} else {
							return 'lowRow';
						}
					});

	// create a cell in each row for each column
	var cells = rows.selectAll("td").data(function(row) {
		return columns.map(function(column) {
			if (column === 'name') {
				return {
					column : column,
					value : row.employeeName,
					data : row
				};
			} else if (column === 'paid') {
				return {
					column : column,
					value : parseFloat(row.paidAmount).toFixed(2),
					data : row
				};
			} else if (column === 'bill') {
				return {
					column : column,
					value : parseFloat(row.billAmount).toFixed(2),
					data : row
				};
			} else {
				return {
					column : column,
					value : parseFloat(row.percentage * 100.0).toFixed(2),
					data : row
				};
			}
		});
	}).enter().append("td").text(function(d) {
		return d.value;
	});

}

// filter code starts here
var SCHEDFOX = SCHEDFOX || {};
var LOCATION_ROWS = null;
SCHEDFOX.filters = {

	user_filter_options : {
		high : false,
		low : false,
		med : false
	},
	filterRows : function(el) {
		// check user selected before table drawn
		if ($(el).is(':checked')) {
			if (el && el.value === 'high') {
				this.user_filter_options.high = true;
			}
			if (el && el.value === 'med') {
				this.user_filter_options.med = true;
			}
			if (el && el.value === 'low') {
				this.user_filter_options.low = true;
			}
		} else {
			// they are unchecked
			if (el && el.value === 'high') {
				this.user_filter_options.high = false;
			}
			if (el && el.value === 'med') {
				this.user_filter_options.med = false;
			}
			if (el && el.value === 'low') {
				this.user_filter_options.low = false;
			}
		}

		if (LOCATION_ROWS) {
			
			if (this.isAnyFilterChecked()) {
				console.log('inside location rows checked');
				if(this.isAllFiltersChecked()) {
					this.displayAllRows();
					return;
				}
				//handle user selects any one
				if(this.user_filter_options.high && !this.user_filter_options.med && !this.user_filter_options.low) {
					console.log('only high profit filter selected');
					var lowRow = LOCATION_ROWS.filter('.lowRow').show();
					LOCATION_ROWS.not(lowRow).hide('slow');
					return;
				}
				if(this.user_filter_options.med && !this.user_filter_options.high && !this.user_filter_options.low) {
					console.log('only medium profit filter selected');
					var mediumRow = LOCATION_ROWS.filter('.mediumRow').show();
					LOCATION_ROWS.not(mediumRow).hide('slow');
					return;
				}
				if(this.user_filter_options.low && !this.user_filter_options.med && !this.user_filter_options.high) {
					console.log('only low profit filter selected');
					var highRow = LOCATION_ROWS.filter('.highRow').show();
					LOCATION_ROWS.not(highRow).hide('slow');
					return;
				}
				
				//handle user selects any two filters
				if(this.user_filter_options.high && this.user_filter_options.med && !this.user_filter_options.low) {
					console.log('user selected high and medium profit filters');
					var lowRow = LOCATION_ROWS.filter('.lowRow').show();
					LOCATION_ROWS.not(lowRow).hide('slow');
					LOCATION_ROWS.filter('.mediumRow').show('slow');
					return;
				}
				
				if(this.user_filter_options.high && !this.user_filter_options.med && this.user_filter_options.low) {
					console.log('user selected high and low profit filters');
					var lowRow = LOCATION_ROWS.filter('.lowRow').show();
					LOCATION_ROWS.not(lowRow).hide('slow');
					LOCATION_ROWS.filter('.highRow').show('slow');
					return;
				}
				
				if(!this.user_filter_options.high && this.user_filter_options.med && this.user_filter_options.low) {
					console.log('user selected med and low profit filters');
					var highRow = LOCATION_ROWS.filter('.highRow').show();
					LOCATION_ROWS.not(highRow).hide('slow');
					LOCATION_ROWS.filter('.mediumRow').show('slow');
					return;
				}
//				
			} else {
				this.displayAllRows();
				return;
			}
		
		}// end of location_rows check

	},
	isAllFiltersChecked : function() {
		return (this.user_filter_options.high && this.user_filter_options.med && this.user_filter_options.low); 
	},
	isAnyFilterChecked : function () {
		return (this.user_filter_options.high || this.user_filter_options.med || this.user_filter_options.low) ;
			
	},
	displayAllRows: function () {
		console.log('inside show all');
		var highRow = LOCATION_ROWS.filter('.highRow').show('slow');
		var highRow = LOCATION_ROWS.filter('.mediumRow').show('slow');
		var highRow = LOCATION_ROWS.filter('.lowRow').show('slow');
	},
	resetFilters: function() {
		$("input:checkbox").removeAttr('checked');
		this.user_filter_options.high = false;
		this.user_filter_options.low = false;
		this.user_filter_options.med = false;
	}
};

$(document).ready(function() {

	$('div.checkbox input').click(function(e) {

		switch (this.value) {
		case 'high':
			// code block
			SCHEDFOX.filters.filterRows(this);
			break;
		case 'med':
			// code block
			SCHEDFOX.filters.filterRows(this);
			break;
		case 'low':
			// code block
			SCHEDFOX.filters.filterRows(this);
			break;

		}

	});

	$('#showWhiteButton').click(function() {
		var white = rows.filter('.white').show();
		rows.not(white).hide();
	});

	$('#showAll').click(function() {
		rows.show();
	});

});
