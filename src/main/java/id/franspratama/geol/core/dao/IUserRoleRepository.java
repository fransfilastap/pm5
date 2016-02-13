package id.franspratama.geol.core.dao;

import java.util.List;

import id.franspratama.geol.core.pojo.User;
import id.franspratama.geol.core.pojo.UserRole;

public interface IUserRoleRepository extends IBaseRepository<UserRole, Integer>{
	
	public List<UserRole> findByUser(User user);

}
