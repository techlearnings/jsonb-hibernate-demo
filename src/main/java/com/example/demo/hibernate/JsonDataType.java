package com.example.demo.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import com.google.gson.Gson;

public class JsonDataType implements UserType {

	private final Gson gson = new Gson();

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.JAVA_OBJECT };
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return CustomClassType.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		if (x == null) {
			return 0;
		}
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		PGobject object = (PGobject)rs.getObject(names[0]);
		if (object.getValue() != null) {
			return gson.fromJson(object.getValue(), CustomClassType.class);
		}
		return new CustomClassType();
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if(value == null) {
			st.setNull(index, Types.OTHER);
		}else {
			st.setObject(index,gson.toJson(value,CustomClassType.class),Types.OTHER);
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable)value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
