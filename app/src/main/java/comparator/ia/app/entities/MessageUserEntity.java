package comparator.ia.app.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import comparator.ia.app.enums.RolChat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_user")
public class MessageUserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_message_user")
	private UUID idMessageUser;
	
	@Column(name = "message", nullable = false, length = 10000)
	private String message;
	
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "rol_chat", nullable = false)
	private RolChat rol;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_history")
	private HistoryIaEntity history;

	public RolChat getRol() {
		return rol;
	}

	public void setRol(RolChat rol) {
		this.rol = rol;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public HistoryIaEntity getHistory() {
		return history;
	}

	public void setHistory(HistoryIaEntity history) {
		this.history = history;
	}

	
	
}
