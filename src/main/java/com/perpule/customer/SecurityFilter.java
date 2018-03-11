package com.perpule.customer;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter  {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String ADD_URL_PREFIX = "add";
	private static final String GET_URL_PREFIX = "get";
	
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if(requestContext.getUriInfo().getPath().contains(ADD_URL_PREFIX) || requestContext.getUriInfo().getPath().contains(GET_URL_PREFIX))
		{
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if(authHeader!=null && authHeader.size() > 0)
			{
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				
				String decodedString = Base64.decodeAsString(authToken);
				
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if("admin".equals(username) && "password".equals(password))
				{
					return;
				}
			}
			
			Response unauthorized = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User is not permitted to access the resource").build();
			
			requestContext.abortWith(unauthorized);
		}
	}

}
