import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    static final String CLASS_NAME = Main.class.getSimpleName();
    static final Logger LOG = Logger.getLogger(CLASS_NAME);
    static final double SCALE_FACTOR = 2.0;

    static double x1 = 0;

    public static void main(String argv[]) {

        if (argv.length != 1) {
            LOG.severe("Falta archivo XML como argumento.");
            System.exit(1);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(argv[0]));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();

            System.out.println("");
            NodeList circles = root.getElementsByTagName("circle");

            int len = circles.getLength();

            for (int i = 0; i < len; i++) {

                Node circle = circles.item(i);

                System.out.printf("%s\n", circle.getNodeName());

                // Obtener los atributos del circulo
                NamedNodeMap circleAtt = circle.getAttributes();

                for (int j = 0; j < circleAtt.getLength(); j++) {
                    Node attNode = circleAtt.item(j);
                    String name = attNode.getNodeName();
                    String value = attNode.getNodeValue();
                    System.out.printf("%s => %s\n", name, value);

                    // identificar el radio y aplicar factor de escala
                    if (name.equals("r")) {

                        double newRadio = Double.parseDouble(value) * SCALE_FACTOR;

                        // asignar nuevo valor al radio
                        attNode.setNodeValue(String.valueOf(newRadio));

                        System.out.printf("New radio: %5.2f\n", newRadio);

                        double peCircle = (3.1416*(newRadio));
                        System.out.println("Perímetro: " + peCircle);

                        double num = 2;
                        double cuadrado = Math.pow(num, (newRadio/2));
                        double areaCircle = (3.1416*(cuadrado));
                        System.out.println("Área: " + areaCircle);

                    }

                }
                System.out.println();
            }

            System.out.println("");
            NodeList square = root.getElementsByTagName("line");

            int lenght = square.getLength();

            for (int i = 0; i < lenght-4; i++) {

                Node line = square.item(i);

                System.out.printf("%s\n", line.getNodeName());

                // Obtener los atributos del cuadrado
                NamedNodeMap lineAtt = line.getAttributes();

                for (int j = 0; j < lineAtt.getLength(); j++) {
                    Node attNode = lineAtt.item(j);
                    String name = attNode.getNodeName();
                    String value = attNode.getNodeValue();
                    System.out.printf("%s => %s\n", name, value);
                    if (name.equals("x1")) {
                        x1 = Double.valueOf(value);
                        attNode.setNodeValue(String.valueOf(x1));
                    }

                    if (name.equals("x2")) {

                        double x2 = Double.valueOf(value);
                        attNode.setNodeValue(String.valueOf(x2));
                        double lado=x2-x1;
                        System.out.println("Lado: " + lado);
                        double per = (lado*4);
                        System.out.println("Perímetro: " + per);
                        double area=lado*lado;
                        System.out.println("Área: " + area);

                        /*
                        largo = Double.valueOf(value);
                        attNode.setNodeValue(String.valueOf(largo));
                        System.out.printf("New radio: %5.2f\n", largo);
                        System.out.println("per: " + (largo));
                        */

                    }


                }

                System.out.println();
            }

        } catch (ParserConfigurationException e) {
            LOG.severe(e.getMessage());
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        } catch (SAXException e) {
            LOG.severe(e.getMessage());
        }
    }

    public static double radians(double degreeAngle) {
        return (degreeAngle * Math.PI) / 180.0;
    }

}