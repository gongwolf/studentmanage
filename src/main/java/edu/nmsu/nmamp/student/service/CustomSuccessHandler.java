package edu.nmsu.nmamp.student.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomSuccessHandler.class); 

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		handle(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		String targetUrl = determineTargetUrl(authentication);
		
		if (response.isCommitted()) {
			LOGGER.error("Response has already been committed. Unable to redirect to " + targetUrl); 
            return;
        }
		redirectStrategy.sendRedirect(request, response, targetUrl);
		 
	}

	protected String determineTargetUrl(Authentication authentication){
		
		boolean isUser = false; 
		boolean isAdmin = false; 
		boolean isStaff = false; 
		boolean isMentor = false; 
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
		for(GrantedAuthority authority: authorities){
			switch(authority.getAuthority()){
			case "ROLE_USER": 
				isUser = true; break; 
			case "ROLE_ADMIN": 
				isAdmin = true; break; 
			case "ROLE_STAFF":
				isStaff = true; break; 
			case "ROLE_MENTOR":
				isMentor = true; break; 
			}
		}
		
	    String targetUrl = ""; 
	    if(isUser){
	    	targetUrl = "/home"; 
	    }else if(isAdmin || isStaff){
	    	targetUrl = "/admin/home"; 
	    }else if(isMentor){
	    	targetUrl = "/mentor/home"; 
	    }else{
	    	targetUrl = "/accessDenied";
	    }
		return targetUrl; 
	}
	 
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
 
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    } 

}
