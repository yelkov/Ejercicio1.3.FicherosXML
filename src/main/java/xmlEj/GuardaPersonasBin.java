package xmlEj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GuardaPersonasBin {
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

        guardarPersonas(grupo);
        guardarDatosPersonas(grupo);

    }
    public  static void guardarPersonas(List<Persona> personas) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/personas.bin"))){
            for (Persona persona : personas){
                oos.writeObject(persona);
            }
        }catch (IOException e){
            System.out.println("Error al guardar" + e.getMessage());
        }

    }

    public  static void guardarDatosPersonas(List<Persona> personas) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/personasDatos.bin"))){
            for (Persona persona : personas){
                oos.writeUTF(persona.getNombre());
                oos.writeInt(persona.getEdad());
            }
        }catch (IOException e){
            System.out.println("Error al guardar" + e.getMessage());
        }

    }
}