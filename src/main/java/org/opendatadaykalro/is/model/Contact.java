package org.opendatadaykalro.is.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class Contact {
    private final static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    private UUID id;
    private String c_o;
    private String location;
    private String address;
    private String telephone;
    private String e_mail;
    private String ref_name;
    private UUID cid;
    private String other;

    public Contact(UUID id, String c_o, String location, String address, String telephone, String e_mail, String ref_name, UUID cid, String other) {
        this.id = id;
        this.c_o = c_o;
        this.location = location;
        this.address = address;
        this.telephone = telephone;
        this.e_mail = e_mail;
        this.ref_name = ref_name;
        this.cid = cid;
        this.other = other;
    }
    public Contact createContactFromResultSet(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        java.util.UUID id = (java.util.UUID) resultSet.getObject(1);
        java.util.UUID cid = (java.util.UUID) resultSet.getObject(8);
        contact.setId(id);
        contact.setC_o(resultSet.getString(2));
        contact.setLocation(resultSet.getString(3));
        contact.setAddress(resultSet.getString(4));
        contact.setTelephone(resultSet.getString(5));
        contact.setE_mail(resultSet.getString(6));
        contact.setRef_name(resultSet.getString(7));
        contact.setCid(cid);
        contact.setOther(resultSet.getString(9));
        return contact;
    }

    public Contact(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getC_o() {
        return c_o;
    }

    public void setC_o(String c_o) {
        this.c_o = c_o;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getRef_name() {
        return ref_name;
    }

    public void setRef_name(String ref_name) {
        this.ref_name = ref_name;
    }

    public UUID getCid() {
        return cid;
    }

    public void setCid(UUID cid) {
        this.cid = cid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
