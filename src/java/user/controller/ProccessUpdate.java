/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.Convert;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.model.Systemuserhelper;
import user.pojo.Systemuser;

/**
 *
 * @author cod.f
 */
@WebServlet(name = "ProccessUpdate", urlPatterns = {"/ProccessUpdate"})
public class ProccessUpdate extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        
        String sId = request.getParameter("id");
        if (sId != null) {
            int iId = Integer.parseInt(sId);
            Systemuserhelper systemuserhelper = new Systemuserhelper();
            Systemuser systemuser = systemuserhelper.findOne(iId);
            if (systemuser != null) {
                response.getWriter().write(gson.toJson(systemuser));
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
