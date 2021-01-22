package com.athome.idubbo.service;

import java.util.Map;

public interface UserInfoService {
	public Map<String, String> getUser(String id);
    public Map<String, String>[] getUsers();
}
