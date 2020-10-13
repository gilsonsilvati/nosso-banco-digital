package br.com.bancodigital.api.email.sending;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(Message message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(message.getRemetente());
		simpleMailMessage.setTo(message.getDestinatarios().toArray(new String[message.getDestinatarios().size()]));
		simpleMailMessage.setSubject(message.getAssunto());
		simpleMailMessage.setText(message.getCorpo());
		
		javaMailSender.send(simpleMailMessage);
	}

}
