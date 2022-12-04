package com.fourJava.rest;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourJava.infraestructure.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserResourceSpringWeb {

	@Autowired
	UserService userService;
	
	@GetMapping("/stringHello")
	public String hello() {
		return "Hello from RESTEasy Reactive";
	}

	@GetMapping("/getAll")
	public Response getAll() {
		return Response.ok(userService.getAll()).build();
	}

	@GetMapping("/{id}")
	public Response get(@PathVariable Integer id) {
		return Response.ok(userService.getById(id)).build();
	}	
	
	@GetMapping("/byPoints/{points}")
	public Response findByPoints(Integer points) {
		return Response.ok(userService.findByPoints(points)).build();
	}	
	
}