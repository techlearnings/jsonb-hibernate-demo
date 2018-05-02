package com.example.demo.hibernate;

public class CustomClassType {

	private int a;

	private String b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "CustomClassType [a=" + a + ", b=" + b + "]";
	}

}
