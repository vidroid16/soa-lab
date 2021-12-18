package controllers;

import dao.LabWork;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import service.impl.LabWorkServiceImpl;
import util.xml.XMLArray;
import util.xml.XMLParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/utill")
public class UtilController extends HttpServlet {
    LabWorkServiceImpl labWorkService;
    SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        labWorkService = new LabWorkServiceImpl(sessionFactory);
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        labWorkService.generateLab(10);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(labWorkService.amount());
        XMLArray arr = new XMLArray();
        arr.labWorks = labWorkService.get(7,-4, "lab_id");
        XMLParser<XMLArray> parser = new XMLParser<>(XMLArray.class);
        resp.getOutputStream().print(parser.objectToXml(arr));
    }
}