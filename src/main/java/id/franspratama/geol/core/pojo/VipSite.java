package id.franspratama.geol.core.pojo;

import java.io.Serializable;

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
@Table(name="vipsites_v2",catalog="geolv2")
public class VipSite implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7493899169848588692L;

	@Id @GeneratedValue
	@Column(name="id",nullable=false)
	private long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="site_id",nullable=false)
	private Site site;
	
	@Column(name="enabled",nullable=false)
	private short enabled;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="groupid")
	private VipGroup group;

	public VipSite(long id, Site site, short enabled, VipGroup group) {
		super();
		this.id = id;
		this.site = site;
		this.enabled = enabled;
		this.group = group;
	}

	public VipSite() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public VipGroup getGroup() {
		return group;
	}

	public void setGroup(VipGroup group) {
		this.group = group;
	}


	@Override
	public String toString() {
		return "VipSite [id=" + id + ", site=" + site + ", enabled=" + enabled + ", group=" + group + "]";
	}
	
	

}
