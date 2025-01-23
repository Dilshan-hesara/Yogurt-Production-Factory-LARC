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
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.BOFactroy;
import lk.edu.yogurtproduction.yogurtproductionitsolution.bo.custom.UserBO;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class FogetPassWordVerfyController {

    @FXML
    private AnchorPane creatAcc;

    @FXML
    private Label txtMail;

    @FXML
    private Label txtMailmsg;

    @FXML
    private TextField txtOtp;

    @FXML
    private Label txtUser;


    UserBO userModel = (UserBO) BOFactroy.getInstance().getBO(BOFactroy.BOType.USER);

  //  UserModel userModel = new UserModel();

    private String generatedOtp;
    private static final String SENDER_EMAIL = "mkdhyogurtfactory@gmail.com";
    private static final String SENDER_PASSWORD = "vcev juis zcnl pifa";
    private boolean isVerified = false;

@FXML
    void VeffiMail(ActionEvent event) throws IOException, SQLException {


    String enteredOtp = txtOtp.getText().trim();
    if (generatedOtp != null && generatedOtp.equals(enteredOtp)) {
        isVerified = true;

        creatAcc.getChildren().clear();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogetPasWordSave.fxml"));
        AnchorPane load = loader.load();

        FogetPassWordSave passUser = loader.getController();
        passUser.userName(userNmae);


        Stage stage = (Stage) creatAcc.getScene().getWindow();
        stage.setTitle("save password");

        creatAcc.getChildren().add(load);

    } else {
        showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again.");

    }

    }




    String userNmae;
    String GetMail;

    public void setUserName(String userName) throws SQLException {
        this.userNmae = userName;
         GetMail=  userModel.GetUserMail(userNmae);
        txtUser.setText(userNmae);

          generateOtp();
        sendOtpEmail();
    }

    private void sendOtpEmail() {

        txtMailmsg.setText("OTP email seding");


        String subject = "Your OTP for Verification";
        String body = "Dear " + userNmae + ",\n\n" +
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
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(GetMail));
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
    void VeffiMailExit(ActionEvent event) throws IOException {

        creatAcc.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FogetPassWord.fxml"));
        AnchorPane load = loader.load();

        Stage stage = (Stage) creatAcc.getScene().getWindow();  // Get the current stage
        stage.setTitle("Foget Password");

        creatAcc.getChildren().add(load);

    }

}

