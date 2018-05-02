package com.example.demo.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.example.demo.hibernate.CustomClassType;
import com.example.demo.hibernate.JsonUserType;

@Entity
@TypeDef(name = "JsonUserType", typeClass = JsonUserType.class)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Type(type = "JsonUserType")
	private Map<String, String> information;

	@Type(type = "com.example.demo.hibernate.JsonDataType") // can be written in this way
	private CustomClassType data;

	public Integer getId() {
		return id;
	}

	public CustomClassType getData() {
		return data;
	}

	public void setData(CustomClassType data) {
		this.data = data;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, String> getInformation() {
		return information;
	}

	public void setInformation(Map<String, String> information) {
		this.information = information;
	}

}
