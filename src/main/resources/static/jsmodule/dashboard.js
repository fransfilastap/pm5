/**
 * Frans Filasta Pratama
 */
var Dashboard = function( baseurl ){
    this._baseurl = baseurl;
    this.tableNeDown = $("table#nedown");
    this.tableDownAging = $("table#aging");
    this.tableStatusHourly = $("table#status_hourly");
    this.tableStatusDaily = $("table#status_daily");
    this.__construct();

}

Dashboard.prototype.__construct = function(){

    this._loadNEDownAging();
    this._loadNEDown();
    this._loadNEDownStatusHourly();
    this._loadNeDownStatusDaily();
    
	this.__scheduled();
    this.__datatable();
    //this.__initDateTimePicker();
    this.__initFilter();

    this.timespan1 = "_1WEEK";
    this.timespan2 = "_1WEEK";

    this.__loadChartData( { age : "LTOA" , type : "HOURLY", timespan : "_1WEEK" } );
    this.__loadChartData( { age : "LTOA" , type : "DAILY", timespan : "_1WEEK" } );
    
    
    this.__loadNetworkAvailabilityChartData({
        tch_cell : "2G",
        type : "HOURLY",
        mode : "CLUSTER",
        timespan : "TODAY",
        region : "JABODETABEK_1",
        elem : "#container_avail_hourly"
    });
    
    
    this.__loadNetworkAvailabilityChartData({
        tch_cell : "2G",
        type : "HOURLY",
        mode : "REGION",
        timespan : "TODAY",
        elem : "#container_avail_daily"
    });
    
    this.__loadNetworkAvailabilityChartData({
        tch_cell : "2G",
        type : "DAILY",
        mode : "DAILY_REGION",
        timespan : "THIS_MONTH",
        elem : "#container_nav_daily"
    });    

    this.clusterAvailabilityTimeSpan = "TODAY";
    this.regionAvailabilityTimeSpan = "TODAY";

    var link = "export-clusternav?region=JABODETABEK_1&span=TODAY";
    var link2 = "export-regionalnav?span=TODAY";

    $("#cluster_avail_download").attr("href",link);
    $("#region_avail_download").attr("href",link2);   
    
    $("a#export_dailynav").attr("href","/export-dailynav?span=this_month");
}

Dashboard.prototype.__listByNE = function( ne, region ){


    var url = this._baseurl+"/dashboard/getSiteDownListByNe";

    jQuery.ajax({
        url : url,
        method : "POST",
        data : { "ne" : ne, "region" : region },
        dataType : "JSON",
        success : function(response){
            var datax = [];
            jQuery.each( response, function(i,v){
                datax.push( [ (i+1) , v.REGION, v.BTSID, v.BTSNAME, v.NODE, v.SUMMARY, v.TTNO ] );
            });

            this.__nedown_datatable = $("#nedown_table").DataTable({
                data: datax,
                columns: [
                    { title: "#" },
                    { title: "REGION" },
                    { title: "SITE ID" },
                    { title: "SITE NAME" },
                    { title: "NODE" },
                    { title: "ALARM" },
                    { title: "TTNO" }
                ],
                destroy : true
            });
        }
    });
}


Dashboard.prototype.__initDateTimePicker = function( elem ){

    var self = this;

    $('#reportrange span').html(moment().subtract(29, 'days').format('YYYY-MM-DD hh:mm:ss') + ' - ' + moment().format('YYYY-MM-DD hh:mm:ss'));

    $('#reportrange').daterangepicker({
        format: 'MM/DD/YYYY',
        startDate: moment().subtract(29, 'days'),
        endDate: moment(),
        minDate: '01/01/2012',
        maxDate: '12/31/2015',
        dateLimit: { days: 60 },
        showDropdowns: true,
        showWeekNumbers: true,
        timePicker: true,
        timePickerIncrement: 1,
        timePicker12Hour: false,
        timePickerSeconds : false,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        opens: 'right',
        drops: 'down',
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-primary',
        cancelClass: 'btn-default',
        separator: ' to ',
        locale: {
            applyLabel: 'Submit',
            cancelLabel: 'Cancel',
            fromLabel: 'From',
            toLabel: 'To',
            customRangeLabel: 'Custom',
            daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
            monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            firstDay: 1
        }
    }, function(start, end, label) {

        $('#reportrange span').html(start.format('YYYY-MM-DD hh:mm:ss') + ' - ' + end.format('YYYY-MM-DD hh:mm:ss'));
        self.startDate   = start;
        self.endDate     = end;

    });

    $("#reportrange").on('apply.daterangepicker',function(ev,picker){
        var st      = self.startDate.format('YYYY-MM-DD');
        var et      = self.endDate.format('YYYY-MM-DD');
        //self.loadSplineData( st,et );
    });

}


