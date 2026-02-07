package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Test
    void testSendEmail(){
        emailService.sendEmail("randhirjha2009@gmail.com","Testing Java Mail Sender","Hi, Aaap Kaise hain?");

    }
}
