/**
 * @author Frans Filasta Pratama
 * @email fransfilastap@live.com 
 */

L.Marker.prototype.radiusWidgetDrag = function () {
  
  var iconMargin, shadowMargin;
  
  this.on('dragstart', function () {
    if (!iconMargin) {
      iconMargin = parseInt(L.DomUtil.getStyle(this._icon, 'marginTop'));
      shadowMargin = parseInt(L.DomUtil.getStyle(this._shadow, 'marginLeft'));
    }
  
    this._icon.style.marginTop = (iconMargin - 15)  + 'px';
    this._shadow.style.marginLeft = (shadowMargin + 8) + 'px';
  });
  
  return this.on('dragend', function () {
    this._icon.style.marginTop = iconMargin + 'px';
    this._shadow.style.marginLeft = shadowMargin + 'px';
  });
};

// to keep popup keep open when marker clicked (especially on radius sizer marker)
L.Map = L.Map.extend({
 	openPopup: function (popup, latlng, options) { 
        if (!(popup instanceof L.Popup)) {
        var content = popup;
        
        popup = new L.Popup(options).setContent(content);
        }
        
        if (latlng) {
        popup.setLatLng(latlng);
        }
        
        if (this.hasLayer(popup)) {
        return this;
        }
        
        //this.closePopup();
        this._popup = popup;
        return this.addLayer(popup);        
    }
});

Number.prototype.toRad = function(){
	return this * Math.PI / 180;
};

Number.prototype.toDeg = function(){
	return this * 180/ Math.PI;
};

L.LatLng.prototype.moveTowards = function( point , distance ){
	var lat1 = this.lat.toRad();
	var lon1 = this.lng.toRad();
	var lat2 = point.lat.toRad();
	var lon2 = point.lng.toRad();
	var dLon = ( point.lng - this.lng ).toRad();

	//find beadring from this point to the next
	var bearing = Math.atan2( Math.sin( dLon ) * Math.cos( lat2 ),
					Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( dLon ) );

	var point2PointDistance = distance / 6378137; // Earth's radius in meter

	//Calculate the destination point, given the source and bearing
	lat2 = Math.asin( Math.sin( lat1 ) * Math.cos( point2PointDistance ) + 
				Math.cos( lat1 ) * Math.sin( point2PointDistance ) *
				Math.cos( bearing ) );

	lon2 = lon1 + Math.atan2( Math.sin( bearing ) * Math.sin( point2PointDistance ) * Math.cos( lat1 ) , 
				Math.cos( point2PointDistance ) - Math.sin( lat1 ) * Math.sin( lat2 ) );

	if( isNaN( lat2 ) || isNaN( lon2 ) ) return null;

	return new L.LatLng( lat2.toDeg() , lon2.toDeg() );
}

L.LatLng.prototype.meterTo = function( a ){
	if( a == undefined ) return;
    var e = Math, ra = e.PI/180; 
    var b = this.lat * ra, c = a.lat * ra, d = b - c; 
    var g = this.lng * ra - a.lng * ra; 
    var f = 2 * e.asin(e.sqrt(e.pow(e.sin(d/2), 2) + e.cos(b) * e.cos (c) * e.pow(e.sin(g/2), 2))); 
    return f * 6378137; 		
}

L.Polyline.prototype.getLength = function(n){
    var a = this.getLatLngs(n);
    var len = a.length;
    var dist = 0; 
    for (var i=0; i < len-1; i++) { 
       dist += a[i].meterTo(a[i+1]); 
    }
    return dist; 
}

var Map = function( id , lat , lng  ){

	this.$elem = $(id);
	this.latLng = L.latLng( lat , lng );
	this.center = this.latLng;
	this.init( this.latLng );
	this.backend = 'statWithinRadius';
	this.mode = "radius"; // mode : 'radius' or 'path'
}

