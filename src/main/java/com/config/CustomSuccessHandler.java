package com.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isAdmin(roles)) {
            url = "/admin/";
        } else if (isUser(roles)) {
            url = "/user/";
        } else if (isLekhpal(roles)) {
            url = "/lekhpal/";
        } else if (isTahsilMantri(roles)) {
            url = "/tahsil/";
        } else if (isJilaMantri(roles)) {
            url = "/jila/";
        } else if (isPradeshMantri(roles)) {
            url = "/pradesh/";
        } else {
            url = "/accessDenied";
        }

        return url;
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isLekhpal(List<String> roles) {
        if (roles.contains("ROLE_LEKHPAL")) {
            return true;
        }
        return false;
    }

    private boolean isTahsilMantri(List<String> roles) {
        if (roles.contains("ROLE_TAHSIL_MANTRI")) {
            return true;
        }
        return false;
    }

    private boolean isJilaMantri(List<String> roles) {
        if (roles.contains("ROLE_JILA_MANTRI")) {
            return true;
        }
        return false;
    }

    private boolean isPradeshMantri(List<String> roles) {
        if (roles.contains("ROLE_PRADESH_MANTRI")) {
            return true;
        }
        return false;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

}