package org.example.project_pfe.config;

import lombok.RequiredArgsConstructor;
import org.example.project_pfe.model.Admin;
import org.example.project_pfe.model.Role;
import org.example.project_pfe.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (utilisateurRepository.findByEmail("admin@pfe.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setNom("Admin");
            admin.setPrenom("System");
            admin.setEmail("admin@pfe.com");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            utilisateurRepository.save(admin);
            System.out.println("✅ Admin créé avec succès");
        }
    }
}