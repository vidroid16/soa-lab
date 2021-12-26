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

@WebServlet(urlPatterns = {"/min", "/min/*"})
public class MinController extends HttpServlet {
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
        try {
            XMLParser<AnyXML> parser = new XMLParser<>(AnyXML.class);
            String str = req.getParameter("point");
            AnyXML anyXML = new AnyXML();
            int a = Integer.parseInt(str);
            int total = labWorkService.getAllWhereMinLowerThan(a);
            anyXML.xml = String.valueOf(labWorkService.getAllWhereMinLowerThan(a));
            String rezult = parser.objectToXml(anyXML);
            resp.setStatus(200);
            resp.getOutputStream().print(rezult);
        } catch (Exception e) {
            resp.sendError(404, "Объект не найден!");
        }
    }
}