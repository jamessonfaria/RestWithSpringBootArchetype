#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.v1;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ${package}.repository.UserRepository;

@Service
public class UserServices implements UserDetailsService {

	private UserRepository repository;
	
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}
		
		return user;
	}
	

}
