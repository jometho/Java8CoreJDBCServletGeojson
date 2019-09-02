package org.opendatadaykalro.is.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Service {
    private UUID sid;
    private UUID cid;
    private String sname;
    private String scategory;
    private String listing;

    public Service(UUID sid, UUID cid, String sname, String scategory, String listing) {
        this.sid = sid;
        this.cid = cid;
        this.sname = sname;
        this.scategory = scategory;
        this.listing = listing;
    }
    public Service(){}

    public Service createServiceFromResultSet(ResultSet resultSet) throws SQLException {
        Service service = new Service();
        java.util.UUID pid = (java.util.UUID) resultSet.getObject(1);
        java.util.UUID cid = (java.util.UUID) resultSet.getObject(2);
        service.setSid(pid);
        service.setCid(cid);
        service.setSname(resultSet.getString(3));
        service.setScategory(resultSet.getString(4));
        service.setListing(resultSet.getString(5));
        return service;
    }
    public UUID getSid() {
        return sid;
    }

    public void setSid(UUID sid) {
        this.sid = sid;
    }

    public UUID getCid() {
        return cid;
    }

    public void setCid(UUID cid) {
        this.cid = cid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getScategory() {
        return scategory;
    }

    public void setScategory(String scategory) {
        this.scategory = scategory;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }
}
