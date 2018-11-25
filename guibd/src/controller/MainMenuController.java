package controller;

import dao.ContactDAOImpl;
import dao.GeneticDAO;
import dao.PhoneDAOImpl;
import dao.PhoneTypeDAOImpl;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private TabPane mainPane;
    @FXML
    private Tab contactsTab;
    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, String> fullnameColumn;
    @FXML
    private TableColumn<Contact, String> firstnameColumn;
    @FXML
    private TableColumn<Contact, String> lastnameColumn;
    @FXML
    private TableColumn<Contact, Boolean> blackColumn;
    @FXML
    private TableColumn<Contact, Integer> idContactColumn;
    @FXML
    private Button editContact;
    @FXML
    private Button deleteContact;
    @FXML
    private Button addContact;
    @FXML
    private Tab phoneTab;
    @FXML
    private TableView<Phone> phoneTable;
    @FXML
    private TableColumn<Phone, Integer> idPhoneColumn;
    @FXML
    private TableColumn<Phone, String> contactColumn;
    @FXML
    private TableColumn<Phone, String> typeColumn;
    @FXML
    private TableColumn<Phone, String> phonenumberColumn;
    @FXML
    private Button editPhone;
    @FXML
    private Button deletePhone;
    @FXML
    private Button addPhone;
    @FXML
    private Tab dictTab;
    @FXML
    private TableView<PhoneType> dictTable;
    @FXML
    private TableColumn<PhoneType, Integer> idColumn;
    @FXML
    private TableColumn<PhoneType, String> typeDictColumn;
    @FXML
    private TableColumn<PhoneType, Integer> codeColumn;
    @FXML
    private TableColumn<PhoneType, String> numberColumn;
    @FXML
    private Button addDict;
    @FXML
    private Button deleteDict;
    @FXML
    private Button editDict;
//lists for tables
    private ObservableList<Contact> contacts;
    private ObservableList<Phone> phones;
    private ObservableList<PhoneType> phoneTypes;

    private void setPhoneTypeTable() {
        GeneticDAO phoneTypeDAO = new PhoneTypeDAOImpl();
        phoneTypes = FXCollections.observableArrayList(phoneTypeDAO.getAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        typeDictColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dictTable.setItems(phoneTypes);
    }
    private void setPhoneTable() {
        GeneticDAO phoneDAO = new PhoneDAOImpl();
        phones = FXCollections.observableArrayList(phoneDAO.getAll());
        idPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("phoneType"));
        phonenumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneTable.setItems(phones);
    }
    private void setContactTable() {
        GeneticDAO contactDAO = new ContactDAOImpl();
        contacts = FXCollections.observableArrayList(contactDAO.getAll());
        idContactColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        blackColumn.setCellValueFactory(new PropertyValueFactory<>("inBlackList"));
        contactTable.setItems(contacts);
    }

    // Отображение окна редактирования
    public boolean showContactEditDialog(Contact contact) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ContactDialogController.class.getResource("/views/ContactDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Contact");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            ContactDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContact(contact);

            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Отображение окна редактирования
    public boolean showPhoneEditDialog(Phone phone) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhoneDialogController.class.getResource("/views/PhoneDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Phone");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            PhoneDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPhone(phone);

            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Отображение окна редактирования
    public boolean showPhoneTypeEditDialog(PhoneType phoneType) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PhoneTypeDialogController.class.getResource("/views/PhoneTypeDialog.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Phone Type");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            PhoneTypeDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPhoneType(phoneType);

            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    private void editContactAction(ActionEvent actionEvent) {
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showContactEditDialog(selectedContact);
            if (okClicked) {
                GeneticDAO contactDao = new ContactDAOImpl();
                contactDao.update(selectedContact);
                contacts.set(selectedIndex, selectedContact);
            }
        }
    }
    @FXML
    private void deleteContactAction(ActionEvent actionEvent) {
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();
        int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GeneticDAO contactDao = new ContactDAOImpl();
            selectedContact = (Contact) contactDao.delete(selectedContact);
            if (selectedContact != null) {
                contactTable.getItems().remove(selectedIndex);
            }
        }
    }
    @FXML
    private void addContactAction(ActionEvent actionEvent) {
        Contact tempContact = new Contact();
        boolean okClicked = showContactEditDialog(tempContact);

        if (okClicked) {
            GeneticDAO contactDao = new ContactDAOImpl();
            tempContact = (Contact) contactDao.create(tempContact);
            contacts.add(tempContact);
        }
    }
    @FXML
    private void addPhoneAction(ActionEvent actionEvent) {
        Phone tempPhone = new Phone();
        boolean okClicked = showPhoneEditDialog(tempPhone);

        if (okClicked) {
            GeneticDAO phoneDAO = new PhoneDAOImpl();
            tempPhone = (Phone) phoneDAO.create(tempPhone);
            phones.add(tempPhone);
        }
    }
    @FXML
    private void deletePhoneAction(ActionEvent actionEvent) {
        Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();
        int selectedIndex = phoneTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GeneticDAO phoneDAO = new PhoneDAOImpl();
            selectedPhone = (Phone) phoneDAO.delete(selectedPhone);
            if (selectedPhone != null) {
                phoneTable.getItems().remove(selectedIndex);
            }
        }
    }
    @FXML
    private void editPhoneAction(ActionEvent actionEvent) {
        Phone selectedPhone = phoneTable.getSelectionModel().getSelectedItem();
        int selectedIndex = phoneTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showPhoneEditDialog(selectedPhone);
            if (okClicked) {
                GeneticDAO phonesDAO = new PhoneDAOImpl();
                phonesDAO.update(selectedPhone);
                phones.set(selectedIndex, selectedPhone);
            }
        }
    }
    @FXML
    private void addDictAction(ActionEvent actionEvent) {
        PhoneType tempPhoneType = new PhoneType();
        boolean okClicked = showPhoneTypeEditDialog(tempPhoneType);

        if (okClicked) {
            GeneticDAO phoneTypeDAO = new PhoneTypeDAOImpl();
            tempPhoneType = (PhoneType) phoneTypeDAO.create(tempPhoneType);
            phoneTypes.add(tempPhoneType);
        }
    }
    @FXML
    private void deleteDictAction(ActionEvent actionEvent) {
        PhoneType selectedPhoneType = dictTable.getSelectionModel().getSelectedItem();
        int selectedIndex = phoneTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GeneticDAO phoneTypeDAO = new PhoneTypeDAOImpl();
            selectedPhoneType = (PhoneType) phoneTypeDAO.delete(selectedPhoneType);
            if (selectedPhoneType != null) {
                dictTable.getItems().remove(selectedIndex);
            }
        }
    }
    @FXML
    private void editDictAction(ActionEvent actionEvent) {
        PhoneType selectedPhoneType = dictTable.getSelectionModel().getSelectedItem();
        int selectedIndex = dictTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showPhoneTypeEditDialog(selectedPhoneType);
            if (okClicked) {
                GeneticDAO phoneTypesDAO = new PhoneTypeDAOImpl();
                phoneTypesDAO.update(selectedPhoneType);
                phoneTypes.set(selectedIndex, selectedPhoneType);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setContactTable();
        setPhoneTable();
        setPhoneTypeTable();
    }
}
