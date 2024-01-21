package com.gamingreservation.dao;

import com.gamingreservation.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModelRepository extends JpaRepository<UserModel, Integer> {
}
