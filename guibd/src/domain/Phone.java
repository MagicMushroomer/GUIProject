/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author mushroomer
 */
public class Phone {
    private long id;                // id
    private Contact contact;        // Владелец телефона
    private PhoneType phoneType;    // Тип телефона
    private String phoneNumber;     // Номер телефона

    public Phone(long id, Contact contact, PhoneType phoneType, String phoneNumber) {
        this.id = id;
        this.contact = contact;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
    }

    public Phone(Contact contact, PhoneType phoneType, String phoneNumber) {
        this.contact = contact;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
    }

    public Phone() {
        this.contact = null;
        this.phoneType = null;
        this.phoneNumber = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
}
