package org.opendatadaykalro.is.model;

import org.postgis.PGgeometry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

public class Center {

    private final static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    private UUID cid;
    private String cname;
    private String category;
    private String speciality;
    private String county;
    private double lon;
    private double lat;
    private PGgeometry geom;

    public Center(){};

    public Center(UUID cid, String cname, String category, String speciality, String county, double lon, double lat, PGgeometry geom) {
        this.cid = cid;
        this.cname = cname;
        this.category = category;
        this.speciality = speciality;
        this.county = county;
        this.lon = lon;
        this.lat = lat;
        this.geom=geom;

    }

    public Center createCenterFromResultSet(ResultSet resultSet) throws SQLException {
        Center center = new Center();
        java.util.UUID uuid = (java.util.UUID) resultSet.getObject(1);
        center.setCid(uuid);
        center.setCname(resultSet.getString(2));
        center.setCategory(resultSet.getString(3));
        center.setSpeciality(resultSet.getString(4));
        center.setCounty(resultSet.getString(5));
        center.setLon(resultSet.getDouble(6));
        center.setLat(resultSet.getDouble(7));
        center.setGeom((PGgeometry) resultSet.getObject(8));
        return center;
    }
    public UUID getCid() {
        return cid;
    }

    public void setCid(UUID cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    public PGgeometry getGeom() {
        return geom;
    }

    public void setGeom(PGgeometry geom) {
        this.geom = geom;
    }


}
