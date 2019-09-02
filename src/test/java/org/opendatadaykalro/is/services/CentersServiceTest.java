package org.opendatadaykalro.is.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opendatadaykalro.is.model.Center;
import org.opendatadaykalro.is.model.Contact;
import org.opendatadaykalro.is.model.Product;
import org.opendatadaykalro.is.model.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CentersServiceTest {
    private final static Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getKalroCenter() throws SQLException, ClassNotFoundException {
        CentersService center = CentersService.getInstance();
        center.asGeoJSON(CentersService.getInstance().queryCenters());
    }

    @Test
    public void getCenterByName() throws SQLException, ClassNotFoundException {
        CentersService center = CentersService.getInstance();
        center.asGeoJSON(CentersService.getInstance().queryCenterByName("Marigat"));
    }

    @Test
    public void printContacts() throws SQLException, ClassNotFoundException {
        List<Contact> contactsList = CentersService.getInstance().queryContacts();
        JsonElement jsonObject = CentersService.getInstance().getCenterContact(contactsList, "a3da7278-4c08-4414-9f7f-ddcdd4490ed2");

        assert jsonObject != null;
    }

    @Test
    public void printProducts() throws IOException, SQLException, ClassNotFoundException {
        CentersService centersService = CentersService.getInstance();
        JsonElement productsJson = centersService.getCenterProduct(centersService.queryProducts(),"a3da7278-4c08-4414-9f7f-ddcdd4490ed2");
        System.out.println(productsJson);
        logger.info("Printing Products");
        filewriter(productsJson.getAsString());


    }

    @Test
    public void queryContacts() {
    }

    @Test
    public void queryCenterByName() {
    }

    @Test
    public void queryProducts() throws SQLException, ClassNotFoundException {
        CentersService.getInstance().queryProducts();
    }

    @Test
    public void queryServices() throws SQLException, ClassNotFoundException {
        CentersService centersService = CentersService.getInstance();
        List<Service> serviceList=new ArrayList<>();
       /* serviceList=CentersService.getInstance().queryservices();
        System.out.println(serviceList.toString());*/

        JsonElement servicesJson = centersService.getCenterServices(centersService.queryservices(),"a3da7278-4c08-4414-9f7f-ddcdd4490ed2");
        System.out.println(servicesJson);
        logger.info("Printing Products");
    }


    @Test
    public void asGeoJSON() throws SQLException, ClassNotFoundException, IOException {
        CentersService centersService = CentersService.getInstance();
        String geojson = CentersService.getInstance().asGeoJSON(centersService.queryCenters());

        filewriter(geojson);
    }

    private void filewriter(String file) throws IOException {
        logger.info("Writing file: " + file);
        FileWriter fw = new FileWriter("D:\\projects\\kalro\\simpleservlet\\src\\test\\data\\centers3.geojson", false);
        fw.write(file);
        fw.close();
        System.out.println("Successfully Copied JSON Object to File...");
    }

}