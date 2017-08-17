package com.demo.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login")
public class Login {
	
private Integer id;
private String name;

@Id
@GeneratedValue(strategy = IDENTITY)
@Column(name = "id", unique = true, nullable = false)
public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}
@Column(name="name")
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}



	
}
