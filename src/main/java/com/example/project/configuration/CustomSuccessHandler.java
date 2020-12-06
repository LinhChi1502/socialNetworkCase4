package com.example.project.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()){
            System.out.println("Khong chuyen huong");
            return;
        }
        redirectStrategy.sendRedirect(request, response, url);

    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String url ="";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority g: authorities
        ) {
            roles.add(g.getAuthority());
        }
        if (checkUser(roles)){
            url= "/home";
        }
        return url;
    }


    private boolean checkUser(List<String> roles){
        if (roles.contains("ROLE_USER")){
            return  true;
        }
        return false;
    }
}
