package controller;

import dao.ContactDAOImpl;
import dao.GeneticDAO;
import dao.PhoneTypeDAOImpl;
import domain.Contact;
import domain.Phone;
import domain.PhoneType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PhoneDialogController implements Initializable {
    @FXML
    private ChoiceBox<Contact> fullname;
    @FXML
    private TextField number;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox<PhoneType> type;

    private Stage dialogStage;
    private Phone phone;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает телефон, который мы редактируем в этом окне
    public void setPhone(Phone phone) {
        this.phone = phone;

        if (phone.getContact() != null) {
            fullname.setValue(phone.getContact());
        }
        if (phone.getPhoneType() != null) {
            type.setValue(phone.getPhoneType());
        }
        if (phone.getPhoneNumber().length() != 0) {
            number.setText(phone.getPhoneNumber());
        }
    }

    public void setChoiceBoxes() {
        GeneticDAO<PhoneType> phoneTypeDAO = new PhoneTypeDAOImpl();
        GeneticDAO<Contact> contactDAO = new ContactDAOImpl();

        ObservableList<Contact> contacts = FXCollections.observableArrayList(contactDAO.getAll());
        fullname.setItems(contacts);

        ObservableList<PhoneType> phoneTypes = FXCollections.observableArrayList(phoneTypeDAO.getAll());
        type.setItems(phoneTypes);
    }

    // Проверка нажатия кнопки OK
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void okAction(ActionEvent actionEvent) {
        phone.setContact(fullname.getValue());
        phone.setPhoneType(type.getValue());
        phone.setPhoneNumber(number.getText());

        okClicked = true;
        dialogStage.close();
    }
    @FXML
    private void cancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxes();
    }
}
