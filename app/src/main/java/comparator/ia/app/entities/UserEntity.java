package comparator.ia.app.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email",unique = true, nullable = false)
	private String email;
	
	@OneToOne(mappedBy = "user")
	private UserPasswordEntity password;
	
	@Column(name = "phone",unique = true, nullable = false)
	private String phone;
	
	@CreationTimestamp
	@Column(name = "Created_at", nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "Updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@Column(name = "comparisons", nullable = false)
	private Integer numComparisons;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<HistoryIaEntity> history;
	
	public List<HistoryIaEntity> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryIaEntity> history) {
		this.history = history;
	}

	public Integer getNumComparisons() {
		return numComparisons;
	}

	public void setNumComparisons(Integer numComparisons) {
		this.numComparisons = numComparisons;
	}

	public UUID getId() {
		return id;
	}

	public UserPasswordEntity getPassword() {
		return password;
	}

	public void setPassword(UserPasswordEntity password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
