package id.franspratama.geol.core.sec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import id.franspratama.geol.core.dao.IUserRepository;
import id.franspratama.geol.core.dao.IUserRoleRepository;
import id.franspratama.geol.core.pojo.UserRole;

@Service("jpaUserDetailService")
public class UserDetailServiceImpl implements UserDetailsService{

	private @Autowired IUserRepository userRepository;
	private @Autowired IUserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		id.franspratama.geol.core.pojo.User user = userRepository.findUserByUsername(username);
		List<UserRole> userRoles = userRoleRepository.findByUser(user);
		
		List<GrantedAuthority> authorities = 
                buildUserAuthority(userRoles);

		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole().getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
	
	private User buildUserForAuthentication(id.franspratama.geol.core.pojo.User user, 
			List<GrantedAuthority> authorities) {
			return new User(user.getUsername(), user.getPassword(), 
				user.isEnabled(), true, true, true, authorities);
	}

}
