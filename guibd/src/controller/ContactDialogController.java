package controller;

import domain.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactDialogController implements Initializable {
    @FXML
    private TextField fullname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField firstname;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox<String> blacklist;

    private Stage dialogStage;
    private Contact contact;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает контакт, который мы редактируем в этом окне
    public void setContact(Contact contact) {
        this.contact = contact;

        if (contact.getFullName().length() != 0) {
            fullname.setText(contact.getFullName());
        }
        if (contact.getLastName().length() != 0) {
            lastname.setText(contact.getLastName());
        }
        if (contact.getFirstName().length() != 0) {
            firstname.setText(contact.getFirstName());
        }
        if (contact.isInBlackList()) {
            blacklist.setValue("True");
        } else {
            blacklist.setValue("False");
        }
    }

    // Проверка нажатия кнопки OK
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void okAction(ActionEvent actionEvent) {
        contact.setFullName(fullname.getText());
        contact.setLastName(lastname.getText());
        contact.setFirstName(firstname.getText());
        contact.setInBlackList(Boolean.valueOf(blacklist.getValue()));

        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
