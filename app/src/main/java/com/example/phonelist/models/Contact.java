package com.example.phonelist.models;

import java.util.ArrayList;

public class Contact {

    public static ArrayList<Contact> contacts = new ArrayList<>();

    private Integer id;
    private String name;
    private String address;
    private String telephone;
    private String cellphone;

    public Contact(Integer id, String name, String address, String telephone, String cellphone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.cellphone = cellphone;
    }

    public Contact(String name, String address, String telephone, String cellphone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.cellphone = cellphone;
    }

    public Contact() {

    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cellphone='" + cellphone + '\'' +
                '}';
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() { return cellphone; }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    static public void updateContactByIndex(Integer index, Contact newContact) {
        if (index < contacts.size()) {
            Contact oldContact = contacts.get(index);
            oldContact.setId(newContact.getId());
            oldContact.setName(newContact.getName());
            oldContact.setAddress(newContact.getAddress());
            oldContact.setTelephone(newContact.getTelephone());
            oldContact.setCellphone(newContact.getCellphone());
        }
    }
}
