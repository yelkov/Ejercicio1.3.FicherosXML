import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Persona jose = new Persona("José",21);
        Persona ana = new Persona("Ana",35);
        Persona david = new Persona("David",25);
        Persona evan = new Persona("Evan",22);

        List<Persona> grupo  = new ArrayList<Persona>();
        grupo.add(jose);
        grupo.add(ana);
        grupo.add(david);
        grupo.add(evan);

        for (Persona persona : grupo) {
           try {
               persona.guardar("src/main/resources/personas");
           }catch (IOException e){
               System.out.println("Error al guardar el archivo." + e.getMessage());
           }
        }

    }
}