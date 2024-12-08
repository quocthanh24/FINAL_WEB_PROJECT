package thanhluu.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;

    // Gửi email với mã OTP
    public void sendOtpEmail(String toEmail, String otpCode) throws MessagingException {
        String subject = "OTP Của Bạn:";
        String content = "Use the following OTP code to verify your account: " + otpCode;

        sendEmail(toEmail, subject, content);
    }

    // Gửi email khi yêu cầu quên mật khẩu
    public void sendPasswordResetEmail(String toEmail, String otpCode) throws MessagingException {
        String subject = "Phản Hồi Yêu Cầu Reset Mật Khẩu";
        String content = "OTP Của Bạn: \n" + otpCode;

        sendEmail(toEmail, subject, content);
    }

    // Phương thức gửi email chung
    private void sendEmail(String toEmail, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content);

        mailSender.send(message);
    }
}
