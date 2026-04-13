package comparator.ia.app.service.email;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import comparator.ia.app.entities.TokenTemporalyEntity;
import comparator.ia.app.entities.UserEntity;
import comparator.ia.app.repository.TokenTemporalyRepository;
import comparator.ia.app.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImp implements EmailService{
	
	private static final String MY_EMAIL = "iaRecoverPwd2026@gmail.com";
	
	private static final String SUBJECT_EMAIL = "Recuperación contraseña - Comparator IA";
	
	private static final String LINK_RECOVER_PWD = "http://localhost:4200/reset-pwd?token=";
	
	private static final int EXPIRATION_MINUTES = 15;
	
	private final TokenTemporalyRepository tokenRepo;
	
	private final UserService userService;
	
    private final JavaMailSender emailSender;
	
	EmailServiceImp(TokenTemporalyRepository tokenRepo, UserService userService, JavaMailSender emailSender){
		this.tokenRepo = tokenRepo;
		this.userService = userService;
		this.emailSender = emailSender;}

		@Override
		public boolean hasBeenSendEmail(String emailUser) {
			Optional<UserEntity> userOpt = userService.getUserByEmail(emailUser);
		    if (userOpt.isEmpty()) {
		        return false;
		    }
	
		    TokenTemporalyEntity token = new TokenTemporalyEntity();
		    token.setExpiryDate(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
		    token.setToken(UUID.randomUUID().toString());
		    token.setUser(userOpt.get());
		    tokenRepo.save(token);

	    MimeMessage message = emailSender.createMimeMessage();
	    
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        
	        helper.setFrom(MY_EMAIL);
	        helper.setTo(emailUser);
	        helper.setSubject(SUBJECT_EMAIL);

	        String htmlContent = "<!DOCTYPE html>"
	                + "<html lang='es'>"
	                + "<head><meta charset='UTF-8'></head>"
	                + "<body style='margin:0; padding:0; background-color:#F5F3EC; font-family: Arial, sans-serif;'>"
	                + "<table width='100%' cellpadding='0' cellspacing='0' style='background-color:#F5F3EC;'>"
	                + "<tr><td align='center' style='padding:40px 20px;'>"
	                + "<table width='600' cellpadding='0' cellspacing='0' style='background-color:#ffffff; border-radius:8px; overflow:hidden;'>"
	                + "<tr><td style='background-color:#212512; padding:24px; text-align:center;'>"
	                + "<h1 style='margin:0; color:#ffffff; font-size:28px;'>Comparator IA</h1></td></tr>"
	                + "<tr><td style='padding:32px; color:#1c1a18; background-color:#ede9de;'>"
	                + "<p style='font-size:16px;'>Hola <strong>" + userOpt.get().getName() + "</strong>,</p>"
	                + "<p>Para recuperarla, haz clic en el botón:</p>"
	                + "<table cellpadding='0' cellspacing='0' style='margin:32px auto;'><tr>"
	                + "<td align='center' style='background-color:#4F6814; border-radius:6px;'>"
	                + "<a href='" + LINK_RECOVER_PWD + token.getToken() + "' "
	                + "style='display:inline-block; padding:14px 28px; color:#f5f3ec; text-decoration:none; font-weight:bold;'>"
	                + "Cambia tu contraseña</a></td></tr></table>"
	                + "<p style='font-size:12px; color:#666;'>Si no fuiste tú, ignora este mensaje.</p></td></tr>"
	                + "<tr><td style='background-color:#212512; padding:20px; text-align:center;'>"
	                + "<p style='margin:0; font-size:13px; color:#ffffff;'>© 2026 COMPARATOR IA</p></td></tr>"
	                + "</table></td></tr></table></body></html>";

	        helper.setText(htmlContent, true);

	        emailSender.send(message);
	        return true;

	    } catch (MessagingException e) {
	        return false;
	    }
	}

}
