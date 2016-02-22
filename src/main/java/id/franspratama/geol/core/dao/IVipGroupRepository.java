package id.franspratama.geol.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import id.franspratama.geol.core.pojo.VipGroup;

public interface IVipGroupRepository extends JpaRepository<VipGroup, Long>{
	public List<VipGroup> findByGroupName(String groupname);
}