Dashboard.prototype.__listByAging = function( aging, region ){


}

Dashboard.prototype.__initFilter = function(){

    var self = this;

    $("select#agingHourly").change(function(event){
        self.agingHourly = $(this).val();
        if( self.timespan1 == undefined )
          self.timespan1 = "_1WEEK";
        self.__loadChartData( { age : self.agingHourly , type : "hourly", timespan : self.timespan1 }  );
    });

    $("select#timespan1").change(function(event){
        self.timespan1 = $(this).val();
        self.__loadChartData( { age : $("select#agingHourly").val() , type : "hourly", timespan : self.timespan1 }  );
    });

    $("select#agingDaily").change(function(event){
        self.agingDaily = $(this).val();
        if( self.timespan2 == undefined )
          self.timespan2 = "_1WEEK";
        self.__loadChartData( { age : self.agingDaily , type : "DAILY", timespan : self.timespan2 } );
    });

    $("select#timespan2").change(function(event){
        self.timespan2 = $(this).val();
        self.__loadChartData( { age : $("select#agingDaily").val() , type : "DAILY" , timespan : self.timespan2 }  );
    });
    
    $("select#hourly_type").change(function(event){
        var type = $(this).val();
        self.__loadNetworkAvailabilityChartData( {
            tch_cell : type,
            type:"HOURLY",
            timespan : $("select#availtimespan1").val(),
            region : "JABODETABEK_1",
            elem : "#container_avail_hourly"
        });
    });

    
    $("select#daily_type").change(function(event){
      var type = $(this).val();
      console.log( type );
      self.__loadNetworkAvailabilityChartData( {
          tch_cell : type,
          type : "DAILY",
          mode : "REGION",
          timespan : $("select#availtimespan2").val(),
          elem : "#container_avail_daily"
      });
    });

    $("select#availtimespan1").change(function(event){
        var timespan_ = $(this).val();
        var type = $("select#hourly_type").val();
        var region = $("select#cregion").val();
        self.__loadNetworkAvailabilityChartData( {
        	tch_cell : type,
            type : "HOURLY",
            timespan : timespan_,
            region : region,
            elem : "#container_avail_hourly"
        });

        var link = "export-clusternav?region="+region+"&span="+timespan_;
        $("#cluster_avail_download").attr("href",link);
    });
    

    $("select#cregion").change(function(evt){
        var region = $(this).val();
        var timespan_ = $("select#availtimespan1").val();
        var type = $("select#hourly_type").val();
        self.__loadNetworkAvailabilityChartData( {
        	tch_cell : type,
            type : "HOURLY",
            timespan : timespan_,
            region : region,
            elem : "#container_avail_hourly"
        });

        var link = "export-clusternav?region="+region+"&span="+timespan_;
        $("#cluster_avail_download").attr("href",link);    		
    });
    
    $("select#availtimespan2").change(function(event){
        var timespan_ = $(this).val();
        var type = $("select#daily_type").val();
        self.__loadNetworkAvailabilityChartData( {
            tch_cell : type,
            type : "DAILY",
            mode : "REGION",
            timespan : $("select#availtimespan2").val(),
            elem : "#container_avail_daily"
        });
        
        var link = "export-regionalnav?span="+timespan_;
        $("#region_avail_download").attr("href",link);

    });

    $("select#timespanxx").change(function(event){
    	var _timespan = $(this).val();
    	var tch = $("select#technologyxx").val();
        self.__loadNetworkAvailabilityChartData({
            tch_cell : tch,
            type : "DAILY",
            mode : "DAILY_REGION",
            timespan : _timespan,
            elem : "#container_nav_daily"
        }); 
        
        var url = "/export-dailynav?span="+_timespan.toLowerCase();
        console.log(_timespan);
        
        $("a#export_dailynav").attr("href",url);
    });
    
    $("select#technologyxx").change(function(event){
    	var tch = $(this).val();
    	var _timespan = $("select#timespanxx").val();
        self.__loadNetworkAvailabilityChartData({
            tch_cell : tch,
            type : "DAILY",
            mode : "DAILY_REGION",
            timespan : _timespan,
            elem : "#container_nav_daily"
        }); 
    });
    
    

}


