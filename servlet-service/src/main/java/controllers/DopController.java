package controllers;

import dao.LabWork;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.impl.LabWorkServiceImpl;
import util.xml.AnyXML;
import util.xml.XMLArray;
import util.xml.XMLParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@WebServlet(urlPatterns = {"/dop", "/dop/*"})
public class DopController extends HttpServlet {
    LabWorkServiceImpl labWorkService;
    SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        labWorkService = new LabWorkServiceImpl(sessionFactory);
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        try {
            XMLParser<AnyXML> parser = new XMLParser<>(AnyXML.class);
            XMLParser<XMLArray> arr = new XMLParser<>(XMLArray.class);
            String str = req.getParameter("str");
            ArrayList<LabWork> labs = labWorkService.getStartedWith(str);
            XMLArray xmlArray = new XMLArray();
            xmlArray.labWorks = labs;
            String rezult = arr.objectToXml(xmlArray);
            resp.setStatus(200);
            resp.getOutputStream().print(rezult);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(404, "Объект не найден!");
        }
    }
}