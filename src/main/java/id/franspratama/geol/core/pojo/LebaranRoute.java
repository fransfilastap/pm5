package id.franspratama.geol.core.pojo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="lebaran_routes",catalog="geolv2")
public class LebaranRoute {
	
	@Id @GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="region",nullable=false)
	private String region;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="routetype")
	private LebaranRouteType type;
	
	@Column(name="routename",nullable=false)
	private String routename;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,mappedBy="route")
	private Set<LebaranSite> sites;
	
	public LebaranRoute() {
		super();
	}

	public LebaranRoute(int id, String region, LebaranRouteType type, String routename, Set<LebaranSite> sites) {
		super();
		this.id = id;
		this.region = region;
		this.type = type;
		this.routename = routename;
		this.sites = sites;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRoutename() {
		return routename;
	}

	public void setRoutename(String routename) {
		this.routename = routename;
	}

	public Set<LebaranSite> getSites() {
		return sites;
	}

	
	public void setSites(Set<LebaranSite> sites) {
		this.sites = sites;
	}

	public LebaranRouteType getType() {
		return type;
	}

	public void setType(LebaranRouteType type) {
		this.type = type;
	}


	
	
	

}
