package com.wipro.cabbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer extends AbstractUser {
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int customerId;
}
