/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
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
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import user.model.Diplomahelper;
import user.pojo.Userdiploma;

/**
 *
 * @author cod.f
 */
@WebServlet(name = "ProcessData", urlPatterns = {"/ProcessData"})
public class ProcessData extends HttpServlet {

    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    // Const
    private final List<String> columns = Arrays.asList("maNhanVien", "ten", "ho", "ngaySinh", "noiSinh", "diaChiThuongTru", "diaChiTamTru", "gioiTinh", "id", "matKhau", "hinhAnh");

    // Global variable
    Systemuserhelper systemuserhelper = new Systemuserhelper();
    private boolean isUpdate = false;
    Gson gson = new Gson();

    private void processGet(HttpServletRequest request, HttpServletResponse response) {
//        String draw = request.getParameter("draw");
//        String start = request.getParameter("start");
//        int iStart = Integer.parseInt(start);
//        String length = request.getParameter("length");
//        int iLength = Integer.parseInt(length);
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            List<Systemuser> systemusersAll = systemuserhelper.findAll();
//            List<Systemuser> systemusersPagination = systemuserhelper.findPagination(iStart, iLength);
            //postBackValue(systemusersAll, request, response, systemusersAll.size());
            StringBuilder builder = new StringBuilder();
//            for (Systemuser s : systemusersAll) {
//                s.setUserdiplomas(null);
//            }
            JsonElement json = gson.toJsonTree(systemusersAll, new TypeToken<List<Systemuser>>() {
                        }.getType());
//
            builder.append("{");
//            builder.append("\"draw\":").append(draw).append(',');
//            builder.append("\"recordsTotal\":").append(systemusersAll.size()).append(",");
//            builder.append("\"recordsFiltered\":").append(systemusersAll.size()).append(",");
            builder.append("\"data\":").append(json);
            builder.append("}");
            out.write(builder.toString());
//            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Do cài đặt kiểu trả về là JSON nên nếu trả về kiểu JSON nghĩa là thành
     * công còn ngược lại nếu trả về String không phải JSON là thất bại.
     *
     * @param request
     * @param response
     * @param message
     * @throws IOException
     */
    private void sendResultSignal(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        if (message.equals(SUCCESS)) {
            String jsonSuccess = "{\"mesage\":\"success\"}";
            response.getWriter().write(jsonSuccess);
        } else {
            response.getWriter().write("That bai");
        }
    }

    private void processRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sId = request.getParameter("id");
        if (sId != null && !"".equals(sId)) {
            try {
                int iId = Integer.parseInt(sId);
                if (systemuserhelper.delete(iId)) {
                    sendResultSignal(request, response, SUCCESS);
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void processAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<String> tenBangCaps = new ArrayList<>();
            List<String> noiCaps = new ArrayList<>();
            List<String> ngayCaps = new ArrayList<>();

            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            File filedir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
            factory.setRepository(filedir);
            List items = new ServletFileUpload(factory).parseRequest(request);
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            for (FileItem item : (List<FileItem>) items) {
                if (item.isFormField()) {
                    if (item.getFieldName().equals("gioiTinh")) {
                        builder.append("\"").append(item.getFieldName()).append("\"" + ":").append("\"").append(item.getString().equals("on") ? 1 : 0).append("\",");
                    } else if (item.getFieldName().contains("[]")) {
                        InputStreamReader inputStreamReader;
                        BufferedReader bufferedReader;
                        String line;
                        switch (item.getFieldName()) {
                            case "tenBangCap[]":
                                inputStreamReader = new InputStreamReader((InputStream) item.getInputStream(), "UTF-8");
                                bufferedReader = new BufferedReader(inputStreamReader);
                                while ((line = bufferedReader.readLine()) != null) {
                                    tenBangCaps.add(item.getString());

                                }
                                break;
                            case "ngayCap[]":
                                inputStreamReader = new InputStreamReader((InputStream) item.getInputStream(), "UTF-8");
                                bufferedReader = new BufferedReader(inputStreamReader);
                                while ((line = bufferedReader.readLine()) != null) {
                                    ngayCaps.add(item.getString());
                                }
                                break;
                            case "noiCap[]":
                                inputStreamReader = new InputStreamReader((InputStream) item.getInputStream(), "UTF-8");
                                bufferedReader = new BufferedReader(inputStreamReader);
                                while ((line = bufferedReader.readLine()) != null) {
                                    noiCaps.add(item.getString());
                                }
                                break;
                        }
                    } else {
                        InputStreamReader inputStreamReader = new InputStreamReader((InputStream) item.getInputStream(), "UTF-8");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            builder.append("\"").append(item.getFieldName()).append("\"" + ":").append("\"").append(line).append("\",");
                        }
                    }
                } else if (!"".equals(item.getName())) {
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
            builder.deleteCharAt(builder.length() - 1);
//           
            builder.append("}");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson g = gsonBuilder.create();
            Systemuser systemuser = g.fromJson(builder.toString(), Systemuser.class);
            Systemuserhelper s = new Systemuserhelper();
            String id = s.create(systemuser);
            if (id == null) {
                sendResultSignal(request, response, FAIL);
            } else {
                StringBuilder stringBuilderDiploma;

                for (int i = 0; i < tenBangCaps.size(); i++) {
                    stringBuilderDiploma = new StringBuilder();
                    stringBuilderDiploma.append("{").append("\"tenBangCap\":\"").append(tenBangCaps.get(i)).append("\",")
                            .append("\"ngayCap\":\"").append(ngayCaps.get(i)).append("\",")
                            .append("\"noiCap\":\"").append(noiCaps.get(i)).append("\"}");
                    Userdiploma userdiploma = g.fromJson(stringBuilderDiploma.toString(), Userdiploma.class);
                    userdiploma.setSystemuser(systemuserhelper.findOne(Integer.parseInt(id)));
                    Diplomahelper d = new Diplomahelper();
                    if (null == d.create(userdiploma)) {
                        sendResultSignal(request, response, FAIL);
                    }
                }

            }
        } catch (FileUploadException | IOException e) {
            sendResultSignal(request, response, FAIL);

        }
    }

    private void processEdit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        String sId = request.getParameter("id");
        if (sId != null && !"".equals(sId)) {
            try {
                int iId = Integer.parseInt(sId);
                Systemuser systemuser = systemuserhelper.findOne(iId);
                if (systemuser != null) {
                    response.getWriter().write(gson.toJson(systemuser));
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processAction(HttpServletRequest request, HttpServletResponse response, String method) throws IOException {
        String action = request.getParameter("action");
        if ("GET".equals(method)) {
            if (action == null || "".equals(action)) {
                processGet(request, response);
            } else {
                processEdit(request, response);
            }
        } else if (action != null && !"".equals(action)) {
            if ("remove".equals(action)) {
                processRemove(request, response);
            }
        } else {
            processAdd(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processAction(request, response, "POST");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processAction(req, resp, "GET"); //To change body of generated methods, choose Tools | Templates.
    }
}
