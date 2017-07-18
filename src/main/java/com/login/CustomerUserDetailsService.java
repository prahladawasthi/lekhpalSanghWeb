package com.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.services.UserService;

@Service("userDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optional = userService.findUserByEmail(email);
		// User user = userService.findByEmail(email);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(email);
		} else {
			return new MongoUserDetails(optional.get().getFirstName(), email, optional.get().getPassword(),
					userService.getUserRole(email));
		}
	}
}
