package  com.kushnerov.paymentSystem.DBinit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import  com.kushnerov.paymentSystem.exceptions.PaySysException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.IOException;

public class PropertiesManager {
    private static final Logger logger = Logger.getLogger(PropertiesManager.class.getName());
    private static final PropertiesManager instance = new PropertiesManager();

    private PropertiesManager() {
    }

    public static PropertiesManager getInstance() {
        return instance;
    }

   
    private PropertiesHolder propertiesReader(Element root)
            throws SAXException, IOException {
        PropertiesHolder propertiesHolder = new PropertiesHolder();
        NodeList dbElement = root.getElementsByTagName("database");
        for (int temp = 0; temp < dbElement.getLength(); temp++) {
            Node nNode = dbElement.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                propertiesHolder.setDriver(getTagValue("driver", eElement));
                propertiesHolder.setUrl(getTagValue("url", eElement));
                propertiesHolder.setUser(getTagValue("user", eElement));
                propertiesHolder.setPassword(getTagValue("password", eElement));
                Integer i = Integer.valueOf(getTagValue("initconnections", eElement));
                propertiesHolder.setInitConnections(i.intValue());
                i = Integer.valueOf(getTagValue("maxconnections", eElement));
                propertiesHolder.setMaxConnections(i.intValue());
            }
        }
        return propertiesHolder;
    }

   
    private String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

   
    public PropertiesHolder getProperty(ServletContext context) throws PaySysException {
        PropertiesHolder propertiesHolder = null;
       try {
            String xmlFileName = context.getRealPath("/WEB-INF/classes/properties.xml");
            String xsdFileName = context.getRealPath("/WEB-INF/classes/properties.xsd");
            if (XSDCheck.check(xmlFileName, xsdFileName)) {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(xmlFileName);
                document.getDocumentElement().normalize();
                Element root = document.getDocumentElement();
                propertiesHolder = propertiesReader(root);
            }
        } catch (Exception ex) {
        	logger.log(Level.ERROR, "Exception in PropertiesHolder:", ex);
            throw new PaySysException("ERROR_VALIDATE_XML", ex);
        }
        return propertiesHolder;
    }
}

