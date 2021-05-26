package request_for_help.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import user.profile.User;

@Entity
@Table(name = "requests")
public class RequestForHelp implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User authorUser;
	private String type;
	private String name;
	private String region;
	private String district;
	private String city;
	private String street;
	@Column(name = "house_number")
	private String houseNumber;
	@Column(name = "creation_date")
	private Calendar creationDate;
	@Column(name = "start_date")
	private Calendar startDate;
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "participants", joinColumns = { @JoinColumn(name = "id_request") }, inverseJoinColumns = {
			@JoinColumn(name = "id_user") })
	private Set<User> participants = new HashSet<User>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "photo_reports_in_request", joinColumns = {
			@JoinColumn(name = "id_request") }, inverseJoinColumns = { @JoinColumn(name = "id_photo_report") })
	private Set<PhotoReport> photoReports = new HashSet<PhotoReport>();

	public RequestForHelp() {

	}

	public Set<PhotoReport> getPhotoReports() {
		return photoReports;
	}

	public void setPhotoReports(Set<PhotoReport> photoReports) {
		this.photoReports = photoReports;
	}

	public User getAuthorUser() {
		return authorUser;
	}

	public void setAuthorUser(User authorUser) {
		this.authorUser = authorUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<User> participants) {
		this.participants = participants;
	}

	public int getId() {
		return id;
	}

}
