package com.repository;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);

	Optional<User> findByConfirmationToken(String confirmationToken);

	Optional<User> findByResetToken(String resetToken);
}