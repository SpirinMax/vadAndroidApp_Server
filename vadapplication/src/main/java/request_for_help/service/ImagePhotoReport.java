package request_for_help.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "list_photos")
public class ImagePhotoReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number_photo")
	private int numberPhoto;
	@Column(name = "id_photo_report")
	private int idPhotoReport;
	@Column(name = "photo")
	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getNumberPhoto() {
		return numberPhoto;
	}

	public int getIdPhotoReport() {
		return idPhotoReport;
	}

}
