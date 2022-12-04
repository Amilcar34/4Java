package com.fourJava.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fourJava.infraestructure.UserService;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.inject.Inject;

@Path("/users")
@Produces(APPLICATION_JSON)
public class UserResource {

	@Inject
	UserService userService;
	
	@GET
	public String hello() {
		return "Hello from RESTEasy Reactive";
	}
	
	@GET
	@Path("getAll")
	public Response getAll() {
		return Response.ok(userService.getAll()).build();
	}
	
	@GET
	@Path("/{id}")
	public Response get(Integer id) {
		return Response.ok(userService.getById(id)).build();
	}	

	@GET
	@Path("byPoints/{points}")
	public Response findByPoints(Integer points) {
		return Response.ok(userService.findByPoints(points)).build();
	}	
}