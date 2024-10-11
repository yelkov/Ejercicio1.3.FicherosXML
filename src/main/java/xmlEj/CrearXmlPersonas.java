package xmlEj;

import org.w3c.dom.*;

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

    private static String RUTA_BIN = "src/main/resources/personas.bin";
    private static String RUTA_XML = "src/main/resources/personas.xml";

    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<Persona>();
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(RUTA_BIN))){
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
        /*
        List<xmlEj.Persona> personasLeidas = leerPersonasXml();
        for (xmlEj.Persona persona : personasLeidas){
            System.out.println(persona.toString());
        }
        */

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
            Result resultado = new StreamResult(new File(RUTA_XML));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, resultado);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Persona> leerPersonasXml(){
        List<Persona> personas = new ArrayList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(RUTA_XML));
            documento.getDocumentElement().normalize();

            NodeList nodosPersonas = documento.getElementsByTagName("persona");

            for(int i = 0; i < nodosPersonas.getLength(); i++){
                Element elementoPersona = (Element) nodosPersonas.item(i);
                Element elementoNombre = (Element) elementoPersona.getElementsByTagName("nombre").item(0);
                String nombre = elementoNombre.getTextContent();
                Element elementoEdad = (Element) elementoPersona.getElementsByTagName("edad").item(0);
                String edad = elementoEdad.getTextContent();
                personas.add(new Persona(nombre, Integer.parseInt(edad)));
            }


        } catch (Exception e) {
            System.out.println("No se puede leer el archivo XML " + e.getMessage());
        }
        return personas;
    }
}
