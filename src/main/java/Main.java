import controlador.LogicaContactos;
import vista.ContactoVista;

public class Main {

    public static void main(String[] args) {
        ContactoVista vista = new ContactoVista();
        new LogicaContactos(vista);
        vista.setVisible(true);
    }
}
