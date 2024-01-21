package com.gamingreservation.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

	private int userId;

	private String userName;

	private String password;

	private String address;

	private long contactNumber;

}
