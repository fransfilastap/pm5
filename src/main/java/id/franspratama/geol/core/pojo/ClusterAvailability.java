package id.franspratama.geol.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import id.franspratama.geol.util.CustomDateTimeSerializer;


@Entity
@Table(name="availabilities_cluster",catalog="geolv2")
public class ClusterAvailability implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2948992922419169197L;
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="Time",nullable=false)
	private Date time;
	
	@Column(name="cluster",nullable=false)
	private String cluster;
	
	@Column(name="region",nullable=false)
	private String region;
	
	@Column(name="availability",nullable=false)
	private double availability;
	
	@Column(name="technology",nullable=false)
	private String technology;
	
	public ClusterAvailability() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public void setTime(Date time) {
		this.time = time;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster= cluster;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public double getAvailability() {
		return availability;
	}

	public void setAvailability(double availability) {
		this.availability = availability;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}
	
	
	
}
