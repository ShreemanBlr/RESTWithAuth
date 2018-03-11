package com.perpule.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/customer")
public class CustomerAdd {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addUser(
			@FormParam("name") String name,
			@FormParam("age") int age,
			@FormParam("phone") String phone,
			@FormParam("email") String email) 
		
		{
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			
			Key key = KeyFactory.createKey("Customer", phone.hashCode());
			
			Entity en = new Entity("Customer", key);
			en.setProperty("Name", name);
			en.setProperty("Age", age);
			en.setProperty("Phone", phone);
			en.setProperty("Email", email);
		
			ds.put(en);
		
			return Response.status(200)
				.entity("The user was added successfully " + "Name : " + name + ", Age : " + age + ", Phone : " + phone + ", Email : " + email)
				.build();
			
		}
	
}
