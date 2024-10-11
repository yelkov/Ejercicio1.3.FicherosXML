package xmlEj.pedidos;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import xmlEj.XmlPrinter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrearPedidos {
    private static String RUTA_DAT = "src/main/resources/xmlEj/pedidos/pedidos.dat";
    private static String RUTA_XML = "src/main/resources/xmlEj/pedidos/pedidos.xml";

    public static void main(String[] args) {
        Producto monitor = new Producto(100, "Monitor",100.0);
        Producto raton = new Producto(101, "Ratón",10.0);
        Producto portatil = new Producto(102, "Portatil",600.0);
        Producto tablet = new Producto(103, "Tablet",400.0);
        Producto teclado = new Producto(104, "Teclado",200.0);

        Pedido pedido1 = new Pedido(1,"Cliente1");
        pedido1.addProducto(monitor).addProducto(raton);

        Pedido pedido2 = new Pedido(2,"Cliente2");
        pedido2.addProducto(portatil).addProducto(tablet);

        Pedido pedido3 = new Pedido(3,"Cliente2");
        pedido3.addProducto(portatil).addProducto(tablet);

        Pedido pedido4 = new Pedido(4,"Cliente3");
        pedido4.addProducto(monitor).addProducto(raton);

        Pedido pedido5 = new Pedido(5,"Cliente4");
        pedido5.addProducto(teclado);

        Pedido pedido6 = new Pedido(6,"Cliente5");
        pedido6.addProducto(monitor).addProducto(raton);

        List<Pedido> pedidos = new ArrayList<Pedido>();
        pedidos.add(pedido1);
        pedidos.add(pedido2);
        pedidos.add(pedido3);
        pedidos.add(pedido4);
        pedidos.add(pedido5);
        pedidos.add(pedido6);

        guardaPedidosDat(pedidos);
        List<Pedido> pedidosLeidos = leerPedidosDat(RUTA_DAT);
        System.out.println(pedidosLeidos.toString());
        crearXmlPedidos(pedidosLeidos);

        Scanner sc = new Scanner(System.in);
        System.out.println("\nDesea printar un xml creado? (s/N)");
        String opcion = sc.nextLine();

        if (opcion.equals("s")) {
            XmlPrinter.main(args);
        }else{
            System.out.println("\nHasta luego!");
        }
    }

    public static void guardaPedidosDat(List<Pedido> pedidos){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_DAT))){
            for (Pedido pedido : pedidos) {
                oos.writeObject(pedido);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar el .dat");
            e.printStackTrace();
        }
        System.out.println("Se han guardado los pedidos.");

    }

    public static List<Pedido> leerPedidosDat(String ruta){
        List<Pedido> pedidos = new ArrayList<>();


        try(FileInputStream fis = new FileInputStream(ruta);
            ObjectInputStream lector = new ObjectInputStream(fis)){
            while(fis.available()>0){
                Object o = lector.readObject();
                if(o instanceof Pedido){
                    pedidos.add((Pedido)o);
                }
            }

        }
        catch (IOException e) {
            System.out.println("Error al leer el .dat");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.out.println("No se encuentra el .dat");
        }
        return pedidos;
    }

    public static void crearXmlPedidos(List<Pedido> pedidos) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            DOMImplementation domImpl = db.getDOMImplementation();
            Document doc = domImpl.createDocument(null, "pedidos",null);
            doc.setXmlVersion("1.0");

            Element raiz = doc.getDocumentElement();
            for (Pedido pedido : pedidos) {
                Element elementoPedido = doc.createElement("pedido");
                raiz.appendChild(elementoPedido);

                Element idPedidoElemento = doc.createElement("idPedido");
                elementoPedido.appendChild(idPedidoElemento);

                Text textoIdPedido = doc.createTextNode(pedido.getIdPedido().toString());
                idPedidoElemento.appendChild(textoIdPedido);

                Element nombreClienteElemento = doc.createElement("nombreCliente");
                elementoPedido.appendChild(nombreClienteElemento);

                Text textoNombreCliente = doc.createTextNode(pedido.getNombreCliente());
                nombreClienteElemento.appendChild(textoNombreCliente);

                Element elementoProductos = doc.createElement("productos");
                elementoPedido.appendChild(elementoProductos);

                for ( Producto producto : pedido.getProductos() ) {
                    Element productoElemento = doc.createElement("producto");
                    elementoProductos.appendChild(productoElemento);

                    Element idProductoElemento = doc.createElement("idProducto");
                    productoElemento.appendChild(idProductoElemento);

                    Text textoIdProducto = doc.createTextNode(producto.getIdProducto().toString());
                    idProductoElemento.appendChild(textoIdProducto);

                    Element descripcionElemento = doc.createElement("descripcion");
                    productoElemento.appendChild(descripcionElemento);

                    Text textoDescripcion = doc.createTextNode(producto.getDescripcion());
                    descripcionElemento.appendChild(textoDescripcion);

                    Element precioElemento = doc.createElement("precio");
                    productoElemento.appendChild(precioElemento);

                    Text textoPrecio = doc.createTextNode(producto.getPrecio().toString());
                    precioElemento.appendChild(textoPrecio);
                }

            }
            Source source = new DOMSource(doc);
            Result resultado = new StreamResult(new File(RUTA_XML));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source,resultado);

            System.out.println("\nSe ha guardado el xml de manera correcta.");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        }catch (TransformerConfigurationException e) {
            e.printStackTrace();

        }catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}
