package id.franspratama.geol.web.api;

import id.franspratama.geol.core.pojo.NetworkTechnology;
import id.franspratama.geol.core.pojo.Region;
import id.franspratama.geol.core.pojo.SpanType;
import id.franspratama.geol.core.pojo.TimeSpan;

public class AvailabilityRequestHolder {
	
	private String region;
	private String netype;
	private String timespan;
	private String type;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTimespan() {
		return timespan;
	}
	public void setTimespan(String timespan) {
		this.timespan = timespan;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getNetype() {
		return netype;
	}
	public void setNetype(String netype) {
		this.netype = netype;
	}
	public AvailabilityRequestHolder() {
		super();
	}
	
	public SpanType getSpanTypeValue(){
		return SpanType.valueOf(type);
	}
	
	public TimeSpan getTimeSpanValue(){
		return TimeSpan.valueOf(timespan);
	}
	
	public NetworkTechnology getNetworkTechnologyValue(){
		
		NetworkTechnology networkTechnology = new NetworkTechnology();
							networkTechnology.setTechnology( netype );
		
		return networkTechnology;
	}
	
	public Region getRegionValue(){
		Region r = new Region();
		r.setRegion(region);
		return r;
	}
	

}
