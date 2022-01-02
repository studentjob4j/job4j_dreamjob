package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @since 1.01.2022
 * @version 1.0
 * Загрузка фото в определенную директорию и чтение по запросу
 * Когда сохраняем файл , то использую путь c:\\images" + File.separator + "image" + req.getParameter("id")
 * например получается следующий путь к папке c:\\images\\image1
 * Чтение также определяю по image + id человека
 */

public class UploadServlet extends HttpServlet {
    private final PropertiesUtil ps = new PropertiesUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> images = new ArrayList<>();

        String folder = ps.properties().getProperty("name") +  File.separator + "image" + req.getParameter("id");
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }
        for (File name : new File(folder).listFiles()) {
            if (!name.isDirectory()) {
                images.add(name.getName());
            }
        }
        req.setAttribute("images", images);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/upload.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File( ps.properties().getProperty("name") +
                    File.separator + "image" + req.getParameter("id"));
            if (!folder.exists()) {
                folder.mkdir();
            }

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator +  item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
