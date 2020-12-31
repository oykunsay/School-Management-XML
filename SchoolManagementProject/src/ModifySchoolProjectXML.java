import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModifySchoolProjectXML {

    public static void main(String[] args) {
        String filePath = "students.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            

            updateAttributeValue(doc);
            

            updateElementValue(doc);
            

            deleteElement(doc);

            
            addElement(doc);
            
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("students_updated.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("XML file updated successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    
    
private static void addElement(Document doc) {
    NodeList students = doc.getElementsByTagName("Student");
    Element student = null;
    

     for(int i=0; i<students.getLength();i++){
        student = (Element) students.item(i);
        Element courseElement = doc.createElement("course");
        courseElement.appendChild(doc.createTextNode("PE"));
        student.appendChild(courseElement);
    }
} 

private static void deleteElement(Document doc) {
    NodeList students = doc.getElementsByTagName("Student");
    Element student = null;

    for(int i=0; i<students.getLength();i++){
    	student = (Element) students.item(i);
        Node genderNode = student.getElementsByTagName("gender").item(0);
        student.removeChild(genderNode);
    }
    
}

private static void updateElementValue(Document doc) {
    NodeList students = doc.getElementsByTagName("Students");
    Element student = null;
    for(int i=0; i<students.getLength();i++){
    	student = (Element) students.item(i);
        Node name = student.getElementsByTagName("name").item(0).getFirstChild();
        name.setNodeValue(name.getNodeValue().toUpperCase());
    }
}

private static void updateAttributeValue(Document doc) {
    NodeList students = doc.getElementsByTagName("Students");
    Element student = null;
    for(int i=0; i<students.getLength();i++){
        student = (Element) students.item(i);
        String gender = student.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue();
        if(gender.equalsIgnoreCase("Male")){
        	student.setAttribute("schoolnumber", "Male"+student.getAttribute("schoolnumber"));
        }else{
        	student.setAttribute("schoolnumber", "Female"+student.getAttribute("schoolnumber"));
        }
    }
}

}

