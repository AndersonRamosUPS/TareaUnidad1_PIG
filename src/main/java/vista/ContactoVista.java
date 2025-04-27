package vista;

import javax.swing.*;
import java.awt.*;

public class ContactoVista extends JFrame {

    private JLabel lb_nombres, lb_telefono, lb_email, lb_buscar, lbl_FamiliaIco, lbl_AmigosIco, lbl_TrabajoIco, lbl_FavoritoIco;
    private JLabel lbl_total, lbl_favoritos, lbl_categoriaFamilia, lbl_categoriaAmigos, lbl_categoriaTrabajo, lbl_categoriaOtros;
    private JTextField txt_nombre, txt_telefono, txt_correo, txt_buscar;
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
    

    public ContactoVista() {
        setTitle("GESTION DE CONTACTOS");
        setBounds(0, 0, 800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        //CREAR PESTANIAS
        tpnTabs = new JTabbedPane();

        //CREAMOS LOS JPANEL
        panelFormulario = new JPanel(null);
        panelFormulario.setLayout(null);

        panelLista = new JPanel();
        panelLista.setLayout(new BorderLayout());

        //CREACION DE LA BARRA DE PROGRESO
        barraProgreso = new JProgressBar();
        barraProgreso.setBounds(150, 165, 500, 20);
        barraProgreso.setStringPainted(true);
        panelFormulario.add(barraProgreso);

        // ---------- COMPONENTES PARA PANEL FORMULARIO ---------- //
        lb_nombres = new JLabel("NOMBRES:");
        lb_nombres.setBounds(20, 30, 200, 30);
        panelFormulario.add(lb_nombres);

        lb_telefono = new JLabel("TELEFONO:");
        lb_telefono.setBounds(20, 60, 200, 30);
        panelFormulario.add(lb_telefono);

        lb_email = new JLabel("EMAIL:");
        lb_email.setBounds(20, 90, 200, 30);
        panelFormulario.add(lb_email);

        lb_buscar = new JLabel("BUSCAR POR NOMBRE:");

        //Creamos los campos de texto
        txt_nombre = new JTextField();
        txt_nombre.setBounds(100, 30, 300, 20);
        panelFormulario.add(txt_nombre);

        txt_telefono = new JTextField();
        txt_telefono.setBounds(100, 60, 300, 20);
        panelFormulario.add(txt_telefono);

        txt_correo = new JTextField();
        txt_correo.setBounds(100, 90, 300, 20);
        panelFormulario.add(txt_correo);

        txt_buscar = new JTextField();

        //Creamos los botones
        btn_add = new JButton("AGREGAR");
        btn_add.setIcon(redimensionarIcono("/img/agregar-usuario.png", 48, 48));

        btn_editar = new JButton("MODIFICAR");
        btn_editar.setIcon(redimensionarIcono("/img/editar-informacion.png", 48, 48));

        btn_eliminar = new JButton("ELIMINAR");
        btn_eliminar.setIcon(redimensionarIcono("/img/eliminar-contacto.png", 48, 48));

        // Estilo vertical: ícono arriba, texto abajo
        btn_add.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_add.setHorizontalTextPosition(SwingConstants.CENTER);

        btn_editar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_editar.setHorizontalTextPosition(SwingConstants.CENTER);

        btn_eliminar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn_eliminar.setHorizontalTextPosition(SwingConstants.CENTER);

        //Creamos el checkbox
        chb_favoritos = new JCheckBox("CONTACTO FAVORITO");
        chb_favoritos.setBounds(20, 140, 150, 20);
        panelFormulario.add(chb_favoritos);

        //Creamos la categoria
        cmb_categoria = new JComboBox<>();  //Familia, amigos, trabajo
        cmb_categoria.setBounds(200, 140, 200, 20);
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
        tpnTabs.add("Contactos", panelFormulario);
        tpnTabs.add("Estadísticas", panelLista);

        // ---------------------Crear panelBusqueda--------------------------//
        JPanel panelBusqueda = new JPanel(new BorderLayout(5, 5));
        panelBusqueda.setBackground(new Color(0xFFC107));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel lblBuscarNombre = new JLabel("NOMBRES:");
        lblBuscarNombre.setFont(new Font("Helvetica", Font.BOLD, 14));

        JLabel lblIconoBuscar = new JLabel(redimensionarIcono("/img/buscar.png", 32, 32));
        lblIconoBuscar.setHorizontalAlignment(SwingConstants.CENTER);

        txt_buscar.setPreferredSize(new Dimension(400, 30));

        panelBusqueda.add(lblBuscarNombre, BorderLayout.WEST);
        panelBusqueda.add(txt_buscar, BorderLayout.CENTER);
        panelBusqueda.add(lblIconoBuscar, BorderLayout.EAST);

        // -----Crear panel para los iconos de categorías, pero ahora en columna----------//
        JPanel panelCategorias = new JPanel();
        panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
        panelCategorias.setOpaque(false); // fondo transparente

        // Crear los JLabel de iconos
        lbl_FamiliaIco = new JLabel();
        lbl_AmigosIco = new JLabel();
        lbl_TrabajoIco = new JLabel();
        lbl_FavoritoIco = new JLabel();

        // Asignar los íconos a cada JLabel
        lbl_FamiliaIco.setIcon(redimensionarIcono("/img/familia.png", 48, 48));
        lbl_AmigosIco.setIcon(redimensionarIcono("/img/amigo.png", 48, 48));
        lbl_TrabajoIco.setIcon(redimensionarIcono("/img/amigo-trabajo.png", 48, 48));
        lbl_FavoritoIco.setIcon(redimensionarIcono("/img/favorito.png", 48, 48));

        // Añadimos íconos uno debajo del otro
        panelCategorias.add(lbl_FamiliaIco);
        panelCategorias.add(Box.createVerticalStrut(10)); // Espacio entre iconos
        panelCategorias.add(lbl_AmigosIco);
        panelCategorias.add(Box.createVerticalStrut(10));
        panelCategorias.add(lbl_TrabajoIco);
        panelCategorias.add(Box.createVerticalStrut(10));
        panelCategorias.add(lbl_FavoritoIco);

        GroupLayout layout = new GroupLayout(panelFormulario);
        panelFormulario.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(lb_nombres)
                                                        .addComponent(lb_telefono)
                                                        .addComponent(lb_email)
                                                        .addComponent(chb_favoritos))
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(txt_nombre)
                                                        .addComponent(txt_telefono)
                                                        .addComponent(txt_correo)
                                                        .addComponent(cmb_categoria)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btn_add)
                                                .addComponent(btn_editar)
                                                .addComponent(btn_eliminar)))
                                .addComponent(panelCategorias) // <-- iconos agrupados
                        )
                        .addComponent(panelBusqueda) // <-- esto ocupará todo el ancho
                        .addComponent(barraProgreso)
                        .addComponent(scrollTabla)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_nombres)
                                                .addComponent(txt_nombre))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_telefono)
                                                .addComponent(txt_telefono))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lb_email)
                                                .addComponent(txt_correo))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(chb_favoritos)
                                                .addComponent(cmb_categoria))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(btn_add)
                                                .addComponent(btn_editar)
                                                .addComponent(btn_eliminar)))
                                .addComponent(panelCategorias) // iconos aquí agrupados
                        )
                        .addComponent(panelBusqueda)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollTabla, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        panelLista.add(lbl_total, BorderLayout.NORTH);
        panelLista.add(scrollTabla, BorderLayout.CENTER);

        // ---------- AGREGAMOS EL TABBEDPANE AL FRAME ----------
        add(tpnTabs);

        aplicarEstiloVisual();

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

    public JLabel getLblFamiliaIco() {
        return lbl_FamiliaIco;
    }

    public JLabel getLblAmigosIco() {
        return lbl_AmigosIco;
    }

    public JLabel getLblTrabajoIco() {
        return lbl_TrabajoIco;
    }

    public JLabel getLblFavoritoIco() {
        return lbl_FavoritoIco;
    }

    private void aplicarEstiloVisual() {
        //Paleta de colores
        Color fondoPrincipal = new Color(0x0A2342);        // Azul oscuro
        Color fondoFormulario = new Color(0x46465C);       // Gris/púrpura oscuro
        Color colorTexto = new Color(0xFFFFFF);            // Blanco
        Color colorBotonAgregar = new Color(0x28A745);     // Verde
        Color colorBotonEditar = new Color(0x007BFF);      // Azul
        Color colorBotonEliminar = new Color(0xDC3545);    // Rojo
        Color colorBusqueda = new Color(0xFFC107);         // Amarillo
        Color fondoCampos = new Color(0xFFFFFF);           // Blanco para campos
        Color textoOscuro = new Color(0x333333);           // Texto gris oscuro

        //Tipografías
        Font fuenteLabel = new Font("Helvetica", Font.BOLD, 14);
        Font fuenteCampo = new Font("Helvetica", Font.PLAIN, 12);
        Font fuenteBoton = new Font("Helvetica", Font.BOLD, 13);

        //Fondos
        getContentPane().setBackground(fondoPrincipal);
        panelFormulario.setBackground(fondoFormulario);
        panelLista.setBackground(fondoPrincipal);
        //tpnTabs.setBackground(fondoPrincipal);

        //Etiquetas
        JLabel[] etiquetas = {
            lb_nombres, lb_telefono, lb_email, lb_buscar,
            lbl_total, lbl_favoritos, lbl_categoriaFamilia,
            lbl_categoriaAmigos, lbl_categoriaTrabajo, lbl_categoriaOtros
        };
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteLabel);
            lbl.setForeground(colorTexto);
        }

        //Campos de texto
        JTextField[] campos = {txt_nombre, txt_telefono, txt_correo, txt_buscar};
        for (JTextField txt : campos) {
            txt.setFont(fuenteCampo);
            txt.setBackground(fondoCampos);
            txt.setForeground(textoOscuro);
            txt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        //CheckBox y ComboBox
        chb_favoritos.setFont(fuenteCampo);
        chb_favoritos.setForeground(colorTexto);
        chb_favoritos.setBackground(fondoFormulario);
        cmb_categoria.setFont(fuenteCampo);
        cmb_categoria.setBackground(fondoCampos);

        //Botones
        btn_add.setBackground(colorBotonAgregar);
        btn_add.setForeground(Color.WHITE);
        btn_add.setFont(fuenteBoton);

        btn_editar.setBackground(colorBotonEditar);
        btn_editar.setForeground(Color.WHITE);
        btn_editar.setFont(fuenteBoton);

        btn_eliminar.setBackground(colorBotonEliminar);
        btn_eliminar.setForeground(Color.WHITE);
        btn_eliminar.setFont(fuenteBoton);

        //Tabla
        tablaContactos.setFont(fuenteCampo);
        tablaContactos.setForeground(textoOscuro);
        tablaContactos.setBackground(Color.WHITE);
        tablaContactos.setGridColor(Color.LIGHT_GRAY);
        tablaContactos.setRowHeight(25);
        tablaContactos.getTableHeader().setFont(fuenteLabel);
        tablaContactos.getTableHeader().setBackground(new Color(0xCCCCCC));
        tablaContactos.getTableHeader().setForeground(Color.BLACK);

        //Barra de progreso
        barraProgreso.setBackground(Color.LIGHT_GRAY);
        barraProgreso.setForeground(new Color(0x007BFF)); // Azul

        //Scroll tabla
        scrollTabla.setBackground(Color.WHITE);
    }

    // Método para redimensionar iconos
    private ImageIcon redimensionarIcono(String ruta, int ancho, int alto) {
        ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

}
