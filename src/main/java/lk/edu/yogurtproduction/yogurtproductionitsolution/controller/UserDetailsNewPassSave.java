package lk.edu.yogurtproduction.yogurtproductionitsolution.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;
import lk.edu.yogurtproduction.yogurtproductionitsolution.dto.CreteAccDto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class UserDetailsNewPassSave {

    @FXML
    private Button btnChangePass;

    @FXML
    private Label lblUserNama;

    @FXML
    private AnchorPane nextPage;

    @FXML
    private TextField txtOtp;

    @FXML
    private Label txtMailmsg;

   // UserModel userModel = new UserModel();

    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);


    String UserName;
    String Password;
    ;


    private String generatedOtp;
    private static final String SENDER_EMAIL = "mkdhyogurtfactory@gmail.com";
    private static final String SENDER_PASSWORD = "vcev juis zcnl pifa";
    private String recipientEmail;
    private boolean isVerified = false;
    private String getUserName = UserName;

    @FXML
    void btnChangePass(ActionEvent event) throws SQLException {

        String username = UserName;
        String password = Password;
        String email = GetEmail;

        CreteAccDto creteAccDto = new CreteAccDto(username, password, email);

        String enteredOtp = txtOtp.getText().trim();

        if (generatedOtp != null && generatedOtp.equals(enteredOtp)) {
            isVerified = true;

            boolean isSaved = userModel.UpdateUser(username,password);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Password Change successfully!");
                closeCurrentWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to Password Change. Please try again.");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");
        }

    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.close();
    }


    String GetEmail;
    public void setUserDetails(String userName, String password) throws SQLException {
        UserName = userName;
        Password = password;
        lblUserNama.setText(userName);
        GetEmail =  userModel.GetUserMail(UserName);
        generateOtp();
        sendOtpEmail();


    }


    private void sendOtpEmail() {

        txtMailmsg.setText("OTP email seding");


        String subject = "Your OTP for Verification";
        String body = "Dear " + UserName + ",\n\n" +
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
                Platform.runLater(() -> txtMailmsg.setText("OTP email sent successfully!"));

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
    private void generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        generatedOtp = String.valueOf(otp);
    }


    public boolean isVerified() {
        return isVerified;
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        nextPage.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetailsFrom.fxml"));
        AnchorPane load = loader.load();


        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setTitle("Use Details");

        nextPage.getChildren().add(load);
    }

}
