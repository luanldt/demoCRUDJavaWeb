/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author cod.f
 */
@WebServlet(name = "ProcessFileUpload", urlPatterns = {"/ProcessFileUpload"})
public class ProcessFileUpload extends HttpServlet {

    @SuppressWarnings("null")
    private void processData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            File filedir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
            factory.setRepository(filedir);
            List items = new ServletFileUpload(factory).parseRequest(request);
            for (FileItem item : (List<FileItem>) items) {
                if (!item.isFormField()) {
                    ServletConfig config = getServletConfig();
                    ServletContext context = config.getServletContext();
                    String webInfPath = context.getRealPath("static");
                    InputStream inputStream = item.getInputStream();
                    File file = new File(webInfPath + "/images/" + item.getName());
                    //                    file.createNewFile();
                    os = new FileOutputStream(file);
                    byte[] buffer = new byte[10 * 1024];
                    for (int length; (length = inputStream.read(buffer)) != -1;) {
                        os.write(buffer, 0, length);
                    }
                    response.getWriter().write(item.getName());
                }
            }
        } catch (FileNotFoundException | FileUploadException ex) {
            Logger.getLogger(ProcessFileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            os.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processData(request, response);
    }

}
