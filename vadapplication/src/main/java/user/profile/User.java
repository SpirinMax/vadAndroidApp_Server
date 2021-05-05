package user.profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import request_for_help.service.RequestForHelp;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	private String patronymic;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;
	
	private String aboutuser;
	private String phone;
	private byte[] photo;
	private int helpcounter;
	private String region;
	private String district;
	private String city;
	
//	@OneToMany (mappedBy="authorUser")
//	private List<RequestForHelp> listRequestsForHelp;

	public User(String userfirstname,String userlastname,String useremail,String userpassword) {
		this.firstname=userfirstname;
		this.lastname=userlastname;
		this.email=useremail;
		this.password=userpassword;
	}
	
	public User () {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAboutuser() {
		return aboutuser;
	}

	public void setAboutuser(String aboutuser) {
		this.aboutuser = aboutuser;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public int getHelpcounter() {
		return helpcounter;
	}

	public void setHelpcounter(int helpcounter) {
		this.helpcounter = helpcounter;
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

//	public List<RequestForHelp> getListRequestsForHelp() {
//		return listRequestsForHelp;
//	}
//
//	public void setListRequestsForHelp(List<RequestForHelp> listRequestsForHelp) {
//		this.listRequestsForHelp = listRequestsForHelp;
//	}
//	
//	public void addItem(RequestForHelp item) {
//        listRequestsForHelp.add(item);
//        item.setAuthorUser(this);
//    }

}
