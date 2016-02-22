package id.franspratama.geol.core.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.VipGroup;
import id.franspratama.geol.core.pojo.VipSite;

public interface IVipSiteRepository extends JpaRepository<VipSite, Long>{
	public Set<VipSite> findByGroup(VipGroup group);
	
}
