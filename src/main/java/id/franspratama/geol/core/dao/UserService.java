package id.franspratama.geol.core.dao;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserRoleRepository userRoleRepository;
	
	
}
