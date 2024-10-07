import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Persona persona1 = new Persona("José",21);
        Persona persona2 = new Persona("Ana",35);
        Persona persona3 = new Persona("David",25);
        Persona persona4 = new Persona("Evan",22);

        List<Persona> grupo  = new ArrayList<Persona>();
        grupo.add(persona1);
        grupo.add(persona2);
        grupo.add(persona3);
        grupo.add(persona4);

        for (Persona persona : grupo) {
           try {
               persona.guardar("src/main/resources/grupo");
           }catch (IOException e){
               System.out.println("Error al guardar el archivo." + e.getMessage());
           }
        }

    }
}