Dashboard.prototype.__barChart = function( id, data, title , suffix  ){

    var chartOptions = {
        chart: {
            type: 'spline'
        },
        title: {
            text: title
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Value'
            }
        },
            tooltip: {
                valuePrefix: '',
                valueSuffix: ' '+suffix
            },
        series: []
    };

    $.each( data.categories, function(index, value){
        var cat = value;
        chartOptions.xAxis.categories.push( cat );
    });

    $.each(data.series_data,function(index,value){
        var srs = {"name":index,"data":value};
        chartOptions.series.push( srs );
    });

    var chart =  $( id ).highcharts( chartOptions );

    return chart;

}

Dashboard.prototype.__trendChart = function( id, data ,title, suffix,column){
        if( suffix == undefined ){
            suffix = "";
        }

        var chartOptions = {
            chart: {
                type: 'spline'
            },
            title: {
                text: title
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: { // don't display the dummy year
                    month: '%e. %b',
                    year: '%b'
                },
                title: {
                    text: 'Date'
                }
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                labels: {
                    formatter: function () {
                        return this.value;
                    }
                }
                ,
                min : 0
            },
            tooltip: {
                valuePrefix: '',
                valueSuffix: ' '+suffix
            },
            credits: {enabled: false },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 4,
                        lineColor: '#666666',
                        lineWidth: 1
                    }
                }
            },
            series: []
        };

    $.each(data,function(index,value){
        var srs = {"name":index,"data":[]};
        var dt = value;
        $.each( dt, function(i,v){
            var sd = [ v.time , v.totalDown ];
            srs.data.push( sd );
        });

        chartOptions.series.push( srs );
    });

    var chart =  $( id ).highcharts( chartOptions );

    return chart;
}

Dashboard.prototype.__availabilityTrendChart = function(id, data ,title, suffix,column){
        var chartOptions = {
            chart: {
                type: 'spline'
            },
            title: {
                text: title
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: { // don't display the dummy year
                    month: '%e. %b',
                    year: '%b'
                },
                title: {
                    text: 'Date'
                }
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                labels: {
                    formatter: function () {
                        return this.value;
                    }
                }
                ,
                max : 100
            },
            tooltip: {
                valuePrefix: '',
                valueSuffix: ' %'
            },
            credits: {enabled: false },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 4,
                        lineColor: '#666666',
                        lineWidth: 1
                    }
                }
            },
            series: []
        };
        
    $.each(data,function(index,value){
        var srs = {"name":index,"data":[]};
        var dt = data[index];
        $.each( dt, function(i,v){
            var sd = [ v.time , v.availability ];
            srs.data.push( sd );
        });

        chartOptions.series.push( srs );
    });

    var chart =  $( id ).highcharts( chartOptions );

    return chart;
}

Dashboard.prototype.__loadChartData = function( options ){

    var _url = this._baseurl+"/nedowntrend";
    var self = this;

    if( !options ){
        throw "no options supplied";
    }

    var chartElemId = "#container";

    if( options.type == "DAILY" ){
        chartElemId = "#container2";
    }
    /*
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	*/
    $.ajax({
        url : _url,
        contentType: 'application/json',
        dataType : "JSON",
        data : JSON.stringify(options),
        method : "POST",
        success : function( response ){
            self.__trendChart( chartElemId, response,"NE DOWN TREND" );
        }
    });
    

}

