 
            package in.thinkedge.service.Impl;

            import jakarta.mail.internet.MimeMessage;
            import org.springframework.beans.factory.annotation.Autowired;
            import org.springframework.core.io.ClassPathResource;
            import org.springframework.mail.javamail.JavaMailSender;
            import org.springframework.mail.javamail.MimeMessageHelper;
            import org.springframework.scheduling.annotation.Async;
            import org.springframework.stereotype.Service;

            @Service
            public class EmailService {

                @Autowired
                private JavaMailSender javaMailSender;

                @Async
                public void sendEmail(String to, String subject, String body) {
                    try {
                        // Create MimeMessage object
                        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                        // Create MimeMessageHelper with inline attachment capability
                        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                        helper.setFrom("thinkedge4@gmail.com");
                        helper.setTo(to);
                        helper.setSubject(subject);

                        // HTML content with logo reference
                        String htmlContent = "<html><body>"
                                + "<img src='cid:logoImage' alt='Thinkedge Logo' style='width:120px;'/><br/><br/>"
                                + "<p>" + body.replace("\n", "<br/>") + "</p>"
                                + "</body></html>";

                        helper.setText(htmlContent, true);

                        // Correct path for the logo image in the resources folder
                        ClassPathResource resource = new ClassPathResource("static/logo.png");
                        helper.addInline("logoImage", resource);

                        // Send the email
                        javaMailSender.send(mimeMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
