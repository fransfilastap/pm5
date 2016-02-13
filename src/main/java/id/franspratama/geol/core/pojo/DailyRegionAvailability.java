package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.CustomDateTimeSerializer;


@NamedNativeQueries({
	@NamedNativeQuery(
			name = "regionDailyAvailablity",
			query = "SELECT * FROM daily_availability_region "+
					"WHERE TIME BETWEEN ? AND ? "+
					"GROUP BY TIME,region,technology",
			resultClass = DailyRegionAvailability.class
	)
})


@Entity
@Table(name="daily_availability_region",catalog="geolv2")
public class DailyRegionAvailability {
	
	@Column(name="time")
	private Date time;
	
	@Column(name="region")
	private String region;
	
	@Column(name="technology")
	private String technology;
	
	@Column(name="availability")
	private double availability;
	
	@Id @GeneratedValue
	@Column(name="id",nullable=false)
	private long id;
	
	public DailyRegionAvailability() {
		super();
	}
	
	public DailyRegionAvailability(Date time, String region, String technology, double availability, long id) {
		super();
		this.time = time;
		this.region = region;
		this.technology = technology;
		this.availability = availability;
		this.id = id;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public Date getTime() {
		return time;
	}
	
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public double getAvailability() {
		return availability;
	}
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	
	
	

}
