/**
 *  @Author Frans Filasta Pratama
 */

$.extend($.fn.datagrid.methods, {
	refetch: function(jq){
		return jq.each(function(){
			var target = this;
			var state = $.data(target, 'datagrid');
			var opts = state.options;
			var view = opts.view;
			var dc = state.dc;
			var top = $(dc.body2).scrollTop();
			var index = Math.floor(top/25);
			var page = Math.floor(index/opts.pageSize) + 1;

			view.getRows.call(view, target, page, function(rows){
				this.index = (page-1)*opts.pageSize;
				this.rows = rows;
				this.r1 = rows;
				this.r2 = [];
				this.populate.call(this, target);
				dc.body2.triggerHandler('scroll.datagrid');
			});
		})
	}
});


var aa_list = $("#active_alarm_list");
	aa_list.datagrid({
		onRowContextMenu : function(e,index,row){
			e.preventDefault();
			$(this).datagrid('selectRow', index);
			$('#alarmRow').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});


var remarkval = $("#remarkval");
	remarkval.textbox({
		disabled : true
	});

var remark_category = $("#remark_category");
var remark_categoryVal = "-1";
	remark_category.combobox({
	    valueField:'value',
	    textField:'text',
		onSelect : function( row ){
			var val = remark_category.combobox('getValue');
			if( val != "" ){
				remarkval.textbox('enable');
			}
			else{
				remarkval.textbox("disable");
			}
			remark_categoryVal = val;
		}
	});

$("#saveremark").click(function(evt){

	var row = aa_list.datagrid("getSelected");
	var remarker = $("input[name=user_id]").val();
	var remark_cat = remark_category.combobox('getValue');
	var remark = remarkval.textbox('getValue');

	var val = remark_category.combobox('getValue');

	if( val != "" ){
		$.ajax({
			url : "<?php echo site_url('netcool/save_remark') ?>",
			method : "POST",
			data : { row : row, remarker : remarker, category : remark_cat, remark : remark },
			success : function(response){
				if( response != undefined ){
					if( response.status == "success" ){
						$("#remark_dialog").dialog("close");
						aa_list.datagrid('load');
					}
					else if( response.status == "failed" ){
						alert('something went worng :(');
					}
					else{
						alert('check error with firebug.');
					}
				}
			}
		});
	}
	else{
		alert("Please choose category first");
	}
});

$("#closeremarkdlg").click(function(evt){
	evt.preventDefault();
	clearRemarkForm();
	$("#remark_dialog").dialog("close");
});


var clearRemarkForm = function(){
	remark_category.combobox('setValue','Choose one');
	remarkval.textbox('setText','Fill your remark here.');
	remarkval.textbox('disable');
}


var severityValue 	= "";
var nodeValue		= "";
var siteValue		= "";
var zoneValue		= "";
var ttnoValue		= "";
var lastOValue      = "";
var lastRec 		= "";
var summaryValue	= "";
var spvareaValue = "";
var mgrareaValue = "";

$("#search").click(function(evt){
	evt.preventDefault();
	var dataPost = {
		'severity' 		: severityValue,
		'zone'			: zoneValue,
		'site'			: siteValue,
		'ttno'			: ttnoValue,
		'last_occurrence' : lastOValue,
		'summary'		: summaryValue,
		'node'			: nodeValue,
		'mgrarea'   : mgrareaValue,
		'spvarea'   : spvareaValue
	};

	filterAlarm( dataPost );
});



function stylingSeverity( val , row )
{
    if (row.Severity == "Minor"){
        return '<span style="color:yellow;font-weight:bolder">'+val+'</span>';
    }else if( row.Severity == "Major" )
    {
    	return '<span style="color:orange;font-weight:bolder">'+val+'</span>';				    }
    else if( row.Severity == "Critical" )
    {
    	return '<span style="color:red;font-weight:bolder">'+val+'</span>';				    }
    else {
        return val;
    }
}


function doFTR()
{
	var row = aa_list.datagrid('getSelected');
	var rowIndex = aa_list.datagrid('getRowIndex');

	$("#terminalContent").html("<?php echo $this->session->userdata(md5('name')) ?>@geol:~$");
	$("#terminalContent").append(' Loading script... \n');
	$("#terminal").window('open');

	$.ajax({
		url : "<?php echo site_url('super/reset') ?>",
		method : "POST",
		dataType : "text",
		data : { "site_id" : row.SiteId,'node' : row.Node, 'Summary' : row.Summary },
		success : function( response )
		{
			$("#terminalContent").append(response);
			var currentTime = new Date();
			var strCurrentTime = currentTime.getFullYear()+"-"+(currentTime.getMonth()+1)+"-"+currentTime.getDate()+" "+currentTime.getHours()+":"+currentTime.getMinutes()+":"+currentTime.getSeconds();
			aa_list.datagrid('refetch');
		}
	});
}

var severity_combobox = $("select#severity");
	severity_combobox.combobox({
	    valueField:'value',
	    textField:'text',
		onSelect : function( row ){
			var val = severity_combobox.combobox('getValue');
			if( val == "all" ) val = "";
			severityValue = val;
		}
});

var spvarea_combobox = $("select#spvarea");
	spvarea_combobox.combobox({
	    valueField:'value',
	    textField:'text',
		onSelect : function( row ){
			var val = spvarea_combobox.combobox('getValue');
			if( val == "all" ) val = "";
			spvareaValue = val;
		}
});

var mgrarea_combobox = $("select#mgrarea");
	mgrarea_combobox.combobox({
	    valueField:'value',
	    textField:'text',
		onSelect : function( row ){
			var val = mgrarea_combobox.combobox('getValue');
			if( val == "all" ) val = "";
			mgrareaValue = val;
		}
});

var zone_combobox = $("select#zone_filter");
	zone_combobox.combobox({
	    valueField:'value',
	    textField:'text',
		onSelect : function( row ){
			var val = zone_combobox.combobox('getValue');
			if( val == "all" ) val = "";
			zoneValue = val;
		}
	});

var node_textbox = $("#node");
	node_textbox.textbox({
		onChange : function(newValue,oldValue){
			nodeValue = newValue;
		}
	});

var site_textbox = $("#site");
	site_textbox.textbox({
		onChange : function(newValue,oldValue){
			siteValue = newValue;
		}
	});


var summary_textbox = $("#summary");
	summary_textbox.textbox({
		onChange : function( newValue, oldValue ){
			summaryValue = newValue;
		}
	});


var ttno_textbox = $("#ttno");
	ttno_textbox.textbox({
		onChange : function(newValue,oldValue){
			ttnoValue = newValue;
		}
	});


var lst_occurrence = $("#last_occurrence_filter");
	lst_occurrence.datebox({
		onSelect : function( date ){
			var dt = lst_occurrence.datebox('getValue');
			lastOValue = dt;
		}
	});

var lst_received = $("#last_received_filter");
	lst_received.datebox({
		onSelect : function( date ){
			var dt = lst_received.datebox('getValue');
			lastRec = dt;
		}
	});

var filterAlarm = function( data )
{
	aa_list.datagrid('load',data);
}
