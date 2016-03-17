/**
 *  Jalur lebaran
 */

var Masscheck = function( mapelem, lat,lng ){
	var position = L.latLng( lat , lng );
	this.$elem = $( mapelem );
	this.__init(  position );
}

Masscheck.prototype.__init = function(latLng){

	var self = this;
	
	var initMap = function(){
		self.map = new L.Map(self.$elem[0],{ zoomControl:false }).setView( latLng , 13);
		var osmUrl='http://10.17.17.201/osm_tiles/{z}/{x}/{y}.png';
		var mapTiles = new L.TileLayer(osmUrl, {
	        attribution: 'Map data &copy; '
	        + '<a href="http://openstreetmap.org">OpenStreetMap</a> contributors, '
	        + '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	        maxZoom: 18
	    });	

	    self.map.addLayer( mapTiles );
	};

	var addSummary = function(){

		self.normalSites = []; 
		self.normalSiteMarkers = [];
		self.alarmSites  = []; 
		self.alarmSiteMarkers = [];
		self.downSites   = []; 
		self.downSiteMarkers = [];	
		self.down = 0;
		self.alarm = 0;
		self.normal = 0;

		self.normalFeatureGroup = new L.FeatureGroup();
		self.minorMajorFeatureGroup = new L.FeatureGroup();
		self.criticalFeatureGroup = new L.FeatureGroup();
		self.pathMarkers = new L.FeatureGroup();

		$("#site_found_button,#site_normal_button,#site_alarm_button,#site_down_button").click(function(e){
	    	e.preventDefault();
	    	$($(this).data('target')).modal('show');
	    });	

		self.swtch_down 	= new Switchery(document.querySelector('#down_only'), { size: 'small' ,color : '#2ecc71'});
		self.swtch_alarm 	= new Switchery(document.querySelector('#alarm_only'), { size: 'small' ,color : '#2ecc71'});
		self.swtch_normal 	= new Switchery(document.querySelector('#normal_only'), { size: 'small' ,color : '#2ecc71'});

		self.swtchdown 		= $("#down_only");
		self.swtchalarm 	= $("#alarm_only");
		self.swtchnormal 	= $("#normal_only");

		self.swtchdown.change(function(event){
			if( self.swtchdown.prop( 'checked' ) ){
				self.redrawMarkerByArray( self.downSiteMarkers, self.criticalFeatureGroup );
			}
			else{
				self.criticalFeatureGroup.clearLayers();
			}
		});

		self.swtchalarm.change(function(event){
			if( self.swtchalarm.prop( 'checked' ) ){
				self.redrawMarkerByArray( self.alarmSiteMarkers, self.minorMajorFeatureGroup );
			}
			else{
				self.minorMajorFeatureGroup.clearLayers();
			}
		});

		self.swtchnormal.change(function(event){
			if( self.swtchnormal.prop( 'checked' ) ){
				self.redrawMarkerByArray( self.normalSiteMarkers, self.normalFeatureGroup );
			}
			else{
				self.normalFeatureGroup.clearLayers();
			}
		});		
	}

	var addOMS = function(){
		
		
   	 	self.oms = new OverlappingMarkerSpiderfier(self.map,{markersWontMove: true, markersWontHide: true,keepSpiderfied:true});

   	 	self.map.addLayer( self.normalFeatureGroup );
		self.map.addLayer( self.criticalFeatureGroup );
		self.map.addLayer( self.minorMajorFeatureGroup );

		var popupOptions = { maxWidth : '500', maxHeight : "300", className : 'geol-popup' };
		var popup = L.popup(popupOptions);
		var me = this;
		
		self.oms.addListener('click', function(marker) {
		  		
  			var contentString = "<i class='fa fa-spin fa-lg' style='color: #FFA46B;' title='Loading...'></i> Loading...";

			popup.setContent( contentString );
			popup.setLatLng( marker.getLatLng() );
			self.map.openPopup(popup);

		  	if( marker.isShouldAjax ){
		  		$.ajax({
			        url : "getSiteAlarm2",
			        dataType : 'JSON',
			        method : 'GET',
			        data : { 'site' : marker.siteid },
			        success : function( alarm_detail ){
			        	self.map.closePopup(popup);
			        	var popup2 = L.popup(popupOptions);
			        	popup2.setContent( self.renderPopupAlarm( alarm_detail.site, alarm_detail.alarm) );
			        	popup2.setLatLng( marker.getLatLng() );
			        	self.map.openPopup(popup2);
			        },
			        fail : function(){
			        	alert('check error');
			        }
			    });

		  	}else{
		  		$.ajax({
			        url : "getSiteInformation",
			        dataType : 'JSON',
			        method : 'POST',
			        data : { 'site' : marker.siteid },
			        success : function( alarm_detail ){
			        	self.map.closePopup(popup);
			        	var popup2 = L.popup(popupOptions);
			        	popup2.setContent(  self.renderPopup(alarm_detail) );
			        	popup2.setLatLng( marker.getLatLng() );
			        	self.map.openPopup(popup2);
			        },
			        fail : function(){
			        	alert('check error');
			        }
			    });
		  	}
	  		

		});

		self.oms.addListener('spiderfy', function(markers) {
		  self.map.closePopup();
		});			
	}

	var selectionListener = function(){
	    $("#routetype").change(function(){
	    	self.route_type = $(this).val();
	    	self.ajaxRouteName();
	    });

	    $("select#region_name").change(function(){
	    	self.region_name = $(this).val();
	    	if( $("select#routetype").val() != "" ){
	    		self.ajaxRouteName();
	    	}
	    });

	    /*$(document).on('click',".goto",function(evt){
	    	evt.preventDefault();
	    	var lt = parseFloat($(this).data('latitude'));
	    	var ln = parseFloat($(this).data('longitude'));
	    	//self.map.setCenter( new google.maps.LatLng( lt , ln ) );
	    });
		*/

	    $("select#region_name option").click(function(){
	    	if( $("select#routetype").val() != "" ){
	    		self.ajaxRouteName();
	    	}
	    });

	    $(document).on('change','select#routename',function(){
	    	self.route_name = $(this).val();
	    	self.generateResult();
	    });

	    $(document).on('click','select#routename option',function(){
	    	self.route_name = $(this).val();
	    	self.generateResult();
	    });

	   	$("#nettype").change(function(){
	    	self.network_type = $(this).val();
	    	self.generateResult();
	    });

	    this.network_type = "all";
	}


	initMap();
	addSummary();
	addOMS();
	selectionListener();
	this.addAvailabilityListener();


}

