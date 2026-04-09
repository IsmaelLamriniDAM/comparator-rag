package comparator.ia.app.util.auth;

import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

public interface SessionManager {
	static final String PROFILE_KEY = "SESSION_PROFILE";

	void createSession(HttpSession session, UUID id);

	Optional<UUID> getKeySession(HttpSession session);

	boolean checkSession(HttpSession session);

	void deleteSession(HttpSession session);
	
	long encodeId (HttpSession session, long id);
	
	long decodeId (HttpSession session, long id);
}
