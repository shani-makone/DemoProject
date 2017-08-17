package com.demo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.DaoImpl.LoginDAOImpl;
import com.demo.model.Login;

@Component
public class LoginManager {

	@Autowired
	LoginDAOImpl loginDAOImpl;
	
	@Transactional
	public String saveData(Login login){
		return this.loginDAOImpl.saveData(login);
	}
	
}