Masscheck.prototype.redrawMarkerByArray = function( Markers, featureGroup ){
 	for (var i = 0; i < Markers.length; i++ ) {
    	featureGroup.addLayer(Markers[i]);
  	}		
}


//development
Masscheck.prototype.renderPopup = function(site){
	var component = "<div class='alarm'>"+
	"<div>Site name : <b class='sitename'>"+site.siteName+"</b></div>"+
	"<div>Site ID : <b class='siteid'>"+site.siteId+"</b></div>"+
	"<div>Tower Provider : <b class='towerprovider'>"+site.towerProvider+"</b></div>"+
    "<div>Node : <b class='node'>"+site.node+"</b></div>"+
	"<div>Zone : <b class='zone'>"+site.zone+"</b></div>"+
    "<div class='form-action'>"+
        "<a class='btn btn-success btn-sm siteavail' data-id='"+site.siteId+"' href='#'><i class='fa fa-signal'>  </i> Availability</a> "+
	    "<!-- "+
        "<a class='btn btn-danger btn-sm resetsite' href='' data-id='"+site.siteId+"'><i class='fa fa-wrench'></i> FTR </a>"+
        "-->"+
    "</div>";
	
	return component;
}

Masscheck.prototype.addAvailabilityListener = function(){
	var me = this;
	$(document).on("click",".siteavail",function(e){
		e.preventDefault();
		
		$(this).closest(".fa-spinner").show();

		var _id = $(this).data('id');
		var _pattern  = /G$/;
		var _pattern4g = /E$/;
		var _ty = "2g";
					
		if( _pattern.test( _id ) ){
			_ty = "3g";
		}
		
		if( _pattern4g.test( _id ) ){
			_ty = "4G";
		}

		me.getSiteAvailability( _id, _ty );
		$(this).closest(".fa-spinner").show();

	});
}


