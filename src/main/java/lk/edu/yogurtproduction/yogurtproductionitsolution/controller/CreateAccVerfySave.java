package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;
import lk.edu.yogurtproduction.yogurtproductionitsolution.model.UserModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class CreateAccVerfySave {

    @FXML
    private AnchorPane creatAcc;

    @FXML
    private Label txtMail;

    @FXML
    private Label txtMailSuss;

    @FXML
    private Label txtMailn;

    @FXML
    private TextField txtOtp;

    @FXML
    private Label txtUser;



    String GetUserName;
    String GetPassword;
    String GetEmail;

    private String generatedOtp;
    private static final String SENDER_EMAIL = "mkdhyogurtfactory@gmail.com";
    private static final String SENDER_PASSWORD = "vcev juis zcnl pifa";
    private String recipientEmail = GetEmail;
    private boolean isVerified = false;
    private String getUserName = GetUserName;


    UserModel userModel = new UserModel();
    @FXML
    void VeffiMail(ActionEvent event) throws SQLException {
        String username = GetUserName;
        String password = GetPassword;
        String email = GetEmail;

        CreteAccDto creteAccDto = new CreteAccDto(username, password, email);

        String enteredOtp = txtOtp.getText().trim();
        if (generatedOtp != null && generatedOtp.equals(enteredOtp)) {
            isVerified = true;
            showAlert(Alert.AlertType.INFORMATION, "Account saved successfully!");

            boolean isSaved = userModel.creatUser(creteAccDto);

            if (isSaved) {
                closeCurrentWindow();
                System.out.println("labbl");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to save account. Please try again.");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");
        }
    }
    private void closeCurrentWindow() {
        Stage stage = (Stage) creatAcc.getScene().getWindow();
        stage.close();
    }


    public boolean isVerified() {
        return isVerified;
    }


    private void sendOtpEmail() {

        txtMail.setText("OTP email seding");


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
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(GetEmail));
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

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void sendAccDetails(CreteAccDto creteAccDto) {



        GetUserName =creteAccDto.getUsername();
        GetPassword =creteAccDto.getPassword();
        GetEmail =creteAccDto.getEmail();

        txtMailn.setText(GetEmail);
        txtUser.setText(GetUserName);

        generateOtp();
        sendOtpEmail();


    }

    private void generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        generatedOtp = String.valueOf(otp);
    }


    @FXML
    void VeffiMailExit(ActionEvent event) throws IOException {



        creatAcc.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateAccGetVal.fxml"));
        AnchorPane load = loader.load();


        Stage stage = (Stage) creatAcc.getScene().getWindow();
        stage.setTitle("Create Account ");

        creatAcc.getChildren().add(load);

    }
}
