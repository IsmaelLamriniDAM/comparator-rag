package comparator.ia.app.entities;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "brands")
public class BrandEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idBrand;
	
	@Column(name = "commun_name", unique = true)
	private String communName;
	
	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	private List<ModelEntity> models;
	
	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
	private List<BrandAliasEntity> alias;

	public List<ModelEntity> getModels() {
		return models;
	}

	public void setModels(List<ModelEntity> models) {
		this.models = models;
	}

	public String getCommunName() {
		return communName;
	}

	public void setCommunName(String communName) {
		this.communName = communName;
	}

}
