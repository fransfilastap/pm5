package id.franspratama.geol.web.api;

import id.franspratama.geol.core.pojo.NEDownAge;
import id.franspratama.geol.core.pojo.SpanType;
import id.franspratama.geol.core.pojo.TimeSpan;

public class NEDownMovementRequestHolder {
	
	private String age;
	private String timespan;
	private String type;
	
	public NEDownMovementRequestHolder() {
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
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
	
	public NEDownAge getAgeValue(){
		return NEDownAge.valueOf(age.toUpperCase());
	}
	
	public TimeSpan getTimeSpanValue(){
		return TimeSpan.valueOf(timespan.toUpperCase());
	}
	
	public SpanType getSpanTypeValue(){
		return SpanType.valueOf(type.toUpperCase());
	}
	
}
