package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.UserDto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class VerfyMailController {

    @FXML
    private TextField txtOtp;


    @FXML
    private Label txtMail;

    @FXML
    private Label txtUser;



    @FXML
    private Label txtMailSuss;

    private String generatedOtp;
    private static final String SENDER_EMAIL = "mkdhyogurtfactory@gmail.com";
    private static final String SENDER_PASSWORD = "vcev juis zcnl pifa";
    private String recipientEmail;
    private boolean isVerified = false;
    private String getUserName;


    public void setUserDetails(UserDto user) {
        if (user != null) {
            this.recipientEmail = user.getPassword();
            txtMail.setText(user.getPassword());
            getUserName = user.getUsername();
            txtUser.setText(getUserName);
            generateOtp();
            sendOtpEmail();


        }
    }


    private void generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        generatedOtp = String.valueOf(otp);
    }


    private void sendOtpEmail() {

        txtMail.setText("OTP email seding");

        if (recipientEmail == null || !isValidEmail(recipientEmail)) {
            showAlert(Alert.AlertType.WARNING, "Invalid email address! Cannot send OTP.");
            return;
        }

        String subject = "Your OTP for Verification";
        String body = "Dear " + getUserName + ",\n\n" +
                "Your OTP is: " + generatedOtp + "\n\n" +
                "Please use this to verify your email.\n\n" +
                "Regards,\n" +
                "MKD Yogurt Factory\n\n" +
                "Developed by Dilshan Hesara";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        new Thread(() -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(SENDER_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);
                Platform.runLater(() -> txtMail.setText("OTP email sent successfully!"));

            } catch (MessagingException e) {
                e.printStackTrace();
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error sending email: " + e.getMessage()));
            }
        }).start();
    }



    public boolean isVerified() {
        return isVerified;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void closeWindow() {
        Stage stage = (Stage) txtOtp.getScene().getWindow();
        stage.close();
    }


    public void VeffiMail(ActionEvent actionEvent) {

        String otpVli = txtOtp.getText();

        if (otpVli.matches("[0-9]+")) {

        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");
            return;
        }

        String enteredOtp = txtOtp.getText().trim();
        if (generatedOtp != null && generatedOtp.equals(enteredOtp)) {
            isVerified = true;
            showAlert(Alert.AlertType.INFORMATION, "OTP verified successfully!");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");
        }
    }

    public void VeffiMailExit(ActionEvent actionEvent) {
  closeWindow();
    }
}
