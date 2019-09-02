package org.opendatadaykalro.is.services;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opendatadaykalro.is.db.DatabaseService;
import org.opendatadaykalro.is.model.Center;
import org.opendatadaykalro.is.model.Contact;
import org.opendatadaykalro.is.model.Product;
import org.opendatadaykalro.is.model.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.logging.Logger;

public class CentersService {
    private final static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    private static volatile CentersService centersServiceInstance;

    //Eager Singleton Initialization
    private CentersService() {
        //Prevent form the reflection api.
        if (centersServiceInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static CentersService getInstance() {
        //Double check locking pattern
        if (centersServiceInstance == null) { //Check for the first time

            synchronized (CentersService.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (centersServiceInstance == null) centersServiceInstance = new CentersService();
            }
        }
        return centersServiceInstance;
    }


    public List<Center> queryCenters() throws SQLException, ClassNotFoundException {
        DatabaseService databaseService = DatabaseService.getInstance();

        String query = "SELECT cid,cname,category,speciality,county,lon,lat, geom FROM centers";
        List<Center> centersList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            Center center = new Center();
            while (rs.next()) {
                centersList.add(center.createCenterFromResultSet(rs));
            }
            logger.info(centersList.toString());
            return centersList;

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.info(e.getCause().toString());
            throw e;
        }
    }

    public List<Center> queryCenterByName(String centerName) throws SQLException, ClassNotFoundException {
        DatabaseService databaseService = DatabaseService.getInstance();

        String query = "SELECT cid,cname,category,speciality,county,lon,lat, geom FROM centers WHERE cname=?";


        List<Center> centersList = new ArrayList<>();
        Center center = new Center();
        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query);
            preparedStatement.setString(1, centerName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                centersList.add(center.createCenterFromResultSet(rs));
            }
            logger.info(center.toString());
            return centersList;

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.info(e.getCause().toString());
            throw e;
        }

    }

    public List<Product> queryProducts() throws SQLException, ClassNotFoundException {
        DatabaseService databaseService = DatabaseService.getInstance();

        String query = "SELECT pid,cid,pname,pcategory, listing::TEXT FROM products";
        List<Product> productsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            Product product = new Product();
            while (rs.next()) {
                productsList.add(product.createProductFromResultSet(rs));
            }
            logger.info(productsList.toString());
            return productsList;

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.info(e.getCause().toString());
            throw e;
        }
    }

    public List<Service> queryservices() throws SQLException, ClassNotFoundException {
        DatabaseService databaseService = DatabaseService.getInstance();

        String query = "SELECT sid,cid,sname,scategory, listing FROM services";
        List<Service> serviceList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            Service service = new Service();
            while (rs.next()) {
                serviceList.add(service.createServiceFromResultSet(rs));
            }
            logger.info(serviceList.toString());
            return serviceList;

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.info(e.getCause().toString());
            throw e;
        }
    }

    public List<Contact> queryContacts() throws SQLException, ClassNotFoundException {
        DatabaseService databaseService = DatabaseService.getInstance();
        String query = "SELECT * FROM contacts";

        List<Contact> contactsList = new ArrayList<>();
        Contact contact = new Contact();
        try {
            PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                contactsList.add(contact.createContactFromResultSet(rs));
            }
            logger.info(contactsList.toString());
            return contactsList;

        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            logger.info(e.getCause().toString());
            throw e;
        }

    }


    JsonElement getCenterContact(List<Contact> contactsList, String centerId) {
        java.util.UUID cid = UUID.fromString(centerId);
        for (Contact contact : contactsList) {
            if (contact.getCid().equals(cid)) {

                String addressLine1 = contact.getC_o() + ", " + contact.getLocation() + " " + contact.getRef_name();
                String addressLine2 = contact.getAddress();
                String email = "Email " + contact.getE_mail();
                String telephone = "Phone " + contact.getTelephone() + " " + contact.getOther();

                JsonArray jsonArray = new JsonArray();
                jsonArray.add(addressLine1);
                jsonArray.add(addressLine2);
                jsonArray.add(email);
                jsonArray.add(telephone);

                return jsonArray;
            }

        }
        return null; //it wasn't found at all
    }

    public JsonElement getCenterProduct(List<Product> products, String centerId) {
        logger.info("Getting products for Center"+centerId);
        java.util.UUID cid = UUID.fromString(centerId);
        JsonArray centerProducts = new JsonArray();

        ListIterator<Product> iterator = products.listIterator();
        while(iterator.hasNext()) {
            JsonObject productObject = new JsonObject();
            Product product;
            product=iterator.next();
            if (product.getCid().equals(cid)) {
                productObject.addProperty("Name", product.getPname());
                productObject.addProperty("Category", product.getPcategory());
                productObject.addProperty("listing",product.getPlisting());
                logger.info("th Product:"+product.getPname()+" to Center product List");
                centerProducts.add(productObject);
                logger.info(centerProducts.size()+" products added.");
            }
        }
        return centerProducts; //it wasn't found at all
    }

    public JsonElement getCenterServices(List<Service> services, String centerId) {
        java.util.UUID cid = UUID.fromString(centerId);
        JsonArray centerServices = new JsonArray();

        ListIterator<Service> iterator =services.listIterator();
        while(iterator.hasNext())  {
            Service service;
            service= iterator.next();
            JsonObject serviceObject = new JsonObject();
            if (service.getCid().equals(cid)) {
                serviceObject.addProperty("Name", service.getSname());
                serviceObject.addProperty("Category", service.getScategory());
                serviceObject.addProperty("listing",service.getListing());

                logger.info("Adding Service:"+service.getCid()+" to Center services List");
                centerServices.add(serviceObject);
                logger.info(centerServices.size()+"services added.");
            }
        }
        return centerServices; //it wasn't found at all
    }


    public String asGeoJSON(List<Center> centerList) throws SQLException, ClassNotFoundException {

        JSONObject featureCollection = new JSONObject();
        try {
            //"crs": { "type": "name", "properties": { "name": "urn:ogc:def:crs:OGC:1.3:CRS84" } }
            JSONObject crs = new JSONObject();
            featureCollection.put("type", "FeatureCollection");
            featureCollection.put("name", "centers");
            crs.put("type", "name");
            JSONObject crsProps = new JSONObject();
            crsProps.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
            crs.put("properties", crsProps);
            featureCollection.put("crs", crs);
            JSONArray featureArray = new JSONArray();
            // iterate through your list
            List<Contact> contactsList = CentersService.getInstance().queryContacts();
            List<Product> productList = CentersService.getInstance().queryProducts();
            List<Service> serviceList = CentersService.getInstance().queryservices();
            for (Center center : centerList) {
                JsonElement contacts = getCenterContact(contactsList, String.valueOf(center.getCid()));
                JsonElement products = getCenterProduct(productList, String.valueOf(center.getCid()));
                JsonElement services = getCenterServices(serviceList, String.valueOf(center.getCid()));

                JSONObject feature = new JSONObject();
                JSONObject geometry = new JSONObject();
                JSONObject properties = new JSONObject();

                JSONArray coord = new JSONArray("[" + center.getLon() + "," + center.getLat() + "]");
                geometry.put("coordinates", coord);
                geometry.put("type", "Point");

                properties.put("cid", center.getCid());
                properties.put("cname", center.getCname());
                properties.put("category", center.getCategory());
                properties.put("speciality", center.getSpeciality());
                properties.put("county", center.getCounty());
                properties.put("contacts", contacts);
                properties.put("products", products);
                properties.put("services", services);


                feature.put("properties", properties);
                feature.put("type", "Feature");
                feature.put("geometry", geometry);
                featureArray.put(feature);
                featureCollection.put("features", featureArray);
            }
        } catch (JSONException e) {
            logger.info("can't save json object: " + e.toString());
        }
        // output the result
        logger.info("featureCollection=" + featureCollection.toString());
       /* if(featureCollection.get('geometry')==null){
            return null;
        }*/
        return featureCollection.toString();
    }

}
