package net.javaguides.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.enums.ActivityType;

@Entity
@Getter
@Setter
@Table(name="users")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(unique = true, length = 50)
	private String email;
	
	@NotNull
	@Column(unique = true, length = 20)
	private String userName;

	@NotNull
	@Column(length = 64)
	private String passWord;
}
