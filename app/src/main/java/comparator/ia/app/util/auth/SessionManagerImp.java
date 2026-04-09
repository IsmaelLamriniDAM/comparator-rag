package comparator.ia.app.util.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionManagerImp implements SessionManager {

	@Override
	public void createSession(HttpSession session, UUID id) {
		session.setAttribute(PROFILE_KEY, id);
		session.setMaxInactiveInterval(1800);
	}

	@Override
	public Optional<UUID> getKeySession(HttpSession session) {
		return Optional.ofNullable((UUID) session.getAttribute(PROFILE_KEY));
	}

	@Override
	public boolean checkSession(HttpSession session) {
		return getKeySession(session).isPresent();
	}

	@Override
	public void deleteSession(HttpSession session) {
		session.invalidate();
	}
	

	@Override
	public long encodeId(HttpSession session, long id) {
		long sessionId = getKeySession(session).get().hashCode();
		return id * sessionId;
	}

	@Override
	public long decodeId(HttpSession session, long id) {	
		long sessionId = getKeySession(session).get().hashCode();
		return id / sessionId;
	}

}
