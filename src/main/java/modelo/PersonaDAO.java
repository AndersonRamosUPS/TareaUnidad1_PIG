package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    private static final String RUTA_DIRECTORIO = "c:/gestionContactos/";
    private static final String RUTA_CSV = RUTA_DIRECTORIO + "datosContactos.csv";

    // Bloque estático para garantizar creación del archivo
    static {
        try {
            File directorio = new File(RUTA_DIRECTORIO);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            File archivo = new File(RUTA_CSV);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Persona> leerArchivo() {
        List<Persona> personas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {    //Leer textos desde archivos
            String linea;
            while ((linea = br.readLine()) != null) {   //Leer archivo linea por linea
                if (linea.trim().isEmpty()) {
                    continue;   //Si la linea esta vacia pasamos a la siguiente
                }
                String[] campos = linea.split(",");     //Corta la cadena cada vez que encuentra una coma
                if (campos.length >= 5) {   // Son 4 elementos, pero 5 por si una linea incompleta, por seguridad
                    String nombre = campos[0];
                    String telefono = campos[1];
                    String email = campos[2];
                    String categoria = campos[3];
                    boolean favorito = Boolean.parseBoolean(campos[4]);

                    personas.add(new Persona(nombre, telefono, email, categoria, favorito));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personas;
    }
    
    // Metodo sincronizado para evitar conflictos si dos hilos escriben al archivo a la misma vez
    public synchronized void escribirArchivo(Persona persona) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV, true))) {
            bw.write(persona.getNombre() + ","
                    + persona.getTelefono() + ","
                    + persona.getEmail() + ","
                    + persona.getCategoria() + ","
                    + persona.isFavorito());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Tambien sincronizamos la sobrescritura del archivo completo
    public synchronized void actualizarContactos(List<Persona> personas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV))) {
            for (Persona persona : personas) {
                bw.write(persona.getNombre() + ","
                        + persona.getTelefono() + ","
                        + persona.getEmail() + ","
                        + persona.getCategoria() + ","
                        + persona.isFavorito());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
