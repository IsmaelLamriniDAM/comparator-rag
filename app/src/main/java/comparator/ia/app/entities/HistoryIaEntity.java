package comparator.ia.app.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "history_ia")
public class HistoryIaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID idHistory;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "id_share", nullable = false)
	private String idShare;
	
	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	private List<MessageIaEntity> messagesIA;
	
	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	private List<MessageUserEntity> messagesUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private UserEntity user;
	

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdShare() {
		return idShare;
	}

	public void setIdShare(String idShare) {
		this.idShare = idShare;
	}

	public List<MessageIaEntity> getMessagesIA() {
		return messagesIA;
	}

	public void setMessagesIA(List<MessageIaEntity> messagesIA) {
		this.messagesIA = messagesIA;
	}

	public List<MessageUserEntity> getMessagesUser() {
		return messagesUser;
	}

	public void setMessagesUser(List<MessageUserEntity> messagesUser) {
		this.messagesUser = messagesUser;
	}
	
}
