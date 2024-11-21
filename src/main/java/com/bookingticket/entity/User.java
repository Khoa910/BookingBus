package com.bookingticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@JsonIgnore
	private String password;

	private String full_name;

	private String phone_number;

	private String email;

	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role", referencedColumnName = "id")
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ticket> tickets = new HashSet<>();
}
