import controlador.LogicaContactos;
import vista.ContactoVista;
import vista.VentanaInternacionalizacion;

public class Main {

    public static void main(String[] args) {
        ContactoVista vista = new ContactoVista();
        new LogicaContactos(vista);
        vista.setVisible(true);
        
        // Lanzar Ventana de Internacionalización (idiomas)
        VentanaInternacionalizacion ventanaIdiomas = new VentanaInternacionalizacion();
        ventanaIdiomas.setVisible(true);
    }
}
