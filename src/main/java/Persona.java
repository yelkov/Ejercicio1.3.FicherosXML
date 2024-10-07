import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;

public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    String nombre;
    Integer edad;

    public Persona() {}

    public Persona(String nombre, Integer edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void guardar(String ruta) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + ".bin", true));
        oos.writeObject(this);
    }
}
