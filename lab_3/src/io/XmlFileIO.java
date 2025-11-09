package io;

import model.Ship;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XmlFileIO extends AbstractIO<Ship> {

    public List<Ship> read(String filename) {
        List<Ship> ships = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));
            NodeList nodes = doc.getElementsByTagName("ship");
            NodeList nodeList = doc.getElementsByTagName("ship");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);
                Ship s = new Ship();
                s.setId(Integer.parseInt(e.getElementsByTagName("id").item(0).getTextContent()));
                s.setName(e.getElementsByTagName("name").item(0).getTextContent());
                s.setType(e.getElementsByTagName("type").item(0).getTextContent());
                s.setTonnage(Double.parseDouble(e.getElementsByTagName("tonnage").item(0).getTextContent()));
                s.setSpeed(Double.parseDouble(e.getElementsByTagName("speed").item(0).getTextContent()));
                s.setPrice(Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent()));

                String dateStr = e.getElementsByTagName("productionDate").item(0).getTextContent();
                s.setProductionDate(new SimpleDateFormat("dd.MM.yyyy").parse(dateStr));

                ships.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ships;
    }

    public void write(String filename, List<Ship> ships) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("ships");
            doc.appendChild(root);
            for (Ship s : ships) {
                Element ship = doc.createElement("ship");
                root.appendChild(ship);

                Element id = doc.createElement("id");
                id.setTextContent(String.valueOf(s.getId()));
                ship.appendChild(id);

                Element name = doc.createElement("name");
                name.setTextContent(s.getName());
                ship.appendChild(name);

                Element type = doc.createElement("type");
                type.setTextContent(s.getType());
                ship.appendChild(type);

                Element tonnage = doc.createElement("tonnage");
                tonnage.setTextContent(String.valueOf(s.getTonnage()));
                ship.appendChild(tonnage);

                Element speed = doc.createElement("speed");
                speed.setTextContent(String.valueOf(s.getSpeed()));
                ship.appendChild(speed);

                Element price = doc.createElement("price");
                price.setTextContent(String.valueOf(s.getPrice()));
                ship.appendChild(price);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(filename)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
