package com.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {
	
	Optional<User> findByEmail(String email);

	Optional<User> findByConfirmationToken(String confirmationToken);

	Optional<User> findByResetToken(String resetToken);
	
	User findOneByEmail(String email);
}
