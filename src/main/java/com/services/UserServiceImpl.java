package com.services;

import com.model.User;
import com.model.UserDesignationEnum;
import com.model.UserRoleEnum;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private MongoTemplate mongoTemplate;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    public Optional<User> findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public String getUserRole(String emailOrMobile) {
        /*return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(email)), User.class)
                .getUserRole();*/
        return mongoTemplate.findOne(
                Query.query(new Criteria().orOperator(Criteria.where("email").is(emailOrMobile),
                        Criteria.where("mobile").is(emailOrMobile))),
                User.class).getUserRole();
    }

    public User deleteUserById(String id) {
        return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), User.class);
    }

    @Cacheable("allUsers")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Boolean isUserExist(User user) {
        return ((mongoTemplate.find(
                Query.query(new Criteria().orOperator(Criteria.where("email").regex(user.getEmail(), "i"),
                        Criteria.where("mobile").regex(user.getMobile(), "i"))),
                User.class)).size() > 0);
    }

    public List<User> find(String text) {
        return mongoTemplate.find(Query.query(new Criteria().orOperator(Criteria.where("email").regex(text, "i"),
                Criteria.where("builder").regex(text, "i"), Criteria.where("firstName").regex(text, "i"),
                Criteria.where("lastName").regex(text, "i"))), User.class);
    }

    public User findByID(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public List<UserRoleEnum> findUserRoles() {
        return Arrays.asList(UserRoleEnum.values());
    }

    public List<UserDesignationEnum> findUserDesignation() {
        return Arrays.asList(UserDesignationEnum.values());
    }

    public List<User> findAllUsersByRole(String userRole) {
        return mongoTemplate.find(
                Query.query(new Criteria().orOperator(Criteria.where("userRole").regex(userRole, "i"))), User.class);
    }

    @Override
    public List<User> findLekhpal(String search) {

        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("designation").regex(UserDesignationEnum.Lekhpal.toString(), "i")
                ).orOperator(
                        Criteria.where("firstName").regex(search, "i"),
                        Criteria.where("lastName").regex(search, "i")
                )
        );

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findVillage(String search) {

        return mongoTemplate.find(
                Query.query(new Criteria().orOperator(Criteria.where("areaAppointedAt").regex(search, "i"))), User.class);
    }
}