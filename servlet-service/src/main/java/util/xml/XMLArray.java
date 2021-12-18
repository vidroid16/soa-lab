package util.xml;

import com.sun.xml.txw2.annotation.XmlElement;
import dao.LabWork;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlElement
@XmlRootElement
public class XMLArray {
   public ArrayList<LabWork> labWorks;

    public XMLArray() {
        this.labWorks = new ArrayList<>();
    }
}
