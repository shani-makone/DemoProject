package com.demo.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.manager.LoginManager;
import com.demo.model.Login;

@Controller
public class LoginController {

	@Autowired
	LoginManager loginManager;
	
	private final ProviderSignInUtils providerSignInUtils;
	
	@Inject
	public LoginController(ConnectionFactoryLocator connectionFactoryLocator,
			UsersConnectionRepository connectionRepository) {

		this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);

	}
	
	@RequestMapping(value="/getData")
	public String getData(@RequestParam String name){
		System.out.println("name is======="+name);
		Login login =new Login();
		login.setName(name);
		loginManager.saveData(login);
		return null;
		
	}
	
}