Map.prototype.init = function(latLng){
	this.setLatLng( latLng );
	this.map = new L.Map(this.$elem[0],{ zoomControl:false }).setView( latLng , 13);
	var osmUrl='http://10.17.17.201/osm_tiles/{z}/{x}/{y}.png';
	var mapTiles = new L.TileLayer(osmUrl, {
        attribution: 'Map data &copy; '
        + '<a href="http://openstreetmap.org">OpenStreetMap</a> contributors, '
        + '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>',
	        maxZoom: 18
	    });		

		this.normalSites = []; 
		this.normalSiteMarkers = [];
		this.alarmSites  = []; 
		this.alarmSiteMarkers = [];
		this.downSites   = []; 
		this.downSiteMarkers = [];	
		this.down = 0;
		this.alarm = 0;
		this.normal = 0;

		this.normalFeatureGroup = new L.FeatureGroup();
		this.minorMajorFeatureGroup = new L.FeatureGroup();
		this.criticalFeatureGroup = new L.FeatureGroup();
		this.pathMarkers = new L.FeatureGroup();
  
   	 	this.map.addLayer(mapTiles);
   	 	this.addSummary();
   	 	this.addSearchBar();
   	 	this.addOMS();
   	 	this.addAvailabilityListener();
   	 	this.addPathDrawer();
   	 	//this.__initGoogleGeocoding(document.getElementById('location_search'));

   	var self = this;
 	//additional
 	$(document).on('click','.site_detail',function( event ){
        event.preventDefault();
        var mkId = $(this).data('id');
        $.ajax({
				url: "sites-availability?site="+mkId,
				method : "GET",
                success : function( response ){
                    $("#alarm_detailx").html( self.renderPopupAlarm(response.site, response.alarm) );
                    $("#site_alarm_detail").modal("show");
                }
		    })
		    .fail(function(jqXHR, textStatus, errorThrown) {
					        alert(errorThrown);
        });
    });

}

Map.prototype.setCircle = function(latLng, meters) {
	this.bounceSite = "";
    if(!this.circle) {
        this.circle = L.circle(latLng, meters, {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: 0.3
        }).addTo(this.map);
    }
    else {
    	
        this.circle.setLatLng(latLng);
        this.circle.setRadius(meters);
        this.circle.redraw();
    }
    //console.log( this.map );
    this.map.fitBounds(this.circle.getBounds());
};

Map.prototype.addPathDrawer = function(){
	
	this.enablePathDrawing = false;
	this.drawPathBtn = $("#drawPath");
	this.clearPathBtn = $("#clearPath");

	var self = this;

	this.drawPathBtn.click(function(event){
		event.preventDefault();
		if( self.enablePathDrawing  ){
			self.enablePathDrawing = false;
			if( self.polyline.getLatLngs().length > 0 ){
				self.__searchSiteNearPath();
			}
			$(this).html("Draw Route");
		}
		else{
			self.enablePathDrawing = true;
			//self.polyline.addTo( self.map );
			$(this).html("Finish & See Result");
		}
	});		

	this.clearPathBtn.click(function(event){
		event.preventDefault();
		self.__clearPath();
	});


	this.map.on('click',function(event){
		if( self.enablePathDrawing ){
			if( !self.polyline ){
				self.polyline = L.polyline( [ event.latlng ] , { color : '#2ecc71', opacity : 1 , weight : 3 } ).addTo( self.map );
			}

			//console.log( self.polyline );
			else{
				self.polyline.addLatLng( event.latlng );
				this.lastPointInPath = event.latlng;
			}

			//self.map.fitBounds( self.polyline.getBounds() );
		}
	});
}

Map.prototype.__moveAlongPath = function( points, distance, index  ){
	index = index || 0; // set index to 0 by default.

	if( index < points.length ){
		// at least we can draw / get one point(lat,lng) from this point

		var temPoint = null;

		if( points[ index + 1 ] ){
			temPoint = points[index+1];
		}else{
			temPoint = this.lastPointInPath;
		}

		var path = [
			points[index], 
			temPoint
		];

		
		var poln = L.polyline( path );

		var distanceToNextPoint = poln.getLength();

		if( distance <= distanceToNextPoint ){
			return points[ index ].moveTowards( points[ index + 1 ], distance );
		}
		
		return this.__moveAlongPath( points, distance - distanceToNextPoint, index + 1 );

	}

	return null;		
}


Map.prototype.__clearCircleRadius = function(){

	this.clearOverlays();

	this.circle.setLatLng([0,0]);
	this.centerMarker.setLatLng([0,0]);
	this.sizer.setLatLng([0,0]);

}

Map.prototype.__initGoogleGeocoding = function( input ){
	this.onlineSearchBox = new google.maps.places.SearchBox(input);
	this.onlineGeocoder = new google.maps.Geocoder();

	var self = this;

	google.maps.event.addListener(this.onlineSearchBox, 'places_changed', function() {
		var place = self.onlineSearchBox.getPlaces();
		var location = place[0].geometry.location;
		var leafletLocation = new L.LatLng( location.lat(), location.lng() );
		var placeName = place[0].name;
		self.RadiusWidget(leafletLocation.lat,leafletLocation.lng,1000,placeName);
		self.generateResult();
	})
}

