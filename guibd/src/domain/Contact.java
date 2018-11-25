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
public class Contact {
    private long id;                // id
    private String fullName;        // ФИО
    private String lastName;        // Фамилия
    private String firstName;       // Имя
    private boolean inBlackList;    // Статус наличия в черном списке

    public Contact(String fullName, String lastName, String firstName, boolean inBlackList) {
        this.fullName = fullName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.inBlackList = inBlackList;
    }
    
    public Contact(long id, String fullName, String lastName, String firstName, boolean inBlackList) {
        this.id = id;
        this.fullName = fullName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.inBlackList = inBlackList;
    }

    public Contact() {
        this.id = 0;
        this.fullName = "";
        this.lastName = "";
        this.firstName = "";
        this.inBlackList = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isInBlackList() {
        return inBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        this.inBlackList = inBlackList;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