Masscheck.prototype.unixTimeToHumanTime = function(unixtime){
	var date = new Date( unixtime );
	var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	var strdt = date.getUTCDate()+"-"+monthNames[ date.getMonth() ]+"-"+date.getUTCFullYear()+"  "+(date.getUTCHours())+":"+date.getMinutes();

	return strdt;		
}


Masscheck.prototype.getSiteAvailability = function( _site , _type ){
	var me = this;
    var _data = {
          'site'  : _site,
          'version' : _type,
        };
    var _url = "sites-availability";

    jQuery.ajax({
    	url : _url,
    	data : _data,
    	dataType : "JSON",
    	method : "GET",
    	success : function( chartData ){

	 		var chartOptions = {
		        chart: {
		            type: 'spline',
		            height : 500,
		            width:870
		        },
		        title: {
		            text: '<b>SITE AVAILABILITY</b>'
		        },
		        subtitle: {
		            text: "LAST 2 DAYS"
		        },
		        xAxis: {
		            type: 'datetime',
		            dateTimeLabelFormats: { // don't display the dummy year
		                month: '%e. %b',
		                year: '%b'
		            },
		            title: {
		                text: '<b>Date</b>'
		            },
		            ordinal: false
		        },
		        yAxis: {
		            title: {
		                text: 'Availability'
		            },
				    labels: {format: '{value}%'},
		            min : 0,
		            max : 100
		        },
		        tooltip: {
		        	formatter : function()
		        	{	

		        		var series = chartData[ this.series.name ];

		        		return "CELL : <b>"+this.series.name+"</b><br>TIME : <b>"+me.unixTimeToHumanTime( this.x )+"</b><br>Availability : <b>"+this.y+"%</b>";
		        	}
		        },
		        plotOptions: {
		            spline: {
		                marker: {
		                    radius: 4,
		                    lineColor: '#666666',
		                    lineWidth: 1
		                }
		            },
		            series: {
	                    cursor: 'pointer',
	                    point: {
	                        events: {
	                            click: function (e) {
									alert( this.series.name );                         
	                            }
	                        }
	                    }
	                }
		        },
		        credits: {enabled: false },
		        series: []
		    };

	        $.each(chartData,function(index,value){
				var srs = {"name":index,"data":[]};
				var dt = chartData[index];
	          	$.each( dt, function(i,v){
	          		var sd = [ v.resultTime , v.availability ];
	          		srs.data.push( sd );
	          	});
	          	chartOptions.series.push( srs );
	        });	    


		    $("#chartCanvas").highcharts( chartOptions );
	        $("#availabilitymodal").modal("show");

    	},
    	fail : function(){
    		alert( "Failed to load data" );
    	}
    })
}


Masscheck.prototype.renderPopupAlarm = function(site,alarm){
	
	var rows = "";
		
	
	$.each(alarm,function(i,e){
		rows +="<tr>"+
			"<td style='width:100px;'>"+e.firstOccurrence+"</td>"+
			"<td style='width:130px;'>"+e.summary+"</td>"+
			"<td style='width:50px;'>"+e.severity+"</td>"+
	        "<td style='width:75px'>"+e.ttno+"</td>"+
	        "<td style='width:75px'>"+e.woId+"</td>"+
	        "<td style='width:75px'>"+e.pic+"</td>"+
	        "<td style='width:75px'>"+e.woStatus+"</td>"+
		"</tr>";
	});
	
	
	var component = "<div class='alarm'>"+
		"<div>Site name : <b class='sitename'>"+site.siteName+"</b></div>"+
		"<div>Site ID : <b class='siteid'>"+site.siteId+"</b></div>"+
		"<div>Tower Provider : <b class='towerprovider'>"+site.towerProvider+"</b></div>"+
	    "<div>Node : <b class='node'>"+site.node+"</b></div>"+
		"<div>Zone : <b class='zone'>"+site.zone+"</b></div>"+
	    "<div class='form-action'>"+
	        "<a class='btn btn-success btn-sm siteavail' data-id='"+site.siteId+"' href='#'><i class='fa fa-signal'>  </i> Availability</a> "+
		    "<!-- "+
	        "<a class='btn btn-danger btn-sm resetsite' href='' data-id='"+site.siteId+"'><i class='fa fa-wrench'></i> FTR </a>"+
	        "-->"+
	    "</div>"+
	    "<div><b>Alarm : </b></div>"+
		"<table class='table table-bordered'>"+
	        "<thead>"+
	          "<tr>"+
	            "<th style='width:100px;'>First Occurrence</th>"+
	            "<th style='width:130px;'>Alarm</th>"+
	            "<th style='width:50px;'>Severity</th>"+
	            "<th style='width:75px'>TTNo</th>"+
	            "<th style='width:75px'>WO ID</th>"+
	            "<th style='width:75px'>PIC</th>"+
	            "<th style='width:75px'>WO Status</th>"+
	          "</tr>"+
	        "</thead>"+
	        "<tbody>"+rows+"</tbody>"+
	      "</table>"+
	"</div>";
	
	return component;
}


