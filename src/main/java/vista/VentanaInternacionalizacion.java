package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class VentanaInternacionalizacion extends JFrame {

    // Componentes
    private JLabel lblNombres;
    private JLabel lblApellidos;
    private JLabel lblGenero;
    private JLabel lblMostrar;
    private JLabel lblIdioma;
    private ResourceBundle bundle;

    private JTextField txtNombres;
    private JTextField txtApellidos;

    private JRadioButton rbtFemenino;
    private JRadioButton rbtMasculino;
    private ButtonGroup grupoGenero;

    private JComboBox<String> cmbIdiomas;
    private JButton btnMensaje;

    public VentanaInternacionalizacion() {
        setTitle("INTERNACIONALIZACION");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 300);
        // Inicializar el bundle en español por defecto
        bundle = ResourceBundle.getBundle("idiomas.mensajes", new Locale("es"));
        setLocationRelativeTo(null);

        // Inicializar componentes
        lblNombres = new JLabel("NOMBRES:");
        lblApellidos = new JLabel("APELLIDOS:");
        lblGenero = new JLabel("GENERO:");
        lblMostrar = new JLabel("MOSTRAR:");
        lblIdioma = new JLabel("Escoja un idioma:");

        txtNombres = new JTextField(20);
        txtApellidos = new JTextField(20);

        rbtFemenino = new JRadioButton("FEMENINO");
        rbtMasculino = new JRadioButton("MASCULINO");
        grupoGenero = new ButtonGroup();
        grupoGenero.add(rbtFemenino);
        grupoGenero.add(rbtMasculino);

        cmbIdiomas = new JComboBox<>(new String[]{"ES", "EN", "PT"});
        btnMensaje = new JButton("MENSAJE");

// ⬇️ Primero CREAS el panel
        JPanel panel = new JPanel();

// ⬇️ Luego APLICAS colores y fuentes
        panel.setBackground(new Color(0x413F58)); // Fondo oscuro

        lblNombres.setForeground(Color.WHITE);
        lblApellidos.setForeground(Color.WHITE);
        lblGenero.setForeground(Color.WHITE);
        lblMostrar.setForeground(Color.WHITE);
        lblIdioma.setForeground(Color.WHITE);

        cmbIdiomas.setBackground(new Color(0xFFECB3)); // Fondo clarito
        cmbIdiomas.setForeground(Color.BLACK);

        btnMensaje.setBackground(new Color(0x007BFF)); // Botón azul
        btnMensaje.setForeground(Color.WHITE);
        btnMensaje.setFocusPainted(false);
        btnMensaje.setFont(new Font("Helvetica", Font.BOLD, 14));

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lblIdioma)
                                .addComponent(lblNombres)
                                .addComponent(lblApellidos)
                                .addComponent(lblGenero)
                                .addComponent(lblMostrar))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(cmbIdiomas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombres)
                                .addComponent(txtApellidos)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbtFemenino)
                                        .addComponent(rbtMasculino))
                                .addComponent(btnMensaje))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblIdioma)
                                .addComponent(cmbIdiomas))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblNombres)
                                .addComponent(txtNombres))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblApellidos)
                                .addComponent(txtApellidos))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblGenero)
                                .addComponent(rbtFemenino)
                                .addComponent(rbtMasculino))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMostrar)
                                .addComponent(btnMensaje))
        );

        btnMensaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener nombre y apellido
                String nombres = txtNombres.getText();
                String apellidos = txtApellidos.getText();

                // Detectar idioma seleccionado
                String idiomaSeleccionado = (String) cmbIdiomas.getSelectedItem();
                String patronFecha;

                switch (idiomaSeleccionado) {
                    case "EN" ->
                        patronFecha = "MM/dd/yyyy HH:mm:ss"; // Formato americano
                    case "PT" ->
                        patronFecha = "dd/MM/yyyy HH:mm:ss"; // Formato latino
                    default ->
                        patronFecha = "dd/MM/yyyy HH:mm:ss";   // Español
                }

                // Obtener fecha y hora actual
                Date fechaActual = new Date();
                SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
                String fechaHora = formato.format(fechaActual);

                // Cargar mensajes traducidos desde ResourceBundle
                String saludo = bundle.getString("mensaje.bienvenida");
                String fechaHoraTexto = bundle.getString("mensaje.fecha_hora");
                String tituloMensaje = bundle.getString("titulo_mensaje");

                // Mostrar el mensaje
                JOptionPane.showMessageDialog(VentanaInternacionalizacion.this,
                        saludo + ", " + nombres + " " + apellidos + "!\n"
                        + fechaHoraTexto + ": " + fechaHora,
                        tituloMensaje,
                        JOptionPane.INFORMATION_MESSAGE);

                // Cerrar esta ventana después de mostrar el mensaje
                dispose();
            }
        });

        // --- Agregar funcionalidad al ComboBox para cambios de idioma ---
        cmbIdiomas.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String idiomaSeleccionado = (String) cmbIdiomas.getSelectedItem();
                    Locale locale;

                    switch (idiomaSeleccionado) {
                        case "ES" ->
                            locale = new Locale("es");
                        case "EN" ->
                            locale = new Locale("en");
                        case "PT" ->
                            locale = new Locale("pt");
                        default ->
                            locale = new Locale("es");
                    }

                    actualizarIdioma(locale);
                }
            }
        });

        add(panel);
    }

    // Métodos Getter para acceder desde el controlador
    public JComboBox<String> getCmbIdiomas() {
        return cmbIdiomas;
    }

    public JButton getBtnMensaje() {
        return btnMensaje;
    }

    public JTextField getTxtNombres() {
        return txtNombres;
    }

    public JTextField getTxtApellidos() {
        return txtApellidos;
    }

    public JRadioButton getRbtFemenino() {
        return rbtFemenino;
    }

    public JRadioButton getRbtMasculino() {
        return rbtMasculino;
    }

    private void actualizarIdioma(Locale locale) {
        bundle = ResourceBundle.getBundle("idiomas.mensajes", locale);

        setTitle(bundle.getString("titulo"));
        lblNombres.setText(bundle.getString("nombres"));
        lblApellidos.setText(bundle.getString("apellidos"));
        lblGenero.setText(bundle.getString("genero"));
        lblMostrar.setText(bundle.getString("mostrar"));
        lblIdioma.setText(bundle.getString("idioma"));
        btnMensaje.setText(bundle.getString("mensaje"));
    }

}
