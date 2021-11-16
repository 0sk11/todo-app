package com.spring.demo.service;

import javax.validation.ConstraintViolationException;

import com.spring.demo.exception.ModelCollectionExpection;
import com.spring.demo.model.Model;

public interface TodoService {
	public void createTodo(Model todo) throws ConstraintViolationException,ModelCollectionExpection;

}
