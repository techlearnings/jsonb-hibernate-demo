package com.example.demo.hibernate;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;

public class CustomPostgreSqlDialect extends PostgreSQL95Dialect{

	public CustomPostgreSqlDialect() {
		this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}
}
