package com.perpule.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Path("/get")
public class CustomerGet {
	

	@Path("/customer")
	@GET
	public Response getUser(@QueryParam("name") String name,
			@QueryParam("phone") String phone)
	{
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		StringBuilder str = new StringBuilder();
		
			Query query = new Query("Customer").addFilter("Phone", Query.FilterOperator.EQUAL, phone);
			PreparedQuery pq = ds.prepare(query);
			
			for (Entity cust : pq.asIterable())
			{
				str.append("Name : ").append(cust.getProperty("Name")).append(System.getProperty("line.separator"))
				.append("Age : ").append(cust.getProperty("Age")).append(System.getProperty("line.separator"))
				.append("Phone : ").append(cust.getProperty("Phone")).append(System.getProperty("line.separator"))
				.append("Email : ").append(cust.getProperty("Email")).append(System.getProperty("line.separator"))
				.append('\n').append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
			}
			
			if(str.length() == 0)
			{
				return Response.status(Response.Status.NOT_FOUND)
						.entity("User records not found for " + phone)
						.build();
			}
			
			else
			{
				return Response.status(200)
						.entity("User records retrieved successfully" + "\n " + str)
						.build();
			}
	}
	
}
