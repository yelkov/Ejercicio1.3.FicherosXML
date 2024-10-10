import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContadorXml {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un archivo xml: ");
        String archivo = sc.nextLine();

        if (archivo.endsWith(".xml")) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbf.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(archivo));

                Integer contador = 0;

                NodeList nodeList = doc.getChildNodes();
                List<String> etiquetas = new ArrayList<>();

                contador = contarElementos(nodeList,contador,etiquetas);

                System.out.println("El número de elementos del xml es: " + contador.toString());
                System.out.println("Las etiquetas que se han encontrado son: ");
                printarEtiquetas(etiquetas);

            } catch (Exception e) {
                System.out.println("Error al leer xml" + e.getMessage());
            }
        }else{
            System.out.println("El archivo no es un xml");
        }
    }

    public static Integer contarElementos(NodeList nodeList, Integer contador, List<String> etiquetas){
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(!etiquetas.contains(element.getNodeName())){
                    contador += 1;
                    etiquetas.add(element.getNodeName());
                }

                if (element.hasChildNodes()){
                    NodeList elementChilds = element.getChildNodes();
                    contador = contarElementos(elementChilds,contador,etiquetas);
                }
            }
        }
        return contador;
    }
    public static void printarEtiquetas(List<String> etiquetas){
        for (String etiqueta : etiquetas){
            System.out.println(etiqueta);
        }
    }
}
