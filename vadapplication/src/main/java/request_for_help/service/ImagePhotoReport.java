package request_for_help.service;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "list_photos")
public class ImagePhotoReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "id_photo_report")
	private int idPhotoReport;
	@Column(name = "photo")
	private byte[] image;

	public int getIdPhotoReport() {
		return idPhotoReport;
	}

	public int getId() {
		return id;
	}

	public void setIdPhotoReport(int idPhotoReport) {
		this.idPhotoReport = idPhotoReport;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
