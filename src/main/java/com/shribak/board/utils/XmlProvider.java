package com.shribak.board.utils;


import com.shribak.board.model.Advertisement;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code XmlProvider} class provides reading and writing advertisements
 * to xml file.
 *
 * @author Dmitrii Shribak
 */
public class XmlProvider implements FileProvider {
    private Logger logger = Logger.getLogger(XmlProvider.class);
    private String xmlPath;

    /**
     * Reads advertisements from xml file.
     *
     * @param xmlPath  path to xml file
     * @return list of advertisements
     */
    @Override
    public List<Advertisement> read(String xmlPath) {
        this.xmlPath = xmlPath;
        List<Advertisement> advertisements = new ArrayList<Advertisement>();

        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("advertisement");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    advertisements.add(createAdvertisement(element));
                }
            }
        } catch (Exception e) {
            logger.info("All information will be store in the " + xmlPath + " file.");
        }

        return advertisements;
    }

    /**
     * Creates new advertisement.
     *
     * @param element xml element
     * @return new advertisement
     */
    private Advertisement createAdvertisement(Element element) {
        String title = element.getAttribute("title");
        String author = element.getElementsByTagName("author").item(0).getTextContent();
        long date = Long.parseLong(element.getElementsByTagName("date").item(0).getTextContent());
        String category = element.getElementsByTagName("category").item(0).getTextContent();
        String content = element.getElementsByTagName("content").item(0).getTextContent();

        return new Advertisement(author, date, category, title, content);
    }

    /**
     * Writes list of advertisements to xml file.
     *
     * @param advertisements  list of advertisements
     */
    @Override
    public void write(List<Advertisement> advertisements) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Advertisements");
            doc.appendChild(rootElement);

            createAdvertisement(advertisements, doc, rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlPath));

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            logger.error("Can't write advertisements to file");
        } catch (TransformerException e) {
            logger.error("Can't write advertisements to file");
        }
    }

    /**
     * Creates children element for xml document.
     *
     * @param advertisements  list of advertisements
     * @param doc  xml document
     * @param rootElement root element of xml document
     */
    private void createAdvertisement(List<Advertisement> advertisements, Document doc, Element rootElement) {
        for(Advertisement advertisement : advertisements) {
            Element advertisementElement = doc.createElement("advertisement");
            rootElement.appendChild(advertisementElement);
            advertisementElement.setAttribute("title", advertisement.getTitle());

            addChild("author", advertisement.getAuthor(), doc, advertisementElement);
            addChild("date", advertisement.getDate() + "", doc, advertisementElement);
            addChild("category", advertisement.getCategory(), doc, advertisementElement);
            addChild("content", advertisement.getContent(), doc, advertisementElement);
        }
    }

    /**
     * Adds children to root element of xml document.
     *
     * @param field field name of advertisement
     * @param value value of advertisement field
     * @param doc xml document
     * @param advertisementElement child element of xml document
     */
    private void addChild(String field, String value, Document doc, Element advertisementElement) {
        Element author = doc.createElement(field);
        author.appendChild(doc.createTextNode(value));
        advertisementElement.appendChild(author);
    }
}
