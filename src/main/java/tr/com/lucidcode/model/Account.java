package tr.com.lucidcode.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class Account {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	private String username;

	private String name;
	private String surname;
	
	@NotNull
	@Pattern(regexp="(^$|[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2})")
	private String phoneNumber;

	
	public Account() {
		super();
	}

	public Account(String username, String name, String surname, String phoneNumber) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
