package pedidos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrearPedidos {
    private static String RUTA_DAT = "src/main/resources/pedidos/pedidos.dat";

    public static void main(String[] args) {
        Producto monitor = new Producto(100, "Monitor",100.0);
        Producto raton = new Producto(101, "Ratón",10.0);
        Producto portatil = new Producto(102, "Portatil",600.0);
        Producto tablet = new Producto(103, "Tablet",400.0);
        Producto teclado = new Producto(104, "Teclado",200.0);

        Pedido pedido1 = new Pedido(1,"Cliente1");
        pedido1.addProducto(monitor);
        pedido1.addProducto(raton);

        Pedido pedido2 = new Pedido(2,"Cliente2");
        pedido2.addProducto(portatil);
        pedido2.addProducto(tablet);

        Pedido pedido3 = new Pedido(3,"Cliente2");
        pedido3.addProducto(portatil);
        pedido3.addProducto(tablet);

        Pedido pedido4 = new Pedido(4,"Cliente3");
        pedido4.addProducto(monitor);
        pedido4.addProducto(raton);

        Pedido pedido5 = new Pedido(5,"Cliente4");
        pedido5.addProducto(teclado);

        Pedido pedido6 = new Pedido(6,"Cliente5");
        pedido6.addProducto(monitor);
        pedido6.addProducto(raton);

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
        try(ObjectInputStream lector = new ObjectInputStream(new FileInputStream(ruta))){
            while(true){
                Object o = lector.readObject();
                if(o instanceof Pedido){
                    pedidos.add((Pedido)o);
                }
            }

        }catch (EOFException e){
            System.out.println("Archivo leído.");
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


}
