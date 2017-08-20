package com.login;

import com.model.User;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String emailOrMobile) throws UsernameNotFoundException {
        Optional<User> emailOptional = userService.findUserByEmail(emailOrMobile);
        Optional<User> mobileOptional = userService.findUserByMobile(emailOrMobile);

        if (emailOptional.isPresent()) {
            User user = emailOptional.get();
            return new MongoUserDetails(user.getFirstName(), user.getEmail(), user.getMobile(), user.getPassword(),
                    userService.getUserRole(emailOrMobile));
        } else if (mobileOptional.isPresent()) {
            User user = mobileOptional.get();
            return new MongoUserDetails(user.getFirstName(), user.getEmail(), user.getMobile(), user.getPassword(),
                    userService.getUserRole(emailOrMobile));
        } else {
            throw new UsernameNotFoundException(emailOrMobile);
        }

    }
}
