/**
 *  Frans Filasta Pratama
 */

function from_unixtime( unix_timestamp ){
	var date = new Date(unix_timestamp);
	// Hours part from the timestamp
	var hours = date.getHours();
	// Minutes part from the timestamp
	var minutes = "0" + date.getMinutes();
	// Seconds part from the timestamp
	var seconds = "0" + date.getSeconds();

	// Will display time in 10:30:23 format
	var formattedTime = hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
	
	return date.getDate()+"-"+date.getMonth()+"-"+date.getFullYear()+" "+formattedTime;
	
}