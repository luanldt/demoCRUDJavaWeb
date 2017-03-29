/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.model.Systemuserhelper;
import user.pojo.Systemuser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Query;
import org.hibernate.Session;
import user.model.HibernateUtil;

/**
 *
 * @author cod.f
 */
@WebServlet(name = "ProcessData", urlPatterns = {"/ProcessData"})
public class ProcessData extends HttpServlet {

    // Const
    private final List<String> columns = Arrays.asList("maNhanVien", "ten", "ho", "ngaySinh", "noiSinh", "diaChiThuongTru", "diaChiTamTru", "gioiTinh", "id", "matKhau", "hinhAnh");

    // Global variable
    Systemuserhelper systemuserhelper = new Systemuserhelper();
    private boolean isUpdate = false;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processAction(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    private void processGet(HttpServletRequest request, HttpServletResponse response) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        int iStart = Integer.parseInt(start);
        String length = request.getParameter("length");
        int iLength = Integer.parseInt(length);
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            List<Systemuser> systemusersAll = systemuserhelper.findAll();
            List<Systemuser> systemusersPagination = systemuserhelper.findPagination(iStart, iLength);
            //postBackValue(systemusersAll, request, response, systemusersAll.size());
            StringBuilder builder = new StringBuilder();
            String json = gson.toJson(systemusersPagination);

            builder.append("{");
            builder.append("\"draw\":").append(draw).append(',');
            builder.append("\"recordsTotal\":").append(systemusersAll.size()).append(",");
            builder.append("\"recordsFiltered\":").append(systemusersAll.size()).append(",");
            builder.append("\"data\":").append(json);
            builder.append("}");

            out.write(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void postBackValue(List<Systemuser> list,HttpServletRequest request,  HttpServletResponse response, int size) {
        try (PrintWriter out = response.getWriter()) {
            String draw = request.getParameter("draw");
            Gson gson = new Gson();
            StringBuilder builder = new StringBuilder();
            String json = gson.toJson(list);
            builder.append("{");
            builder.append("\"draw\":").append(draw).append(',');
            builder.append("\"recordsTotal\":").append(size).append(",");
            builder.append("\"recordsFiltered\":").append(size).append(",");
            builder.append("\"data\":").append(json);
            builder.append("}");

            out.write(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String element = params.nextElement();
            if (element.contains("data") && element.contains("id")) {
                if (element != null) {
                    String sId = request.getParameterValues(element)[0];
                    int iId = Integer.parseInt(sId);
                    if (systemuserhelper.delete(iId)) {
                        response.getWriter().write("{\"message\": \"success\"}");
                    }

                }
            }
        }
    }

    private String processDataToJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Enumeration<String> params = request.getParameterNames();
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        while (params.hasMoreElements()) {
            String element = params.nextElement();
            if (!element.equals("action")) {
                String key = "";
                if (!isUpdate) {
                    key = element.substring("data[0][".length(), element.length() - 1);
                } else {
                    Pattern r = Pattern.compile("^\\S+\\[");
                    Matcher m = r.matcher(element);
                    if(m.find()) {
                        key = element.substring(m.end(), element.length() - 1);
                    }
                }
                
                String value = request.getParameterValues(element)[0];
                builder.append("\"").append(key).append("\"" + ":").append("\"").append(value).append("\",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        Enumeration<String> s = request.getParameterNames();
        if(request.getParameter("search[value]")!=null && !"".equals(request.getParameter("search[value]"))) {
           List<Systemuser> list = systemuserhelper.query(request.getParameter("search[value]"));
            postBackValue(list, request, response, list.size());
        } else if (action == null) {
            processGet(request, response);
        } else {
            String systemuser;
            switch (action) {
                case "create":
                    isUpdate = false;
                    systemuser = processDataToJson(request, response);
                    processAdd(systemuser, response);
                    break;
                case "edit":
                    isUpdate = true;
                    systemuser = processDataToJson(request, response);
                    processAdd(systemuser, response);
                    break;
                case "remove":
                    processRemove(request, response);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processAdd(String systemuserJson, HttpServletResponse response) throws IOException {
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson g = gsonBuilder.create();
            Systemuser systemuser = g.fromJson(systemuserJson, Systemuser.class);
            if (!systemuserhelper.create(systemuser)) {
                response.getWriter().write("Fail!!!");
            } else {
                response.getWriter().write("{\"message\":\"success\"}");
            }
        } catch (IOException e) {
            response.getWriter().write("Fail!!!");
            e.printStackTrace();
        }
    }

}
