package com.fourJava.infraestructure;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.fourJava.domain.User;

@Service
public class UserService {

	@Inject
	UserRepository userRepository;
	
	public User getById(int id) {
		return userRepository.getById(id);
	}

	public List<User> findByPoints(Integer points) {
		return userRepository.findByPoints(points);
	}

	public Collection<User> getAll() {
		return userRepository.getAll();
	}

	
}