Map.prototype.__clearPath = function(){

	if( this.polyline ){
		if( this.polyline.getLatLngs().length > 0 ){
			this.polyline.setLatLngs( [] );
		}
	}
}

Map.prototype.__searchSiteNearPath = function(){
	var nextMarkerAt = 0;
	var nextPoint = null;

	var pPoints = this.polyline.getLatLngs();
	var qPoints = [];
	$.each(pPoints,function(i,v){
		var p = L.latLng( v.lat, v.lng );
		qPoints.push( p );
	});

	var postPoints = [];
	var i = 1;
	while( true ){

		nextPoint = this.__moveAlongPath( qPoints, nextMarkerAt );

		if( nextPoint ){

			var pts = {
				latitude : nextPoint.lat,
				longitude : nextPoint.lng
			};

			postPoints.push( pts );

			//var markerIcon = "http://localhost/geol/assets/images/map/star-3.png";
			this.pathMarkers.clearLayers();
			var marker = L.marker(nextPoint, {
				            draggable: false
				        });

			this.pathMarkers.addLayer( marker );

			//marker.openPopup();
			/*
			var meters = 1000;
			var circle = L.circle(nextPoint, meters, {
		            color: 'red',
		            fillColor: '#f03',
		            fillOpacity: 0.3
		        }).addTo(this.map);
			*/

			nextMarkerAt += 500;  
		}
		else{
			break;
		}
		i++;
	}

	this.showLoading();

	if( this.circle ){
		this.__clearCircleRadius();
	}

	this.abortAjax( this.xhr );

	var self = this;

	this.xhr = $.ajax({			
		type: "POST",
		contentType : "application/json",
		url: "statNearPath",
		data:  JSON.stringify({ points : postPoints }),
		dataType : "JSON",
		beforeSend: function()
		{

		},
		success: function(msg)
		{
			self.hideLoading();
			var nearby = msg;
			self.markSiteWithinRadius( nearby );
			$("#site_found_count").html( nearby.length );
		}
	});
}

