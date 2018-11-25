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
public class PhoneType {
    private long id;            // id
    private long code;          // Код типа
    private String shortName;   // Сокращенное название типа
    private String fullName;    // Полное название типа

    public PhoneType(long id, long code, String shortName, String fullName) {
        this.id = id;
        this.code = code;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public PhoneType(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public PhoneType() {
        this.id = 0;
        this.code = 0;
        this.shortName = "";
        this.fullName = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