Masscheck.prototype.ajaxRouteName = function(){
	
	this.__hideLoading();

	var _url = "fetch-lebaran-route";

    $.ajax({
    	url : _url,
    	method : "GET",
    	data : { region : this.region_name, type: this.route_type },
    	dataType : "JSON",
    	success : function(response){

    		var def = '<option value="">SELECT ROUTE</option>';
    		$("select#routename").html( def );
   	   		$.each(response,function(index,r){
    			var option = '<option value="'+r.id+'">'+r.routename+'</option>';
    			$("select#routename").append( option );
    		});
    	}
    });	
}

Masscheck.prototype.clearOverlays = function(){
	this.normalFeatureGroup.clearLayers();
	this.minorMajorFeatureGroup.clearLayers();
	this.criticalFeatureGroup.clearLayers();

		this.downSiteMarkers = [];
		this.normalSiteMarkers = [];
		this.alarmSiteMarkers = [];
}

Masscheck.prototype.generateResult = function(){

	this.__showLoading();
	if( this.network_type == undefined ){
		this.network_type = "all";
	}

	this.api = "lebaran-route-status";
				
	var self = this;

	if(self.xhr!=""){
		this.abortAjax(self.xhr);
		this.clearOverlays();
	}

	self.xhr = $.ajax({			
		type: "GET",
		url: self.api,
		data : { route : self.route_name, version : self.network_type },
		dataType : "JSON",
		beforeSend: function()
		{

		},
		success: function(msg)
		{
			self.__hideLoading();
			//var nearby = msg.sites;
			//var tables = msg.tables;
			self.__markRoute( msg );
			//self.drawTable( tables );
			$("#site_found_count").html( msg.length );
		}
	});		
}

Masscheck.prototype.__hideLoading = function(){
	$("#content > .loading").stop().removeClass("bounceIn").toggleClass("bounceOut");
}

Masscheck.prototype.__showLoading = function(){
	$("#content > .loading").stop().removeClass("bounceOut").toggleClass("bounceIn").show();
}

