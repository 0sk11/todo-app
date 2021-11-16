package com.spring.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.demo.model.Model;


//Model is the model we created for collection and String is the type of id
//Use Repository annotation to create a mongodb Model repository
@Repository
public interface ModelRepository extends MongoRepository<Model, String> {
	@Query("{'todo': ?0}")
	Optional<Model> findByTodo(String todo);
	
}
