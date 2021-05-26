package request_for_help.service;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import user.profile.User;

@Entity
@Table(name = "photo_reports")
public class PhotoReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User authorReport;
	private String name;
	private String description;
	@OneToMany (fetch = FetchType.EAGER)
	@JoinColumn(name = "id_photo_report")
	private Set<ImagePhotoReport> images = new HashSet<ImagePhotoReport>();
	@Column(name = "creation_date")
	private Calendar creationDate;

	public User getAuthorReport() {
		return authorReport;
	}

	public void setAuthorReport(User authorReport) {
		this.authorReport = authorReport;
	}

	public Set<ImagePhotoReport> getImages() {
		return images;
	}

	public void setImages(Set<ImagePhotoReport> images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

}