Masscheck.prototype.__markRoute = function( sites ){

		this.clearOverlays();
		this.down = 0;
		this.alarm = 0;
		this.normal = 0;
		this.normalSites = [];
		this.alarmSites  = [];
		this.downSites   = [];
		
		//this.map.setZoom(15);

		this.resetSummary();

		var self = this;

		if( sites.length > 0 ){
			var last = "";
			$.each(sites,function(index,site) {
				
				var markerIcon;
				var severity = site.severity;
				if( severity == null || severity == undefined ){
					self.normalSites.push(site);
					self.normal++;

					if( site.type == "HUB" ){
						markerIcon = "images/map/HUB_NORMAL.png";
					}else if( site.type == "HUT" ){
						markerIcon = "images/map/HUT_NORMAL.png";
					}
					else{
						
						if( site.technology.technology == "2G" ){
							markerIcon = "images/map/2G_NORMAL.png";
						}
						else if(site.technology.technology == "3G"){
							markerIcon = "images/map/3G_NORMAL.png";
						}else {
							markerIcon = "images/map/4G_NORMAL.png";
						}
					}
				}
				else if( severity == "MAJOR" || severity == "MINOR" || severity == "CRITICAL" ){
						self.alarmSites.push(site);
						self.alarm++;
						if( site.type == "HUB" ){
							markerIcon = "images/map/HUB_ALARM.png'";
						}else if( site.type == "HUT" ){
							markerIcon = "images/map/HUT_ALARM.png";
						}
						else{
							
							if( site.technology.technology == "2G" ){
								markerIcon = "images/map/2G_ALARM.png";
							}
							else if(site.technology.technology == "3G"){
								markerIcon = "images/map/3G_ALARM.png";
							}else {
								markerIcon = "images/map/4G_ALARM.png";
							}
						}
				}
				else if( severity == "DOWN" ){
					self.downSites.push(site);
					self.alarmSites.push(site);
					self.down++;
					self.alarm++;

					if( site.type == "HUB" ){
						markerIcon = "images/map/HUB_DOWN.png";
					}else if( site.type == "HUT" ){
						markerIcon = "images/map/HUT_DOWN.png";
					}
					else{
						
						if( site.technology.technology == "2G" ){
							markerIcon = "images/map/2G_DOWN.png";
						}
						else if(site.technology.technology == "3G"){
							markerIcon = "images/map/3G_DOWN.png";
						}else {
							markerIcon = "images/map/4G_DOWN.png";
						}
					}

				}

				var shadow = "images/map/shadow.png";

				var leafletIcon = new L.Icon( { iconUrl : markerIcon , shadowUrl : shadow  ,iconSize: [30, 44], shadowSize: [30, 48], shadowAnchor: [12, 20.3] } );

				if( !isNaN( parseFloat( site.latitude ) ) || !isNaN( parseFloat( site.longitude ) ) ){
					var lat = parseFloat(site.latitude );
					var lng = parseFloat(site.longitude);

					var marker = L.marker([ lat , lng ],{ icon : leafletIcon });
					

					if( site.severity != null ) marker.isShouldAjax = true;
					else marker.isShouldAjax = false;

					marker.siteid = site.siteId;
					marker.siteInfo = null;

					if( site.severity == "DOWN" ) self.downSiteMarkers.push( marker );
					else if( site.severity == "MAJOR" || site.severity == "MINOR" || site.severity == "CRITICAL" ) self.alarmSiteMarkers.push( marker );
					else self.normalSiteMarkers.push( marker );


					if( site.severity == "DOWN" ){

						self.criticalFeatureGroup.addLayer(marker);
					}
					else if( site.severity == "MAJOR" || site.severity == "MINOR" || site.severity == "CRITICAL" ){		
						self.minorMajorFeatureGroup.addLayer( marker );				
					}
					else{	
						self.normalFeatureGroup.addLayer(marker);					
					}

					//marker.addTo( self.map );

					self.oms.addMarker(marker);

					last = site;

					if( self.bounceSite ){
						if( self.bounceSite.toLowerCase() == site.site_Id.toLowerCase() ){

							marker.setBouncingOptions({
						        bounceHeight : 60,    // height of the bouncing
						        bounceSpeed  : 70,    // bouncing speed coefficient
						        exclusive    : true,  // if this marker bouncing all others must stop
						    }).bounce();
						}

						//

					}

				}

			});

			this.resetSummary();

			this.drawSpecial( this.alarmSites, '#table_alarm_site' );
			this.drawSpecial( this.downSites, '#table_down_site' );
			this.drawSpecial( this.normalSites, '#table_normal_site' );
		}
			//map.setCenter( new google.maps.LatLng( last.latitude , last.longitude ) );
			
			var href = "jalur-lebaran-export?route="+$("select#routename").val();
			$("#exportResult").attr("href",href);
}

Masscheck.prototype.resetSummary = function(){			
	$("#down_count").html(this.down);
	$("#alarm_count").html(this.alarm);
	$("#normal_count").html(this.normal);
}

Masscheck.prototype.drawTable = function(nearby_sites){
	$("#nearby_site_found").html(nearby_sites);
    $("#nearby_table").dataTable();
}

Masscheck.prototype.drawSpecial = function(data, table_id){
	$(table_id+" > tbody").html('');
	$.each(data, function(index,value){

		var row = '<tr>'+
					'<td>'+(index+1)+'</td>'+
					'<td>'+value.site_Id+'</td>'+
					'<td>'+value.site+'</td>'+
					'<td>'+value.tprovider+'</td>'+
					'<td>'+value.ttno+'</td>'+
                    '<td>'+( value.isDead == null ? 'NO' : '<a href="#" data-id="'+value.site_Id+'" class="site_detail">YES</a>' )+'</td>'+
				'</tr>';

		$(table_id+" > tbody").append( row );

	});
}

