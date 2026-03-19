package org.example.project_pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    /**
     * Sends a welcome email to a newly created user (student or teacher).
     * Annotated @Async so it does NOT block the HTTP response — the user is
     * saved and the API responds immediately; the email goes out in the background.
     *
     * @param to       recipient email address
     * @param fullName first + last name  (e.g. "John Doe")
     * @param password plain-text password chosen by the admin
     * @param role     "Student" | "Teacher" (display only)
     */
    @Async
    public void sendWelcomeEmail(String to, String fullName, String password, String role) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject("Welcome to SchoolBridge — Your Account Details");
            message.setText(buildEmailBody(fullName, to, password, role));
            mailSender.send(message);
        } catch (Exception e) {
            // Log but never crash the main request if email fails
            System.err.println("[EmailService] Failed to send welcome email to " + to + ": " + e.getMessage());
        }
    }

    private String buildEmailBody(String fullName, String email, String password, String role) {
        return String.join("\n",
                "Hello " + fullName + ",",
                "",
                "Your " + role + " account has been created on SchoolHub.",
                "",
                "──────────────────────────────",
                "  Your login credentials",
                "──────────────────────────────",
                "  Email    : " + email,
                "  Password : " + password,
                "  Role     : " + role,
                "──────────────────────────────",
                "",
                "Please log in at your earliest convenience and change your password.",
                "",
                "If you did not expect this email, please contact your institution's admin.",
                "",
                "— SchoolBridge System"
        );
    }
}