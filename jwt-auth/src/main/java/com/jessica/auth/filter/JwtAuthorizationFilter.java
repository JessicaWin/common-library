package com.jessica.auth.filter;

import com.jessica.jwt.JwtClaim;
import com.jessica.jwt.JwtUtils;
import com.jessica.user.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "jwtAuthFilter", urlPatterns = "/api/*")
public class JwtAuthorizationFilter implements Filter {
	@Autowired
	private Environment env;

	@Autowired
	private UserContext userContext;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Change the req and res to HttpServletRequest and HttpServletResponse
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (RequestMethod.OPTIONS.name().equalsIgnoreCase(httpServletRequest.getMethod())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			chain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			// Get authorization from Http request
			String authHeader = httpServletRequest.getHeader("Authorization");
			String publicKeyStr = env.getProperty("publicKey");
			try {
				JwtClaim jwtClaim = JwtUtils.parseToken(authHeader, publicKeyStr);
				userContext.reset(jwtClaim.getUserName());
				chain.doFilter(httpServletRequest, httpServletResponse);
			} catch (Exception exception) {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		}
	}
}
