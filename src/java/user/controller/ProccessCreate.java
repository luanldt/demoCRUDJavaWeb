/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import user.model.Systemuserhelper;
import user.pojo.Systemuser;

/**
 *
 * @author cod.f
 */
@WebServlet(name = "ProccessCreate", urlPatterns = {"/ProccessCreate"})
public class ProccessCreate extends HttpServlet {
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

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
        try {
            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory =new DiskFileItemFactory();
            File filedir = (File)getServletContext().getAttribute("FILES_DIR_FILE");
            factory.setRepository(filedir);
            List items = new ServletFileUpload(factory).parseRequest(request);
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (FileItem item : (List<FileItem>)items) {
                if (item.isFormField()) {
                    if(item.getFieldName().equals("gioiTinh")) {
                        builder.append("\"").append(item.getFieldName()).append("\"" + ":").append("\"").append(item.getString().equals("Nam")?1:0).append("\",");
                    } else {
                        InputStreamReader inputStreamReader = new InputStreamReader((InputStream)item.getInputStream(), "UTF-8");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String line;    
                        while((line = bufferedReader.readLine())!=null) {
                            builder.append("\"").append(item.getFieldName()).append("\"" + ":").append("\"").append(line).append("\",");
                        }
                        
                    }
                } else {
                    if(!"".equals(item.getName())) {
                        builder.append("\"").append(item.getFieldName()).append("\"" + ":").append("\"").append(item.getName()).append("\",");

                        // get file path
                        ServletConfig config = getServletConfig();
                        ServletContext context = config.getServletContext();
                        String webInfPath = context.getRealPath("static");

                        InputStream inputStream = item.getInputStream();
                        File file = new File(webInfPath + "/images/" + item.getName());
    //                    file.createNewFile();
                        OutputStream os = new FileOutputStream(file);
                        byte[] buffer = new byte[10 * 1024];
                        for (int length; (length = inputStream.read(buffer)) != -1;) {
                            os.write(buffer, 0, length);
                        }
                    }
                    
                }
            }
            builder.deleteCharAt(builder.length()-1);
            builder.append("}");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson g = gsonBuilder.create();
            Systemuser systemuser = g.fromJson(builder.toString(), Systemuser.class);
            Systemuserhelper s = new Systemuserhelper();
            if(!s.create(systemuser)){
                response.getWriter().write("Fail!!!");
            } else {
                response.getWriter().write("Success");
            }
        } catch (FileUploadException | IOException e) {
            response.getWriter().write("Fail!!!");
            e.printStackTrace();
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
