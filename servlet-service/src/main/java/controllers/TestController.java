package controllers;

import dao.Coordinates;
import dao.Discipline;
import dao.LabWork;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.validator.constraints.Range;
import repository.impl.LabWorkRepoImpl;
import service.impl.LabWorkServiceImpl;
import util.xml.XMLArray;
import util.xml.XMLParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.UnmarshalException;
import java.util.Map;

@WebServlet(urlPatterns = {"/elements", "/elements/*"})
public class TestController extends HttpServlet {
    LabWorkServiceImpl labWorkService;
    SessionFactory sessionFactory;
    @Override
    public void init() throws ServletException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        labWorkService = new LabWorkServiceImpl(sessionFactory);
        super.init();
    }
    //POST/elements/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            XMLParser<LabWork> parser = new XMLParser<>(LabWork.class);
            LabWork labWorkUnparsed = parser.xmlToObject(req.getInputStream());
            LabWork labWork = parser.objectToData(labWorkUnparsed);
            System.out.println(labWork.toString());
            labWorkService.save(labWork);

        }catch(Exception e){
            resp.sendError(405,"Недопустимый элемент");
        }

    }

    //DELETE/elements/id
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String pathInfo = req.getPathInfo();
//        if(pathInfo == null || pathInfo.equals("/")){
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        String[] splits = pathInfo.split("/");
//        if(splits.length != 2) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        try {
//            int id = Integer.valueOf(splits[1]);
//            labWorkService.delete(id);
//            resp.setStatus(200);
//        }catch (Exception e){
//            resp.sendError(404, "Объект не найден!");
//        }
        try {
            String[] pathParams = null;
            if (req.getPathInfo() != null) {
                pathParams = req.getPathInfo().split("/");
            }

            if (pathParams != null && pathParams.length > 1 && !pathParams[1].equals("")) {
                labWorkService.delete(Integer.parseInt(pathParams[1]));
            }
        }catch (Exception e){
            resp.sendError(404, "Объект не найден!");
        }
    }
    //GET/elements/sortField/pageNumber
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo == null || pathInfo.equals("/")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = pathInfo.split("/");
        if(splits.length != 4) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int first = Integer.valueOf(splits[1]);
            int amount = Integer.valueOf(splits[2]);
            String sortfield = splits[3];
            XMLArray arr = new XMLArray();
            arr.labWorks = labWorkService.get(first,amount, sortfield);
            XMLParser<XMLArray> parser = new XMLParser<>(XMLArray.class);
            resp.getOutputStream().print(parser.objectToXml(arr));
            resp.setStatus(200);
        }catch (Exception e){
            resp.sendError(404, "Объект не найден!");
        }
    }
    //PUT/elements
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream stream = req.getInputStream();
        labWorkService.update(stream);
    }

}
