package com.spring.demo.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.spring.demo.repository.ModelRepository;
import com.spring.demo.service.TodoService;
import com.spring.demo.exception.ModelCollectionExpection;
import com.spring.demo.model.Model;

@RestController
public class ModelController {
	@Autowired
	private ModelRepository modelRepository;
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		List<Model> todos = modelRepository.findAll();
		if(todos.size()>0) {
			return new ResponseEntity<List<Model>>(todos,HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Available",HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/todos")
	public ResponseEntity <?> postATodo(@RequestBody Model todo){
				try {
					todoService.createTodo(todo);
					return new ResponseEntity<>("Added",HttpStatus.OK);
				} catch (ConstraintViolationException e) {
					// TODO: handle exception
					return new ResponseEntity<> (e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
				}catch(ModelCollectionExpection e) {
					return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
				}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id){
		Optional<Model> todoOptional = modelRepository.findById(id);
		if(!todoOptional.isEmpty()) {
			return new ResponseEntity<>(todoOptional.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found anything @ "+id,HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id,
			@RequestBody Model todo)
	{
		
		Optional<Model> todoOptional = modelRepository.findById(id);
		if(todoOptional.isPresent()) {
			Model modelToSave = todoOptional.get();
			modelToSave.setTodos(todo.getTodos()!=null?todo.getTodos():modelToSave.getTodos());
			modelToSave.setCompleted(todo.getCompleted()!=null?todo.getCompleted():modelToSave.getCompleted());
			modelToSave.setDescription(todo.getDescription()!=null?todo.getDescription():modelToSave.getDescription());
			modelToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
			modelRepository.save(modelToSave);
			return new ResponseEntity<>(modelRepository.findById(id).get(),HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found anything @ "+id,HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			modelRepository.deleteById(id);
			return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);			
		}		
	}
	
}