Masscheck.prototype.abortAjax = function(){
	if( this.xhr ){
		this.xhr.abort();
	}
}


jQuery(function($){
	var map = new Masscheck( "#gmap", -6.2234585, 106.84260930000005 );
	$("#content").append('<div class="loading"><img src="img/rolling.gif"/></div>');
});


$(function(){

	var serverCheckURL 	= "<?php echo site_url('mass-check/check') ?>";
	var massCheckMapDataGetURL = "<?php echo site_url('mass_check/get_map_data') ?>";
	var siteTextArea	= $("textarea[name=sites]");
	var oms;

	var markers = [];
	var arrMarkers = [];
	var arrInfoWindows = [];
	
	var down = 0;
	var alarm = 0;
	var normal = 0;

	var normalSites = [];
	var alarmSites  = [];
	var downSites   = [];

	var currentSites = "";

	var map;

     var url;
     var dg = $('#dg');
		dg.datagrid();    // create datagrid
		dg.datagrid('enableFilter');    // enable filter

     var dg1 = $('#dg1');
		dg1.datagrid();    // create datagrid
		dg1.datagrid('enableFilter');    // enable filter

	$("#resultexcelcheck").fadeOut();
	$("#resultexceltext").fadeOut();

	$("#resultexcelcheck").click(function(evt){
		evt.preventDefault();
		$("#w").window('open');
	});

	$("#export2").click(function(e){
		var link = "http://localhost:8080/pm5-exporter/massproblem/export/"+currentSites;
		$(this).attr("href",link);
	});

	$("#exportSummary2").click(function(e){
		var link = "http://localhost:8080/pm5-exporter/massproblem/summary/"+currentSites;
		$(this).attr("href",link);
	});

	var massCheckAjax = function(sites){
		showLoading();
		jQuery.ajax({
			url : serverCheckURL,
			method : "POST",
			dataType : "TEXT",
			data : {
				"sites" : sites
			},
			beforeSend : function(){
				return ( sites != "" );
			},
			success : function(response){
				
				var asek = JSON.parse(response);
				currentSites = asek.code;
			},
			fail : function(){
				alert(':(');
			}
		});

	};

	var massCheckAjaxByFile = function( fileCheckUrl ){

		$("#resultexcelcheck").fadeOut();
		showLoading();
		jQuery.ajax({
			url : fileCheckUrl,
			method : "GET",
			dataType : "JSON",
			success : function(response){
				dg.datagrid('reload');
				$("#resultexcelcheck").fadeIn();
				$('#w2').window('open');
			},
			fail : function(){
				alert(':(');
			}
		});

	};


	$("form#masscheckform").bind("submit",function(){
		currentSites="";
		massCheckAjax( siteTextArea.val() );
		$('#w2').window('open');
		dg1.datagrid('reload');
		hideLoading();
		return false;

	});

	$("a#excelcheck").click(function (evt){
		evt.preventDefault();
		massCheckAjaxByFile( $(this).attr('href') );
	});

	function loadMassCheckMapData(type){
		jQuery.ajax({
			url : massCheckMapDataGetURL+"/"+type,
			method : "GET",
			dataType : "JSON",
			success : function(response){
				markSites(response);
			},
			fail : function(){
				alert(':(');
			}
		});		
	}

	function clearOverlays() {
	 	for (var i = 0; i < markers.length; i++ ) {
	    	markers[i].setMap(null);
	  	}
	  	markers=[];
		arrMarkers=[];
		arrInfoWindows=[];
	}	

	function resetSummary(){
			
		/*$("#down_count").html(down);
		$("#alarm_count").html(alarm);
		$("#normal_count").html(normal);*/
     }

	function markSites( sites ){

		clearOverlays();
		down = 0;
		alarm = 0;
		normal = 0;
		normalSites = [];
		alarmSites  = [];
		downSites   = [];

		map.setZoom(14);

		resetSummary();
			if( sites.length > 0 ){
				var last = "";
				$.each(sites,function(index,site){

					var markerIcon;

					if( site.isDead != null ){
						if( site.isDead == 1 ){
							downSites.push(site);
							down++;
							if( site.hubOrHut == "HUB" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_down_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_down_map.png') ?>";
								}
							}else if( site.hubOrHut == "HUT" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_down_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_down_map.png') ?>";
								}
							}
							else{
								
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/tower_down_3g.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/tower_down_2g.png') ?>";
								}
							}
						}else{
							alarmSites.push(site);
							alarm++;
							if( site.hubOrHut == "HUB" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_alarm_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_alarm_map.png') ?>";
								}
							}else if( site.hubOrHut == "HUT" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_alarm_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_alarm_map.png') ?>";
								}
							}
							else{
								
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/icon_alarm_3g.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/icon_alarm_2g.png') ?>";
								}
							}						
						}						
					}
					else{

						normalSites.push( site );

							normal++;
							if( site.hubOrHut == "HUB" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_normal_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_normal_map.png') ?>";
								}
							}else if( site.hubOrHut == "HUT" ){
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/2g_hub_normal_map.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/3g_hub_normal_map.png') ?>";
								}
							}
							else{
								
								if( site.nettype != "2G" ){
									markerIcon = "<?php echo base_url('assets/images/map/cell_tower_3g.png'); ?>";
								}
								else{
									markerIcon = "<?php echo base_url('assets/images/map/cell_tower_2g.png') ?>";
								}
							}
					}

					var marker = new google.maps.Marker({
						position : new google.maps.LatLng( site.latitude , site.longitude ),
						map : map,
						icon : markerIcon,
						animation : google.maps.Animation.DROP,
						title : " Site ID: "+site.site_id+" , Site Name : "+site.site 
					});

					arrMarkers[index] = marker;
					markers.push( marker );

					if( site.isDead != null ) marker.isShouldAjax = true;
					else marker.isShouldAjax = false;

					marker.siteid = site.site_id;
					marker.siteInfo = null;

					oms.addMarker(marker);

					last = site;

				});

				resetSummary();

				map.setCenter( new google.maps.LatLng( last.latitude , last.longitude ) );

			}		


	}


	function showLoading(){
        $(".loading").stop().removeClass("bounceOut").toggleClass("bounceIn").show();
    }
        
    function hideLoading(){
        $(".loading").stop().removeClass("bounceIn").toggleClass("bounceOut");
    }


	$(document).ready(function(){

		var mapDiv = document.getElementById("google_map");
		map = new google.maps.Map( mapDiv, {
				center: new google.maps.LatLng(-6.2234585, 106.84260930000005),
				zoom: 10,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				mapTypeControlOptions: {
				      style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
				      position: google.maps.ControlPosition.LEFT_CENTER,
				      mapTypeIds: [
				        google.maps.MapTypeId.ROADMAP,
				        google.maps.MapTypeId.TERRAIN
				      ]
				    },
				minZoom:10,
				maxZoom : 18,
				scrollwheel: true,
				panControl:false,
				mapTypeControl: true,
				zoomControl: false,
				streetViewControl: false,
		});		
		oms = new OverlappingMarkerSpiderfier(map,
        {markersWontMove: true, markersWontHide: true,keepSpiderfied:true});

        oms.addListener('click', function(marker, event) {
		  	
		  		var contentString = "<i class='fa fa-spin fa-lg' style='color: #FFA46B;' title='Loading...'></i> Loading...";
			    var infoWindow = new google.maps.InfoWindow({
			  			content : contentString
			  	});
			  	infoWindow.open(map,marker);
			  	if( marker.isShouldAjax ){
			  		$.ajax({
				        url : "<?php echo site_url('gis_alarm/alarmdetail') ?>",
				        dataType : 'text',
				        method : 'POST',
				        data : { 'site_id' : marker.siteid },
				        success : function( alarm_detail ){
				        	infoWindow.setContent( alarm_detail );
				        },
				        fail : function(){
				        	alert('check error');
				        }
				    });

			  	}else{
			  		$.ajax({
				        url : "<?php echo site_url('gis_alarm/sitedetail_ajax') ?>",
				        dataType : 'text',
				        method : 'POST',
				        data : { 'site_id' : marker.siteid },
				        success : function( alarm_detail ){
				        	infoWindow.setContent( alarm_detail );
				        },
				        fail : function(){
				        	alert('check error');
				        }
				    });
			  	}

		  		infoWindow.open(map,marker);

		});
	});


});