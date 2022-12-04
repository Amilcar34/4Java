package com.fourJava.infraestructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.fourJava.domain.User;

@ApplicationScoped
public class UserRepository {

	public User getById(int id) {
		return users.get(id);
	}

	public List<User> findByPoints(Integer points) {
		Predicate<Entry<Integer, User>> predicate = m -> m.getValue().getPoints().equals(points);
		return users.entrySet().stream().filter(predicate).map(Entry::getValue).collect(toList());
	}
	
	public Collection<User> getAll() {
		return users.values();
	}
	
	

	private static Map<Integer, User> users = new HashMap<>() {
		{
			put(1, new User(1, "Ana", 4));
			put(2, new User(2, "Martin", 7));
			put(3, new User(3, "Susana", 5));
			put(4, new User(4, "Pedro", 2));
			put(5, new User(5, "Ayelen", 8));
			put(6, new User(6, "Pablo", 6));
			put(7, new User(7, "Maria", 4));
			put(8, new User(8, "Javier", 5));
			put(9, new User(9, "Alejandra", 7));
		}
	};


}