Map.prototype.addAvailabilityListener = function(){
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

//development
Map.prototype.renderPopup = function(site){
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

Map.prototype.renderPopupAlarm = function(site,alarm){
	
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

Map.prototype.addOMS = function(){

 	this.oms = new OverlappingMarkerSpiderfier(this.map,{markersWontMove: true, markersWontHide: true,keepSpiderfied:true});

 	this.map.addLayer( this.normalFeatureGroup );
	this.map.addLayer( this.criticalFeatureGroup );
	this.map.addLayer( this.minorMajorFeatureGroup );

	var popupOptions = { maxWidth : '500', maxHeight : "300", className : 'geol-popup' };
	var popup = L.popup(popupOptions);
	var me = this;
	
	this.oms.addListener('click', function(marker) {
	  		
		var contentString = "<i class='fa fa-spin fa-lg' style='color: #FFA46B;' title='Loading...'></i> Loading...";

		popup.setContent( contentString );
		popup.setLatLng( marker.getLatLng() );
		me.map.openPopup(popup);

	  	if( marker.isShouldAjax ){
	  		$.ajax({
		        url : "getSiteAlarm2?site="+marker.siteid,
		        dataType : 'JSON',
		        method : 'GET',
		        success : function( detail ){
		        	me.map.closePopup(popup);
		        	var popup2 = L.popup(popupOptions);
		        	popup2.setContent( me.renderPopupAlarm(detail.site, detail.alarm) );
		        	popup2.setLatLng( marker.getLatLng() );
		        	me.map.openPopup(popup2);
		        },
		        fail : function(){
		        	alert('check error');
		        }
		    });

	  	}else{
	  		$.ajax({
		        url : "getSiteInformation?site="+marker.siteid,
		        dataType : 'JSON',
		        method : 'GET',
		        success : function( information ){
		        	me.map.closePopup(popup);
		        	var popup2 = L.popup(popupOptions);
		        	popup2.setContent( me.renderPopup(information) );
		        	popup2.setLatLng( marker.getLatLng() );
		        	me.map.openPopup(popup2);
		        },
		        fail : function(){
		        	alert('check error');
		        }
		    });
	  	}
  		

	});

	me.oms.addListener('spiderfy', function(markers) {
	  me.map.closePopup();
	});		
}

Map.prototype.setLatLng = function(latLng) {
    this.lat = latLng.lat;
    this.lng = latLng.lng;

    //this._position = latLng;
    
    if(this.circle) {
        this.circle.setLatLng(latLng);
    } 

    if( this.sizer ){
    	var lng = this.circle.getBounds().getNorthEast().lng;     
  		var position = L.latLng( this.lat, lng );
    	this.sizer.setLatLng( position );
    }          
};


Map.prototype.RadiusWidget = function(lat,lng,radius,placeName){

	var self = this;
	this.mode = "radius";
    this.lat = lat;
    this.lng = lng;
    this.radius = radius;
    this._position =L.latLng( this.lat , this.lng );

    
    if(!this.centerMarker) {
        this.centerMarker = L.marker([this.lat, this.lng], {
            draggable: true
        }).bindPopup( placeName );
    
        this.centerMarker.on('drag', function(event) {
            self.setLatLng(event.target.getLatLng()); 
            self.clearOverlays();           
        });

        this.centerMarker.on('dragend',function(event){
        	self.generateResult();
        	self.__reverse( event.target.getLatLng() );
        });
        
        this.centerMarker
            .radiusWidgetDrag()
            .addTo(this.map);

        self.oms.addMarker( this.centerMarker ) ;

    }
    else{
    	//this.map.removeLayer(this.centerMarker);
    	this.centerMarker.setLatLng( L.latLng( this.lat, lng ) );
    	this.centerMarker._popup.setContent( placeName );
    	this.centerMarker.openPopup();
    }
    

    this.map.setView([this.lat, this.lng], 15);
    this.setCircle([this.lat, this.lng], this.radius);
    this.centerMarker.openPopup();
    this.addSizer();

}


Map.prototype.addSizer = function(){
	
	var self = this;

	var lng = this.circle.getBounds().getNorthEast().lng;   
	//console.log(lng);      
  	var position = L.latLng( this.lat, lng );
  	var sizerIconUrl = "images/map/sizer.png";
  	var sizerIcon = L.icon( { iconUrl : sizerIconUrl } );
  	this.sizer_position = position;

  	if( !this.sizer ){
  		this.sizer = L.marker( position , {
            draggable: true,
            icon : sizerIcon
        } ).addTo(this.map).bindPopup("<b>Circle radius : "+this.radius+" meters</b>");

      	this.sizer.on('drag', function(event){
      		self.clearOverlays();
			self.setDistance();
			self.sizer._popup.setContent( "<b>Circle radius : "+self.radius.toFixed(2)+" meters</b>" );
      	});

      	this.sizer.on('dragend',function(event){
      		self.sizer.openPopup();
      		self.generateResult();
      	});
  	}else{
  		this.sizer.setLatLng(position);
  	}

  	this.sizer.openPopup();

	
}

Map.prototype.setDistance = function(){
	var max = 3000;
	var min = 100;
	var pos = this.sizer.getLatLng();
	var center = this.centerMarker.getLatLng();
	//var circleBoundNorthEast = this.circle.getBounds().getNorthEast();
	var distance = this.distanceBetweenPoints(center,pos);//circleBoundNorthEast.distanceTo(this.circle.getLatLng());
		this.radius = distance * 1000 ;

	this.circle.setRadius( this.radius );

}

Map.prototype.clearOverlays = function(){
	this.normalFeatureGroup.clearLayers();
	this.minorMajorFeatureGroup.clearLayers();
	this.criticalFeatureGroup.clearLayers();

	this.downSiteMarkers = [];
	this.normalSiteMarkers = [];
	this.alarmSiteMarkers = [];
}

Map.prototype.resetSummary = function(){
		
	$("#down_count").html(this.down);
	$("#alarm_count").html(this.alarm);
	$("#normal_count").html(this.normal);
 }

Map.prototype.markSiteWithinRadius = function( sites , clearPath ){

	this.clearOverlays();
	
	if( clearPath && this.enablePathDrawing == false )
		this.__clearPath();
	
	this.down = 0;
	this.alarm = 0;
	this.normal = 0;
	this.normalSites = [];
	this.alarmSites  = [];
	this.downSites   = [];
	
	//this.map.setZoom(15);

	this.resetSummary();

	var self = this;
	var exportTarget = "";

	if( sites.length > 0 ){
		var last = "";
		$.each(sites,function(index,site) {

			exportTarget += site.site_Id+"-";
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

			//var href = "http://localhost:8084/pm5-exporter/gis/export/"+self.exportTarget;
			//$("#exportResult").attr("href",href);
			
			

		});

		this.resetSummary();

		this.drawSpecial( this.alarmSites, '#table_alarm_site' );
		this.drawSpecial( this.downSites, '#table_down_site' );
		this.drawSpecial( this.normalSites, '#table_normal_site' );
	}
		//map.setCenter( new google.maps.LatLng( last.latitude , last.longitude ) );
	var href = "customer-complaint-export?lat="+this.lat+"&lon="+this.lng+"&rad="+(this.radius/1000);
	$("#exportResult").attr("href",href);
}


Map.prototype.generateResult = function(options){

	var self = this;
			
	if(this.xhr!=""){
		this.abortAjax(this.xhr);
		this.clearOverlays();
	}

	this.showLoading();

	var rad = self.radius/1000;
	var api_url = self.backend + "?lat="+self.lat+"&lng="+self.lng+"&rad="+rad+"&type="+self.netype;
	
	if( self.site_id || self.site_id != "" ){
		api_url = api_url+"&site="+ self.site_id;
	}
	
	this.xhr = $.ajax({			
		type: "GET",
		url: api_url,
		dataType : "JSON",
		beforeSend: function()
		{

		},
		success: function(msg)
		{
			self.hideLoading();
			var nearby = msg;
			//var tables = msg;
			if( options ){
				self.bounceSite = options.site_id;
			}
			self.markSiteWithinRadius( nearby, true );
			//self.drawTable( tables );
			$("#site_found_count").html( nearby.length );
		}
	});
}


Map.prototype.distanceBetweenPoints = function( p1 , p2 ){
    if (!p1 || !p2) {
        return 0;
    }

    var R = 6371; // Radius of the Earth in km
    var dLat = (p2.lat - p1.lat) * Math.PI / 180;
    var dLon = (p2.lng - p1.lng) * Math.PI / 180;
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(p1.lat * Math.PI / 180) * Math.cos(p2.lat * Math.PI / 180) *
    Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c;
         
    //Limit max 50km and min half km
    if (d > 100) {
        d = 100;
    }
    if (d < 0.5) {
        d = 0.5;
    }
    return d;
}

Map.prototype.addSearchBar = function(){

	this.$searchbar = $("input[name=searchbar]");
	this.$searchbar.keypress(function(e){
		if(e.which == 13){
			self.__search( self.$searchbar.val() );
			$(".search_result").fadeIn();
		}
		else{
			$(".search_result").fadeOut("fast");
		}
	});

	this.$searchbar.focusout(function(e){
		$(".search_result").fadeOut("fast");
	});

	$("body").on("click",".nominatim-res", function(event){
		event.preventDefault();
		var lat = parseFloat( $(this).data('lat') );
		var lng = parseFloat( $(this).data('lng') );

		//var latLng = new L.latLng( lat, lng );

		self.RadiusWidget( lat, lng , 1000, $(this).html() );
		self.generateResult();

	});

	this.$_sne = $("select#site_type");

	this.netype = "all";
	var self = this;

	this.$_sne.change(function(){
		self.netype = self.$_sne.val();

		if( self._position ){
			var custLat = self.lat;
			var custLng = self.lng;
			self.generateResult();
		}

	});

}

Map.prototype.__search = function(keyword){

	var self = this;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	if( keyword.indexOf('@') == 0 ){
		var site_id = keyword.substring(1, keyword.length);
		$.ajax({
        	url : "mapcheck",
        	dataType : "JSON",
        	method : "POST",
        	data : { "site_id" : site_id },
        	success : function( response ){
        		if( response.site_info.site_id ){
				    var site = response.site_info;
					self.RadiusWidget( parseFloat(site.latitude), parseFloat(site.longitude) , 1000, keyword );
					//self.centerMarker.setIcon( leafletIcon );
					self.generateResult(site); 
        		}
        		else{
        			alert("site not found!");
        		}
   		
        	}


        })
	}
	else{
		var geocodeUrl = "http://10.17.17.201/nominatim/search.php?format=json&q="+keyword;

		$.getJSON(geocodeUrl, function(data) {
			$(".search_result ul").html("");
			$.each(data, function(i,v){
				var li = "<li><a href='#' class='nominatim-res' data-lat='"+v.lat+"' data-lng='"+v.lon+"'>"+v.display_name+"</a></li>";
				$(".search_result ul").append( li );
			});

		});
	}
}

Map.prototype.__reverse = function( position ){
	var me = this;
	var reverseGeocode = "http://10.17.17.201/nominatim/reverse.php?format=json&lat="+position.lat+"&lon="+position.lng+"&zoom=18&addressdetails=1";
	$.getJSON(reverseGeocode , function( data ){
		me.centerMarker._popup.setContent( data.display_name );
		me.centerMarker.openPopup();
	});
}

Map.prototype.addSummary = function(){

	$("#site_found_button,#site_normal_button,#site_alarm_button,#site_down_button").click(function(e){
    	e.preventDefault();
    	$($(this).data('target')).modal('show');
    });	

	this.swtch_down 	= new Switchery(document.querySelector('#down_only'), { size: 'small' ,color : '#2ecc71'});
	this.swtch_alarm 	= new Switchery(document.querySelector('#alarm_only'), { size: 'small' ,color : '#2ecc71'});
	this.swtch_normal 	= new Switchery(document.querySelector('#normal_only'), { size: 'small' ,color : '#2ecc71'});

	this.swtchdown 	= $("#down_only");
	this.swtchalarm 	= $("#alarm_only");
	this.swtchnormal 	= $("#normal_only");

	var me = this;

	this.swtchdown.change(function(event){
		if( me.swtchdown.prop( 'checked' ) ){
			me.redrawMarkerByArray( me.downSiteMarkers, me.criticalFeatureGroup );
		}
		else{
			me.criticalFeatureGroup.clearLayers();
		}
	});

	this.swtchalarm.change(function(event){
		if( me.swtchalarm.prop( 'checked' ) ){
			me.redrawMarkerByArray( me.alarmSiteMarkers, me.minorMajorFeatureGroup );
		}
		else{
			me.minorMajorFeatureGroup.clearLayers();
		}
	});

	this.swtchnormal.change(function(event){
		if( me.swtchnormal.prop( 'checked' ) ){
			me.redrawMarkerByArray( me.normalSiteMarkers, me.normalFeatureGroup );
		}
		else{
			me.normalFeatureGroup.clearLayers();
		}
	});
		
}

Map.prototype.redrawMarkerByArray = function( Markers, featureGroup ){
 	for (var i = 0; i < Markers.length; i++ ) {
    	featureGroup.addLayer(Markers[i]);
  	}		
}

Map.prototype.unixTimeToHumanTime = function(unixtime){
	var date = new Date( unixtime );
	var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	var strdt = date.getUTCDate()+"-"+monthNames[ date.getMonth() ]+"-"+date.getUTCFullYear()+"  "+(date.getUTCHours())+":"+date.getMinutes();

	return strdt;		
}

Map.prototype.getSiteAvailability = function( _site , _type ){
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

Map.prototype.abortAjax = function(){
	if( this.xhr ){
		this.xhr.abort();
	}
}


Map.prototype.showLoading = function(){
	$("#content > .loading").stop().removeClass("bounceOut").toggleClass("bounceIn").show();
}

Map.prototype.hideLoading = function(){
	$("#content > .loading").stop().removeClass("bounceIn").toggleClass("bounceOut");
}

Map.prototype.drawTable = function(nearby_sites){
	$("#nearby_site_found").html(nearby_sites);
    $("#nearby_table").dataTable();
}

Map.prototype.drawSpecial = function(data, table_id){
	$(table_id+" > tbody").html('');
	$.each(data, function(index,value){

		var row = '<tr>'+
					'<td>'+(index+1)+'</td>'+
					'<td>'+value.siteId+'</td>'+
					'<td>'+value.siteName+'</td>'+
					'<td>'+value.towerProvider+'</td>'+
					'<td>'+value.ttno+'</td>'+
                    '<td>'+( value.severity == null ? 'NO' : '<a href="#" data-id="'+value.siteId+'" class="site_detail">YES</a>' )+'</td>'+
				'</tr>';

		$(table_id+" > tbody").append( row );

	});

	$(table_id).dataTable();
}

window.addEventListener('load',function(){

	  var script = document.createElement('script');
	  script.type = 'text/javascript';
	  script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyA-99rHYu2y4Y5g_kxBF3ZsQYL99tjTkCg&libraries=places&callback=initGmap';
	  document.body.appendChild(script);
});

function initGmap(){
	pageLoad();
}
function pageLoad(){
	var map = new Map( "#gmap", -6.2234585, 106.84260930000005 );
	$("#content").append('<div class="loading"><img src="img/rolling.gif"/></div>');
}
jQuery(function($){
	//pageLoad();
    SingApp.onPageLoad(pageLoad);

});