package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.edu.yogurtproduction.yogurtproductionitsolution.view.tdm.SuplierTM;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Pattern;

public class SendMailSupierController {

    @FXML
    private TextArea txtBody;

    @FXML
    private Label txtName;

    @FXML
    private TextField txtSubject;
    private SuplierTM selctSuplier;

    public void sendMailData(SuplierTM selectSup) {
        this.selctSuplier = selectSup;
        txtName.setText(selectSup.getSupEmail());

    }

    @FXML
    void btnsend(ActionEvent event) {

        final String FROM = "mkdhyogurtfactory@gmail.com";
        final String PASSWORD = "vcev juis zcnl pifa";

        String subject = txtSubject.getText();
        String body = txtBody.getText();
        String recipientEmail = selctSuplier.getSupEmail();

        if (subject.isEmpty() || body.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Subject and body must not be empty!");
            return;
        }

        if (!isValidEmail(recipientEmail)) {
            showAlert(Alert.AlertType.WARNING, "Invalid email address!");
            return;
        }


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });

        new Thread(() -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);

                Platform.runLater(() -> showAlert(Alert.AlertType.INFORMATION, "Email sent successfully!"));

            } catch (MessagingException e) {
                e.printStackTrace();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error sending email: " + e.getMessage()));
            }
        }).start();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Platform.runLater(() -> new Alert(alertType, message).show());
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
