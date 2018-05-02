package com.example.demo.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUserType implements UserType {

	private final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		return deepCopy(arg0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object deepCopy(Object originalValue) throws HibernateException {
		if (originalValue == null) {
			return null;
		}

		if (!(originalValue instanceof Map)) {
			return null;
		}

		Map resultMap = new HashMap<>();

		Map<?, ?> tempMap = (Map<?, ?>) originalValue;
		tempMap.forEach((key, value) -> resultMap.put((String) key, (String) value));

		return resultMap;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		Object copy = deepCopy(value);

		if (copy instanceof Serializable) {
			return (Serializable) copy;
		}

		throw new SerializationException(
				String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		if (x == null) {
			return 0;
		}

		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor arg2, Object owner)
			throws HibernateException, SQLException {
		PGobject object = (PGobject) rs.getObject(names[0]);
		if (object.getValue() != null) {
			return gson.fromJson(object.getValue(), Map.class);
		}
		return new HashMap<>();
	}

	@Override
	public void nullSafeSet(PreparedStatement ps, Object value, int index, SharedSessionContractImplementor arg3)
			throws HibernateException, SQLException {

		if (value == null) {
			ps.setNull(index, Types.OTHER);
		} else {
			ps.setObject(index, gson.toJson(value, Map.class), Types.OTHER);
		}
	}

	@Override
	public Object replace(Object original, Object arg1, Object arg2) throws HibernateException {
		return deepCopy(original);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return Map.class;
	}

	@Override
	public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
	}

}