Dashboard.prototype.__loadNetworkAvailabilityChartData = function( options ){
    var _url = this._baseurl;
    var self = this;
    
    if( options.mode == undefined )
    	_url = _url+"/clusteravailbility";
    else{
    	if( options.mode == "REGION" )
    		_url = _url+"/regionavailability";
    	else if( options.mode == "CLUSTER" )
    		_url = _url+"/clusteravailbility";
    	else if( options.mode == "DAILY_REGION" )
    		_url = _url+"/dailyregionavailability"
    	else
    		alert("Please specify availability level (Cluster/Region)");
    }

    if( options.tch_cell == undefined ){
        options.tch_cell = "2G";
    }

    if( options.timespan == undefined ){
        options.timespan == "TODAY";
    }
    
    var datae = {region: options.region, netype : options.tch_cell, timespan : options.timespan, type : options._timespan};
    
    $.ajax({
        url : _url,
        dataType : "JSON",
        method : "POST",
        contentType: 'application/json',
        data : JSON.stringify(datae),
        success : function( response ){
            self.__availabilityTrendChart( options.elem, response ,"AVAILABILITY TREND","%" );
        }
    });
}

Dashboard.prototype.__datatable = function(){
    this.__nedown_datatable = $("#nedown_table").DataTable({
        data: [],
        columns: [
            { title: "#" },
            { title: "REGION" },
            { title: "SITE ID" },
            { title: "SITE NAME" },
            { title: "NODE" },
            { title: "ALARM" },
            { title: "TTNO" }
        ]
    });

    this.__nedown_aging_datatable = $("#nedown_aging_table").DataTable({
        data: [],
        columns: [
            { title: "#" },
            { title: "REGION" },
            { title: "SITE ID" },
            { title: "SITE NAME" },
            { title: "NODE" },
            { title: "ALARM" },
            { title: "TTNO" }
        ]
    });

}

Dashboard.prototype._loadNEDown = function(){
    var url = this._baseurl+"/nedownsummary";
    var self = this;

    var _nedownrow = self.tableNeDown.find("tbody");
    
    jQuery.ajax({
        url : url,
        dataType : "JSON",
        method : "GET",
        success : function(response){
    
        	var _nedownData = response;
        	
        	_nedownrow.html("");
        	
            jQuery.each( _nedownData, function(i,v){
                var row = "<tr>"+
                           "<td class='text-align-center'>"+v.region+"</td>"+
                            "<td class='text-align-center'><a href='#' data-ne='2g' class='m-nedown' data-region='"+v.region+"'><span class='badge bg-danger'>"+v.neDown2g+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' data-ne='3g' class='m-nedown' data-region='"+v.region+"'><span class='badge bg-danger'>"+v.neDown3g+"</span></a></td>"+
                            "<td class='text-align-center'>"+v.totalNe2g+"</td>"+
                            "<td class='text-align-center'>"+v.totalNe3g+"</td>"+
                            "<td class='text-align-center'>"+parseFloat(v.percentageNeDown2g).toFixed(2)+" %</td>"+
                            "<td class='text-align-center'>"+parseFloat(v.percentageNeDown3g).toFixed(2)+" %</td>"+
                        "</tr>";
                _nedownrow.append( row );
            });
        }
    })	
}

