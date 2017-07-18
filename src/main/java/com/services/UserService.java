package com.services;

import java.util.List;
import java.util.Optional;

import com.model.User;
import com.model.UserRoleEnum;

public interface UserService {

	public Optional<User> findUserByEmail(String email);

	public Optional<User> findByConfirmationToken(String confirmationToken);

	public Optional<User> findUserByResetToken(String resetToken);

	public void saveUser(User user);

	public String getUserRole(String email);

	public User deleteUserById(String id);

	public List<User> findAllUsers();

	public boolean isUserExist(User user);

	public List<User> find(String text);

	public User findByID(String id);

	public List<UserRoleEnum> findUserRoles();

	public List<User> findAllUsersByRole(String userRole);

}