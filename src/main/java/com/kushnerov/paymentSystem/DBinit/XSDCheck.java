package  com.kushnerov.paymentSystem.DBinit;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDCheck {
    private static final XSDCheck instance = new XSDCheck();

    private XSDCheck() {
    }

    public static XSDCheck getInstance() {
        return instance;
    }

    public static boolean check(String pathToXml, String pathToXsd) throws SAXException, IOException {

        SchemaFactory factory =
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        File schemaLocation = new File(pathToXsd);
        Schema schema = factory.newSchema(schemaLocation);

        Validator validator = schema.newValidator();

        Source source = new StreamSource(pathToXml);

        try {
            validator.validate(source);
            return true;
        } catch (SAXException ex) {
            return false;
        }

    }

}