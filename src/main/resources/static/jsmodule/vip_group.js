/**
 * @Author Frans Filasta Pratama
 * @Email  fransfilastap@live.com
 */

	var api = "vipgroupstats";
	var selectedRoute = "all";
	var map;
	var markers = [];
	var normalFeatureGroup = new L.FeatureGroup();
	var minorMajorFeatureGroup = new L.FeatureGroup();
	var criticalFeatureGroup = new L.FeatureGroup();
	var xhr;
	var oms;
	var globalInfoWindow;

	var down = 0;
	var alarm = 0;
	var normal = 0;

	var normalSites = [], normalSiteMarkers = [];
	var alarmSites  = [], alarmSiteMarkers = [];
	var downSites   = [], downSiteMarkers = [];	

	var exportTarget = "";

	var swtch_down,swtch_alarm,swtch_normal;
	var elem_down = document.querySelector('#down_only')
		,elem_alarm = document.querySelector('#alarm_only')
		,elem_normal = document.querySelector('#normal_only');

	function initialize(){

		swtch_down = new Switchery(elem_down, { size: 'small' ,color : '#2ecc71'});
		swtch_alarm = new Switchery(elem_alarm, { size: 'small' ,color : '#2ecc71'});
		swtch_normal = new Switchery(elem_normal, { size: 'small' ,color : '#2ecc71'});

		$("select#route").change(function(){
			if( $(this).val() != "" ){
				selectedRoute = $(this).val();
				//oms.clearMarkers();
				generateResult();
			}else{
				alert('Please select specific route!');
			}

		});

		$("#down_only").change(function(evt){
			if( $("#down_only").prop("checked") ){
				redrawOverlaysByArray( downSiteMarkers, criticalFeatureGroup );
			}
			else{
				criticalFeatureGroup.clearLayers();
			}
		});

		$("#alarm_only").change(function(evt){
			if( $("#alarm_only").prop("checked") ){
				redrawOverlaysByArray( alarmSiteMarkers, minorMajorFeatureGroup );
			}
			else{
				minorMajorFeatureGroup.clearLayers();
			}
		});

		$("#normal_only").change(function(evt){
			if( $("#normal_only").prop("checked") ){
				redrawOverlaysByArray( normalSiteMarkers, normalFeatureGroup );
			}
			else{
				normalFeatureGroup.clearLayers();
			}
		});
		
	    $("#gmap-zoom-in").on('click', function() {
	        map.setZoom( map.getZoom() + 1 );;
	    });
	    $("#gmap-zoom-out").on('click', function() {
	        map.setZoom( map.getZoom() -1 );
	    });

	 	$("#site_found_button,#site_normal_button,#site_alarm_button,#site_down_button").click(function(e){
			e.preventDefault();
			$($(this).data('target')).modal('show');
		});


		map = L.map('gmap',{ zoomControl:false }).setView([-6.2234585, 106.84260930000005],13);

		L.tileLayer('http://10.17.17.201/osm_tiles/{z}/{x}/{y}.png', {
		    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
		    maxZoom: 18
		}).addTo(map);			

		map.addLayer( normalFeatureGroup );
		map.addLayer( criticalFeatureGroup );
		map.addLayer( minorMajorFeatureGroup );


		oms = new OverlappingMarkerSpiderfier(map,{markersWontMove: true, markersWontHide: true,keepSpiderfied:true});

		var popupOptions = { maxWidth : '500', maxHeight : "300", className : 'geol-popup' };
		var popup = L.popup(popupOptions);

		oms.addListener('click', function(marker) {
		  		
		  		var contentString = "<i class='fa fa-spin fa-lg' style='color: #FFA46B;' title='Loading...'></i> Loading...";

				popup.setContent( contentString );
				popup.setLatLng( marker.getLatLng() );
				map.openPopup(popup);

			  	if( marker.isShouldAjax ){
			  		$.ajax({
				        url : "<?php echo site_url('gis_alarm/alarmdetail') ?>",
				        dataType : 'text',
				        method : 'POST',
				        data : { 'site_id' : marker.SITEID },
				        success : function( alarm_detail ){
				        	map.closePopup(popup);
				        	var popup2 = L.popup(popupOptions);
				        	popup2.setContent( alarm_detail );
				        	popup2.setLatLng( marker.getLatLng() );
				        	map.openPopup(popup2);
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
				        data : { 'site_id' : marker.SITEID },
				        success : function( alarm_detail ){
				        	map.closePopup(popup);
				        	var popup2 = L.popup(popupOptions);
				        	popup2.setContent( alarm_detail );
				        	popup2.setLatLng( marker.getLatLng() );
				        	map.openPopup(popup2);
				        },
				        fail : function(){
				        	alert('check error');
				        }
				    });
			  	}
		  		

			});

			oms.addListener('spiderfy', function(markers) {
			  map.closePopup();
			});


	}

        $(document).on('click','.site_detail',function( event ){
            event.preventDefault();
            showLoading();
            var mkId = $(this).data('id');
            $.ajax({
					url: "<?php echo site_url('gis_alarm/alarmdetail') ?>",
					method : "POST",
                    data : {
				        	"site_id" : mkId
				        },
                    success : function( response ){
                        $("#alarm_detailx").html( response );
                        $("#site_alarm_detail").modal("show");
                    }
			    })
			    .fail(function(jqXHR, textStatus, errorThrown) {
						        alert(errorThrown);
            });
			hideLoading();
        });	

	function generateResult(){
		showLoading();
					
		if(xhr!=""){
			abortAjax(xhr);
			clearOverlays();
		}
		xhr = $.ajax({			
			type: "GET",
			url: api+"?group="+selectedRoute,
			dataType : "JSON",
			beforeSend: function()
			{

			},
			success: function(msg)
			{
				hideLoading();
				var nearby = msg;
				markVIPSite( nearby );
				$("#site_found_count").html( nearby.length );
			}
		});	
	}
     function resetSummary(){
			
		$("#down_count").html(downSites.length);
		$("#alarm_count").html(alarmSites.length);
		$("#normal_count").html(normalSites.length);
     }


	function markVIPSite( sites ){

		clearOverlays();
		normalSites = [];
		alarmSites  = [];
		downSites   = [];
		
		exportTarget = "";
		
		map.setZoom(14);

		resetSummary();
			if( sites.length > 0 ){
				var last = "";
				$.each(sites,function(index,site) {
					exportTarget += site.SITEID+"-";
					var markerIcon;
					var severity = site.severity;
					if( severity == null || severity == undefined ){
						normalSites.push(site);
						//normal++;
						if( site.type != null  ){
							if( site.type.siteType == "HUB" )
								markerIcon = "images/map/HUB_NORMAL.png";
							else if( site.type.siteType == "HUT" )
								markerIcon = "images/map/HUT_NORMAL.png";
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
					else if( severity == "CRITICAL" || severity == "MAJOR" || severity == "MINOR" ){
						alarmSites.push(site);
						//alarm++;
						if( site.type != null  ){
							if( site.type.siteType == "HUB" )
								markerIcon = "images/map/HUB_NORMAL.png";
							else if( site.type.siteType == "HUT" )
								markerIcon = "images/map/HUT_NORMAL.png";
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
					else if( severity == "DOWN" ){
						downSites.push(site);
						alarmSites.push(site);
						//down++;
						if( site.type != null  ){
							if( site.type.siteType == "HUB" )
								markerIcon = "images/map/HUB_NORMAL.png";
							else if( site.type.siteType == "HUT" )
								markerIcon = "images/map/HUT_NORMAL.png";
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

					if( !isNaN( parseFloat( site.latitude ) ) || !isNaN( parseFloat( site.latitude ) ) ){
						var lat = parseFloat(site.latitude );
						var lng = parseFloat(site.longitude);

						var marker = L.marker([ lat , lng ],{ icon : leafletIcon });

						if( site.severity != null || site.severity != undefined ) marker.isShouldAjax = true;
						else marker.isShouldAjax = false;

						marker.SITEID = site.siteId;
						marker.siteInfo = null;

						if( site.severity == "DOWN" ) downSiteMarkers.push( marker );
						else if( site.severity != "DOWN" || site.severity != null  || site.severity != undefined ) alarmSiteMarkers.push( marker );
						else normalSiteMarkers.push( marker );



						if( site.severity == "DOWN" ){

							if( $("#down_only").prop("checked") == false ){
								marker.addTo( map );
							}

							criticalFeatureGroup.addLayer(marker);
						}
						else if( site.severity != "DOWN" || site.severity != null || site.severity != undefined  ){
							if( $("#alarm_only").prop("checked") == false ){
								marker.addTo( map );
							}		
							minorMajorFeatureGroup.addLayer( marker );				
						}
						else{
							if( $("#normal_only").prop("checked") == false ){
								marker.addTo( map );
							}		
							normalFeatureGroup.addLayer(marker);					
						}

						oms.addMarker(marker);

						last = site;

					}
					
					

				});

				resetSummary();

				drawSpecial( alarmSites, '#table_alarm_site' );
				drawSpecial( downSites, '#table_down_site' );
				drawSpecial( normalSites, '#table_normal_site' );

				//map.setCenter( new google.maps.LatLng( last.LATITUDE , last.LONGITUDE ) );
				
				var href = "http://10.23.32.109:8080/pm5-exporter/gis/export/vip/"+encodeURI(selectedRoute);
				$("#exportResult").attr("href",href);
			}		

			map.fitBounds( normalFeatureGroup.getBounds() );

	}


		$(document).on("click",".siteavail",function(e){
			e.preventDefault();
			
			$(this).closest(".fa-spinner").show();

			var _id = $(this).data('id');
			var _pattern  = /G$/;
			var _ty = "2g";
						
			if( _pattern.test( _id ) ){
				_ty = "3g";
			}

			loadAvailabilityData( _id, _ty );
			$(this).closest(".fa-spinner").show();

		});

	$(document).on("click",".resetsite",function(e){
		e.preventDefault();

		$("#terminalContent").html("<?php echo $this->session->userdata(md5('name')) ?>@geol:~$");
		$("#terminalContent").append('Loading script...');
		$("#terminal").window('open');

		var _id = $(this).data('id');
		$.ajax({
			url : $(this).attr('href'),
			method : "POST",
			dataType : "text",
			data : { "site_id" : _id },
			success : function( response )
			{
				$("#terminalContent").append(response);
			}
		});
	
	

	});		

		function loadAvailabilityData( _site, _type )
		{

	        var _data = {
	              'poc'  : _site,
	              'type' : _type,
	            };
	        var _url = "<?php echo site_url('availability/sitedetail') ?>";

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

				        		var series = chartData.series_data[ this.series.name ];

				        		return "CELL : <b>"+this.series.name+"</b><br>TIME : <b>"+unixTimeToHumanTime( this.x )+"</b><br>Availability : <b>"+this.y+"%</b>";
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

			        $.each(chartData.series_data,function(index,value){
						var srs = {"name":index,"data":[]};
						var dt = chartData.series_data[index];
			          	$.each( dt, function(i,v){
			          		var sd = [ v.time , v.avail ];
			          		srs.data.push( sd );
			          		console.log( sd );
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

		function unixTimeToHumanTime( unixtime )
 		{
 			var date = new Date( unixtime );
 			var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
 			var strdt = date.getUTCDate()+"-"+monthNames[ date.getMonth() ]+"-"+date.getUTCFullYear()+"  "+(date.getUTCHours())+":"+date.getMinutes();

 			return strdt;
 		}	

	function drawTable( nearby_sites ){
		//$("#result").html( nearby_sites );
		$("#nearby_site_found").html(nearby_sites);
        $("#nearby_table").dataTable();
	}

	function drawSpecial( data, table_id ){

		$(table_id+" > tbody").html('');
		
		$.each(data, function(index,value){

			var row = '<tr>'+
						'<td>'+(index+1)+'</td>'+
						'<td>'+value.SITEID+'</td>'+
						'<td>'+value.SITENAME+'</td>'+
						'<td>'+value.TOWER_PROVIDER+'</td>'+
						'<td>'+value.TTNO+'</td>'+
                        '<td>'+( value.ISDEAD == null ? 'NO' : '<a href="#" data-id="'+value.SITEID+'" class="site_detail">YES</a>' )+'</td>'+
					'</tr>';

			$(table_id+" > tbody").append( row );

		});

		$(table_id).dataTable();

	}

	function abortAjax(xhr) {
		if(xhr && xhr.readystate != 4){
			xhr.abort();
		}
	}

	function clearOverlays() {

		normalFeatureGroup.clearLayers();
		minorMajorFeatureGroup.clearLayers();
		criticalFeatureGroup.clearLayers();

  		downSiteMarkers = [];
  		normalSiteMarkers = [];
  		alarmSiteMarkers = [];
	}

	/*

	function clearOverlaysByArray( arrMarkers ){
	 	for (var i = 0; i < arrMarkers.length; i++ ) {
	    	arrMarkers[i].setMap(null);
	  	}
	}

	*/

	function redrawOverlaysByArray( Markers, FeatureGroup ){
	 	for (var i = 0; i < Markers.length; i++ ) {
	    	FeatureGroup.addLayer(Markers[i]);
	  	}		
	}



    function showLoading(){
        $("#content > .loading").stop().removeClass("bounceOut").toggleClass("bounceIn").show();
    }
        
    function hideLoading(){
        $("#content > .loading").stop().removeClass("bounceIn").toggleClass("bounceOut");
    }

    

	$(document).ready(function(){
		initialize();
		$("#content").append('<div class="loading"><img src="img/rolling.gif"/></div>');
	});