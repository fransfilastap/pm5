package id.franspratama.geol.core.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="vipgroups",catalog="geolv2")
public class VipGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8459325076248669550L;

	@Id @GeneratedValue
	@Column(name="id")
	private long id;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="group")
	private Set<VipSite> sites;
	
	@Column(name="groupname",nullable=false)
	private String groupName;
	
	@Column(name="desc")
	private String desc;

	public VipGroup(long id, Set<VipSite> sites, String groupName, String desc) {
		super();
		this.id = id;
		this.sites = sites;
		this.groupName = groupName;
		this.desc = desc;
	}

	public VipGroup() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<VipSite> getSites() {
		return sites;
	}

	public void setSites(Set<VipSite> sites) {
		this.sites = sites;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "VipGroup2 [id=" + id + ", sites=" + sites + ", groupName=" + groupName + ", desc=" + desc + "]";
	}
	
	
}
