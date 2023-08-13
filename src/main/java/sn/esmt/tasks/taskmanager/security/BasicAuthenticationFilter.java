package sn.esmt.tasks.taskmanager.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


public class BasicAuthenticationFilter extends OncePerRequestFilter {

    
    AuthenticationManager authenticationManager;
    
    
    
	public BasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String basic = getBasicFromRequest(request);

        if (StringUtils.hasText(basic)) {
        	byte[] valueDecoded = Base64.getDecoder().decode(basic);
//        	System.out.println("Decoded value is " + new String(valueDecoded));
        	String[]strs = new String(valueDecoded).split(":");
        	Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		strs[0],
                    		strs[1]
                    )
            );
        	
        	System.out.println("Authentifier : " + authentication.isAuthenticated());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
	}
	
	private String getBasicFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Basic ")) {
            return bearerToken.substring(6, bearerToken.length());
        }
        return null;
    }

}
