package com.services;

import com.model.User;
import com.model.UserDesignationEnum;
import com.model.UserRoleEnum;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByMobile(String mobile);

    Optional<User> findByConfirmationToken(String confirmationToken);

    Optional<User> findUserByResetToken(String resetToken);

    void saveUser(User user);

    String getUserRole(String emailOrMobile);

    User deleteUserById(String id);

    List<User> findAllUsers();

    Boolean isUserExist(User user);

    List<User> find(String text);

    User findByID(String id);

    List<UserRoleEnum> findUserRoles();

    List<UserDesignationEnum> findUserDesignation();

    List<User> findAllUsersByRole(String userRole);

    List<User> findLekhpal(String search);

    List<User> findVillage(String search);
}