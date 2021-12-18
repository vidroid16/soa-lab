package util.xml;

import dao.Coordinates;
import dao.Discipline;
import dao.LabWork;

import javax.xml.bind.*;
import java.io.*;

public class XMLParser<T>{
    private Class c;

    public XMLParser(Class c) {
        this.c = c;
    }

    public T xmlToObject(InputStream stream){

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            T work = (T) jaxbUnmarshaller.unmarshal(stream);
            return work;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String objectToXml(T object){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            OutputStream outputStream = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(object, outputStream);
            return outputStream.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LabWork objectToData(LabWork labWork){

        Coordinates coords = new Coordinates();
        coords.setX(labWork.getCoordinates().getX());
        coords.setY(labWork.getCoordinates().getY());
        Discipline discipline = new Discipline(labWork.getDiscipline().getName(),labWork.getDiscipline().getLectureHours());
        return new LabWork(labWork.getName(),coords,labWork.getMinimalPoint(),
                labWork.getMaximumPoint(),labWork.getAveragePoint(), labWork.getDifficultyName(),discipline);
    }
}
