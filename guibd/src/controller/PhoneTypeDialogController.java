package controller;

import domain.Contact;
import domain.PhoneType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PhoneTypeDialogController {
    @FXML
    private TextField code;
    @FXML
    private TextField fullname;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField shortname;

    private Stage dialogStage;
    private PhoneType phoneType;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает тип телефона, который мы редактируем в этом окне
    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;

        if (phoneType.getCode() != 0) {
            code.setText(Long.toString(phoneType.getCode()));
        } else {
            code.setText("");
        }
        if (phoneType.getShortName().length() != 0) {
            shortname.setText(phoneType.getShortName());
        } else {
            shortname.setText("");
        }
        if (phoneType.getFullName().length() != 0) {
            fullname.setText(phoneType.getFullName());
        } else {
            fullname.setText("");
        }
    }

    // Проверка нажатия кнопки OK
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void okAction(ActionEvent actionEvent) {
        phoneType.setCode(Long.parseLong(code.getText()));
        phoneType.setShortName(shortname.getText());
        phoneType.setFullName(fullname.getText());

        okClicked = true;
        dialogStage.close();
    }
    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
