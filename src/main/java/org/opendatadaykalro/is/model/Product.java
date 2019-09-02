package org.opendatadaykalro.is.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Product {
    private UUID pid;
    private UUID cid;
    private String pname;
    private String pcategory;
    private String plisting;

    public Product(){}

    public Product(UUID pid, UUID cid, String pname, String pcategory, String plisting) {
        this.pid = pid;
        this.cid = cid;
        this.pname = pname;
        this.pcategory = pcategory;
        this.plisting=plisting;
    }

    public Product createProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        java.util.UUID pid = (java.util.UUID) resultSet.getObject(1);
        java.util.UUID cid = (java.util.UUID) resultSet.getObject(2);
        product.setPid(pid);
        product.setCid(cid);
        product.setPname(resultSet.getString(3));
        product.setPcategory(resultSet.getString(4));
        product.setPlisting(resultSet.getString(5));
        return product;
    }

    public UUID getPid() {
        return pid;
    }

    private void setPid(UUID pid) {
        this.pid = pid;
    }

    public UUID getCid() {
        return cid;
    }

    private void setCid(UUID cid) {
        this.cid = cid;
    }

    public String getPname() {
        return pname;
    }

    private void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcategory() {
        return pcategory;
    }

    private void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public String getPlisting() {
        return plisting;
    }

    public void setPlisting(String plisting) {
        this.plisting = plisting;
    }
}
