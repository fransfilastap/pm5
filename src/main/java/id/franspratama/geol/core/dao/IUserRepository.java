package id.franspratama.geol.core.dao;

import id.franspratama.geol.core.pojo.User;


public interface IUserRepository extends IBaseRepository<User,Integer>{
	public User findUserByUsername(String username);
}
