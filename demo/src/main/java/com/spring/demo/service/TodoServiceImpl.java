package com.spring.demo.service;

import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.demo.exception.ModelCollectionExpection;
import com.spring.demo.model.Model;
import com.spring.demo.repository.ModelRepository;
@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	ModelRepository todoRepository;

	@Override
	public void createTodo(Model todo) throws ConstraintViolationException,ModelCollectionExpection {
		// TODO Auto-generated method stub
		Optional<Model> todoOptional = todoRepository.findByTodo(todo.getTodos());
		if (todoOptional.isPresent()) {
			throw new ModelCollectionExpection("This todo already exists");
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todo);
		}
		
	}
	
}
