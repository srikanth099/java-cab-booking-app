package com.wipro.cabbooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractUser {

	@Column(nullable = false, unique = true)
    private String username;
	@Column(nullable = false)
	private String password;
    private String address;
    private String mobileNumber;
    @Column(name = "email")
    private String email;
}
