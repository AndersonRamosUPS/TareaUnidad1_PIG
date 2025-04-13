package vista;

import javax.swing.*;
import java.awt.*;

public class ContactoVista extends JFrame {

    private JLabel lb_nombres, lb_telefono, lb_email, lb_buscar;
    private JLabel lbl_total, lbl_favoritos, lbl_categoriaFamilia, lbl_categoriaAmigos, lbl_categoriaTrabajo, lbl_categoriaOtros;
    private JTextField txt_nombre,txt_telefono,txt_correo, txt_buscar;
    private JButton btn_add, btn_editar, btn_eliminar;
    private JCheckBox chb_favoritos;
    private JComboBox cmb_categoria;
    private JTable tablaContactos;
    private JScrollPane scrollTabla; // para que tenga scroll
    private JPanel panelFormulario, panelLista;
    private JTabbedPane tpnTabs;
    private JPopupMenu menuContextual = new JPopupMenu();
    private JMenuItem exportarItem = new JMenuItem("Exportar a CSV");
    private JProgressBar barraProgreso;

    public ContactoVista(){
        setTitle("GESTION DE CONTACTOS");
        setBounds(0,0,800,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        //CREAR PESTANIAS
        tpnTabs = new JTabbedPane();

        //CREAMOS LOS JPANEL
        panelFormulario = new JPanel(null);
        panelLista = new JPanel(null);

        //CREACION DE LA BARRA DE PROGRESO
        barraProgreso = new JProgressBar();
        barraProgreso.setBounds(150, 165, 500, 20);
        barraProgreso.setStringPainted(true);
        panelFormulario.add(barraProgreso);

        // ---------- COMPONENTES PARA PANEL FORMULARIO ---------- //
        lb_nombres = new JLabel("NOMBRES:");
        lb_nombres.setBounds(20,30, 200,30);
        panelFormulario.add(lb_nombres);

        lb_telefono = new JLabel("TELEFONO:");
        lb_telefono.setBounds(20,60,200,30);
        panelFormulario.add(lb_telefono);

        lb_email = new JLabel("EMAIL:");
        lb_email.setBounds(20,90,200,30);
        panelFormulario.add(lb_email);

        lb_buscar = new JLabel("BUSCAR POR NOMBRE:");
        lb_buscar.setBounds(20,550,200,30);
        panelFormulario.add(lb_buscar);

        //Creamos los campos de texto
        txt_nombre = new JTextField();
        txt_nombre.setBounds(100,30,300,20);
        panelFormulario.add(txt_nombre);

        txt_telefono = new JTextField();
        txt_telefono.setBounds(100,60, 300,20);
        panelFormulario.add(txt_telefono);

        txt_correo = new JTextField();
        txt_correo.setBounds(100,90,300,20);
        panelFormulario.add(txt_correo);

        txt_buscar = new JTextField();
        txt_buscar.setBounds(170,555,600,20);
        panelFormulario.add(txt_buscar);

        //Creamos los botones
        btn_add = new JButton("AGREGAR");
        btn_add.setBounds(425,60,100,30);
        panelFormulario.add(btn_add);

        btn_editar = new JButton("MODIFICAR");
        btn_editar.setBounds(540,60,100,30);
        panelFormulario.add(btn_editar);

        btn_eliminar = new JButton("ELIMINAR");
        btn_eliminar.setBounds(655,60,100,30);
        panelFormulario.add(btn_eliminar);

        //Creamos el checkbox
        chb_favoritos = new JCheckBox("CONTACTO FAVORITO");
        chb_favoritos.setBounds(20,140,150,20);
        panelFormulario.add(chb_favoritos);

        //Creamos la categoria
        cmb_categoria = new JComboBox<>();  //Familia, amigos, trabajo
        cmb_categoria.setBounds(200,140,200,20);
        cmb_categoria.addItem("Familia");
        cmb_categoria.addItem("Amigos");
        cmb_categoria.addItem("Trabajo");
        cmb_categoria.addItem("Otros");
        panelFormulario.add(cmb_categoria);

        // Crear la tabla (por ahora vacía)
        tablaContactos = new JTable();
        scrollTabla = new JScrollPane(tablaContactos);
        scrollTabla.setBounds(20, 190, 750, 350);
        panelFormulario.add(scrollTabla);

        //Menu Contextual
        menuContextual.add(exportarItem);
        tablaContactos.setComponentPopupMenu(menuContextual);

        //**********PANEL DE ESTADISTICAS***********//
        lbl_total = new JLabel("Total de contactos: 0");
        lbl_total.setBounds(50, 40, 300, 30);
        panelLista.add(lbl_total);

        lbl_favoritos = new JLabel("Contactos favoritos: 0");
        lbl_favoritos.setBounds(50, 80, 300, 30);
        panelLista.add(lbl_favoritos);

        lbl_categoriaFamilia = new JLabel("Familia: 0");
        lbl_categoriaFamilia.setBounds(50, 120, 300, 30);
        panelLista.add(lbl_categoriaFamilia);

        lbl_categoriaAmigos = new JLabel("Amigos: 0");
        lbl_categoriaAmigos.setBounds(50, 160, 300, 30);
        panelLista.add(lbl_categoriaAmigos);

        lbl_categoriaTrabajo = new JLabel("Trabajo: 0");
        lbl_categoriaTrabajo.setBounds(50, 200, 300, 30);
        panelLista.add(lbl_categoriaTrabajo);

        lbl_categoriaOtros = new JLabel("Otros: 0");
        lbl_categoriaOtros.setBounds(50, 240, 300, 30);
        panelLista.add(lbl_categoriaOtros);


        // ---------- AGREGAMOS LOS PANELES AL TABBEDPANE ---------- //
        tpnTabs.add( "Contactos",panelFormulario);
        tpnTabs.add("Estadísticas",panelLista);

        // ---------- AGREGAMOS EL TABBEDPANE AL FRAME ----------
        add(tpnTabs);

        setVisible(true);
    }

    public JButton getBtnAgregar() {
        return btn_add;
    }

    public JButton getBtnEditar() {
        return btn_editar;
    }

    public JButton getBtnEliminar() {
        return btn_eliminar;
    }

    public JTextField getTxtNombre() {
        return txt_nombre;
    }

    public JTextField getTxtTelefono() {
        return txt_telefono;
    }

    public JTextField getTxtCorreo() {
        return txt_correo;
    }

    public JTextField getTxtBuscar() {
        return txt_buscar;
    }

    public JCheckBox getChbFavoritos() {
        return chb_favoritos;
    }

    public JComboBox getCmbCategoria() {
        return cmb_categoria;
    }

    public JTable getTablaContactos() {
        return tablaContactos;
    }

    public JMenuItem getItemExportarCSV() {
        return exportarItem;
    }

    public JProgressBar getBarraProgreso() {
        return barraProgreso;
    }

    public JLabel getLblTotal() {
        return lbl_total;
    }

    public JLabel getLblFavoritos() {
        return lbl_favoritos;
    }

    public JLabel getLblCategoriaFamilia() {
        return lbl_categoriaFamilia;
    }

    public JLabel getLblCategoriaAmigos() {
        return lbl_categoriaAmigos;
    }

    public JLabel getLblCategoriaTrabajo() {
        return lbl_categoriaTrabajo;
    }

    public JLabel getLblCategoriaOtros() {
        return lbl_categoriaOtros;
    }

// etc.

}
