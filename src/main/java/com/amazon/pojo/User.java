package com.amazon.pojo;

import java.util.List;

import com.amazon.keys.APIKey;

public class User {

	private APIKey key;
	private String name;
	private String firstname;
	private List<Command> command;

	public User(APIKey key, String name, String firstname) {
		super();
		this.key = key;
		this.name = name;
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return "User [key=" + key + ", name=" + name + ", firstname=" + firstname + ", command=" + command + "]";
	}

	public APIKey getKey() {
		return key;
	}

	public void setKey(APIKey key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public List<Command> getCommand() {
		return command;
	}

	public void setCommand(List<Command> command) {
		this.command = command;
	}

}
