package com.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.model.User;
import com.model.UserRoleEnum;
import com.repository.UserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findOneByEmail(email);
	}

	public Object findUserRoles() {
		return Arrays.asList(UserRoleEnum.values());
	}

	public boolean isUserExist(User user) {
		return ((mongoTemplate.find(
				Query.query(new Criteria().orOperator(Criteria.where("email").regex(user.getEmail(), "i"))),
				User.class)).size() > 0) ? true : false;
	}

	public Object findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User findByID(String id) {
		return mongoTemplate.findById(id, User.class);
	}

	public User deleteUserById(String id) {
		return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("id").is(id)), User.class);
	}

	public void saveUser(User user) {
		userRepository.save(user);
		
	}

	public User findUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}	
}
