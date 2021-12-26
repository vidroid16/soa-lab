package util.xml;

import com.sun.xml.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;

@XmlElement
@XmlRootElement
public class AnyXML {
    public String xml;

    public AnyXML() {
        this.xml = "";
    }
}
