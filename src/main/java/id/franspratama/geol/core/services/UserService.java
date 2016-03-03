package id.franspratama.geol.core.services;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import id.franspratama.geol.core.dao.IUserRepository;
import id.franspratama.geol.core.dao.IUserRoleRepository;

@Service
public class UserService {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserRoleRepository userRoleRepository;
	
	
}
