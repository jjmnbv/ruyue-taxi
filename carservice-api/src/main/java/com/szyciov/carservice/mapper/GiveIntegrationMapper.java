package com.szyciov.carservice.mapper;

import java.util.List;

import com.szyciov.lease.entity.PubDictionary;

public interface GiveIntegrationMapper {
	PubDictionary findKeyValue();
	void updateKeyText();
	List<String> findAllUsers();
}
