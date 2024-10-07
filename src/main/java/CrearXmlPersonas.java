import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrearXmlPersonas {

    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<Persona>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream("src/main/resources/personas.bin"))){
            while (true){
                Object o = lector.readObject();
                if(o instanceof Persona){
                    personas.add((Persona)o);
                }
            }
        }
        catch (EOFException e){
            System.out.println("Fin del archivo.");
        }catch (IOException e){
            System.out.println("No se puede leer el archivo IO " + e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println("No se puede leer el archivo "+ e.getMessage());
        }
        crearXml(personas);
    }

    public static void crearXml(List<Persona> personas){
        try {
            //Crear DOM en memoria
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "personas", null);
            document.setXmlVersion("1.0");
            Element raiz = document.getDocumentElement();

            for (Persona persona : personas) {
                Element personaElemento = document.createElement("persona");
                raiz.appendChild(personaElemento);

                Element nombre = document.createElement("nombre");
                personaElemento.appendChild(nombre);
                Text nombreTexto = document.createTextNode(persona.getNombre());
                nombre.appendChild(nombreTexto);

                Element edad = document.createElement("edad");
                personaElemento.appendChild(edad);

                Text edadTexto = document.createTextNode(String.valueOf(persona.getEdad()));
                edad.appendChild(edadTexto);

            }
            Source source = new DOMSource(document);
            Result resultado = new StreamResult(new File("src/main/resources/personas.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, resultado);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
