package com.gamingreservation.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingreservation.dao.UserModelRepository;
import com.gamingreservation.model.UserModel;
import com.gamingreservation.service.UserModelService;

@Service
public class UserModelServiceImpl implements UserModelService {
	
	@Autowired
	private UserModelRepository userModelRepository;

	@Override
	public UserModel saveUser(UserModel userModel) {
		
		return userModelRepository.save(userModel);
	}
}
