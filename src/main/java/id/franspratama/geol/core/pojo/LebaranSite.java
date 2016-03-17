package id.franspratama.geol.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="lebaran_route_sites",catalog="geolv2")
public class LebaranSite {
	
	@Id @GeneratedValue
	@Column(name="id",nullable=false)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="route_id")
	private LebaranRoute route;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="site_id")
	private Site site;
	
	@Column(name="enabled",nullable=false)
	private int enabled;
	
	public LebaranSite() {
		super();
	}
	

	public LebaranSite(int id, LebaranRoute route, Site site, int enabled) {
		super();
		this.id = id;
		this.route = route;
		this.site = site;
		this.enabled = enabled;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LebaranRoute getRoute() {
		return route;
	}

	public void setRoute(LebaranRoute route) {
		this.route = route;
	}


	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	
	

}