Dashboard.prototype._loadNEDownAging = function(){
    var url = this._baseurl+"/nedownaging";
    var self = this;

    var _agingrow = self.tableDownAging.find("tbody");
    
    jQuery.ajax({
        url : url,
        dataType : "JSON",
        method : "GET",
        success : function(response){
    
        	var _agingData = response;
            _agingrow.html("");

            jQuery.each( _agingData, function(i,v){
                var row = "<tr>"+
                            "<td class='text-align-center'>"+v.region+"</td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='lt2' data-region='"+v.region+"'><span class='badge'>"+v.oneHourToFour+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='2to4' data-region='"+v.region+"'><span class='badge'>"+v.fourTo24Hour+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='4to24' data-region='"+v.region+"'><span class='badge bg-warning'>"+v.oneToThreeDay+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='24to48' data-region='"+v.region+"'><span class='badge bg-danger'>"+v.threeToSevendDay+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='48to168' data-region='"+v.region+"'><span class='badge bg-danger'>"+v.moreThanSevenDay+"</span></a></td>"+
                        "</tr>"
                _agingrow.append( row );
            });
        }
    })
}

Dashboard.prototype._loadNEDownStatusHourly = function(){

	var url = this._baseurl+"/nedownstatushourly";
    var self = this;
	
    var _statusHourlyRow = self.tableStatusHourly.find("tbody");
    
    jQuery.ajax({
        url : url,
        dataType : "JSON",
        method : "GET",
        success : function(response){
        	
            var _statusData = response;

            _statusHourlyRow.html("");
            
            jQuery.each( _statusData, function(i,v){
                var row = "<tr>"+
                            "<td class='text-align-center'>"+v.region+"</td>"+
                            "<td class='text-align-center'>"+v.timeBefore+" to "+v.time+"</td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='2to4'><span class='badge bg-warning'>"+v.downNew+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='4to24' ><span class='badge bg-danger'>"+v.downOpen+"</span></a></td>"+
                            "<td class='text-align-center'><a href='#' class='m-aging' data-range='24to48'><span class='badge bg-success'>"+v.downClosed+"</span></a></td>"+
                        "</tr>"
                _statusHourlyRow.append( row );
            });

        }
    })
	
}

Dashboard.prototype._loadNeDownStatusDaily = function(){
    var url = this._baseurl+"/nedownstatusdaily";
    var self = this;

    var _statusDailyRow = self.tableStatusDaily.find("tbody");


    jQuery.ajax({
        url : url,
        dataType : "JSON",
        method : "GET",
        success : function(response){
            var _statusDataDaily = response;

            _statusDailyRow.html("");

            jQuery.each( _statusDataDaily, function(i,v){
            	var row = "<tr>"+
				                "<td class='text-align-center'>"+v.region+"</td>"+
				                "<td class='text-align-center'>"+v.timeBefore+" to "+v.time+"</td>"+
				                "<td class='text-align-center'><a href='#' class='m-aging' data-range='2to4'><span class='badge bg-warning'>"+v.downNew+"</span></a></td>"+
				                "<td class='text-align-center'><a href='#' class='m-aging' data-range='4to24' ><span class='badge bg-danger'>"+v.downOpen+"</span></a></td>"+
				                "<td class='text-align-center'><a href='#' class='m-aging' data-range='24to48'><span class='badge bg-success'>"+v.downClosed+"</span></a></td>"+
				            "</tr>"
                
                _statusDailyRow.append( row );
            });
        }
    })
}

Dashboard.prototype.__scheduled = function(){
    var interval = 900000;
    setInterval( interval, this.__loadAgingAndNeDown );
}

Dashboard.prototype.__initLiveTile = function(){

    $(".live-tile").css('height', function(){
        return $(this).data('height')
    }).liveTile();

    $(document).one('pjax:beforeReplace', function(){
        $('.live-tile').liveTile("destroy", true).each(function(){
            var data = $(this).data("LiveTile");
            if (typeof (data) === "undefined")
                return;
            clearTimeout(data.eventTimeout);
            clearTimeout(data.flCompleteTimeout);
            clearTimeout(data.completeTimeout);
        });
    });

}

$(function(){
	var pageLoad = function(){
	    var dashboard = new Dashboard("api/dashboard");
	    $("body").on("click",".m-nedown",function(event){
	        event.preventDefault();
	        $("#nedown-aging-modal").modal("show");

	        var region = $(this).data('region');
	        var ne = $(this).data("ne");

	        dashboard.__listByNE( ne , region );

	    });
	};
	
	pageLoad();
    SingApp.onPageLoad(pageLoad);
});