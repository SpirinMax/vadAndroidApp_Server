package request_for_help.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import user.profile.User;

public class RequestForHelp {
	private User author;
	private String name;
	private String city;
	private ArrayList<User> listParticipants;
	private LocalDateTime startDate;
	private String description;
	
	public RequestForHelp (User author) {
		this.author = author;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ArrayList<User> getListParticipants() {
		return listParticipants;
	}

	public void setListParticipants(ArrayList<User> listParticipants) {
		this.listParticipants = listParticipants;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
