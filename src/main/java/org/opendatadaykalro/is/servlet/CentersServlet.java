package org.opendatadaykalro.is.servlet;

import org.opendatadaykalro.is.db.DatabaseService;
import org.opendatadaykalro.is.services.CentersService;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CentersServlet")
public class CentersServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("POST Method is not allowed for this Servlet. Try Get");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String centerName = request.getParameter("centerName");
        String serviceResponse = "";
        try{
            CentersService centersService=CentersService.getInstance();
            if(centerName!=null){
                //Get Center By Name
                serviceResponse=centersService.asGeoJSON(centersService.queryCenterByName(centerName));
            }else {
                //Get All centers
                serviceResponse=centersService.asGeoJSON(centersService.queryCenters());
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(serviceResponse.isEmpty()){
            throw new ServletException("Error during Service request. Servlet response :" + response.getStatus());
        }
        response.getWriter().write(serviceResponse);


    }

    @Override
    public void destroy() {
        System.out.println("Servlet destruction initiated");
        try {
            DatabaseService.getInstance().closeConnection();
        } catch (SQLException e) {
            System.err.println("Error during Close Connection");
            e.printStackTrace();
        }
        super.destroy();
    }
}
