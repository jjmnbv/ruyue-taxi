package com.szyciov.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectMappingCustomer extends ObjectMapper {
	private static final long serialVersionUID = 1L;

	public ObjectMappingCustomer() {
		super();
		//格式化时间
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 空值处理为空串
		SerializerProvider sp = (SerializerProvider) this.getSerializerProvider();
		sp.setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
					throws IOException, JsonProcessingException {
				jg.writeString("");
			}
		});
	}
}