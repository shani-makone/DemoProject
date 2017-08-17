package com.demo.DaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.model.Login;

@Component
public class LoginDAOImpl {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public String saveData(Login lo){
			manager.merge(lo);
		return null;
		
	}
}
