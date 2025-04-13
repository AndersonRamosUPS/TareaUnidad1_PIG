package controlador;

import modelo.Persona;
import modelo.PersonaDAO;
import vista.ContactoVista;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.JLabel;

public class LogicaContactos implements ActionListener, ListSelectionListener, ItemListener {

    private ContactoVista vista;
    private List<Persona>  contactos = new ArrayList<>();   //Lista en memoria
    private Persona personaSeleccionada;
    private String nombres, telefono, email, categoria = "";
    private boolean favorito = false;
    private TableRowSorter<DefaultTableModel> sorter;

    public LogicaContactos(ContactoVista vista) {
        this.vista = vista;
        this.contactos = new ArrayList<>();

        // Cargar contactos del archivo al iniciar
        cargarContactosRegistrados();

        // Asociar eventos a componentes
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getCmbCategoria().addItemListener(this);
        this.vista.getTablaContactos().getSelectionModel().addListSelectionListener(this);
        this.vista.getChbFavoritos().addItemListener(this);
        this.vista.getItemExportarCSV().addActionListener(e -> exportarA_CSV());

        //Agregar atajo de teclado (Ctrl + E)
        vista.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("control E"), "exportar");

        vista.getRootPane().getActionMap().put("exportar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarA_CSV();
            }
        });

        vista.getTxtBuscar().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabla();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabla();
            }
        });

    }

    private void inicializarCampos() {
        nombres = vista.getTxtNombre().getText();
        telefono = vista.getTxtTelefono().getText();
        email = vista.getTxtCorreo().getText();
    }

    private void cargarContactosRegistrados() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                vista.getBarraProgreso().setIndeterminate(true);
                vista.getBarraProgreso().setString("Cargando contactos...");
                contactos = new PersonaDAO().leerArchivo();
                return null;
            }

            @Override
            protected void done() {
                vista.getBarraProgreso().setIndeterminate(false);
                vista.getBarraProgreso().setValue(100);
                vista.getBarraProgreso().setString("Carga completada");

                actualizarTabla();           // ✅ Ya estaba
                actualizarEstadisticas();   // ✅ Esto le faltaba
            }
        };
        worker.execute();
    }



    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtCorreo().setText("");
        vista.getCmbCategoria().setSelectedIndex(0);
        vista.getChbFavoritos().setSelected(false);
        categoria = "";
        favorito = false;

        inicializarCampos();
        cargarContactosRegistrados();
        actualizarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Obtener valores directamente del formulario
        nombres = vista.getTxtNombre().getText();
        telefono = vista.getTxtTelefono().getText();
        email = vista.getTxtCorreo().getText();
        categoria = vista.getCmbCategoria().getSelectedItem().toString();
        favorito = vista.getChbFavoritos().isSelected();

        if (e.getSource() == vista.getBtnAgregar()) {
            if (!nombres.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
                if (!categoria.equals("Elija una Categoria") && !categoria.isEmpty()) {
                    Persona nueva = new Persona(nombres, telefono, email, categoria, favorito);
                    new PersonaDAO().escribirArchivo(nueva);
                    JOptionPane.showMessageDialog(vista, "¡Contacto registrado!");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(vista, "Seleccione una categoría válida.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben ser llenados.");
            }
        } else if (e.getSource() == vista.getBtnEliminar()) {
            int index = vista.getTablaContactos().getSelectedRow();
            if (index != -1) {
                contactos.remove(index);
                new PersonaDAO().actualizarContactos(contactos);
                limpiarCampos();
                JOptionPane.showMessageDialog(vista, "Contacto eliminado.");
            }
        } else if (e.getSource() == vista.getBtnEditar()) {
            int index = vista.getTablaContactos().getSelectedRow();
            if (index != -1) {
                Persona p = contactos.get(index);
                p.setNombre(nombres);
                p.setTelefono(telefono);
                p.setEmail(email);
                p.setCategoria(categoria);
                p.setFavorito(favorito);
                new PersonaDAO().actualizarContactos(contactos);
                limpiarCampos();
                JOptionPane.showMessageDialog(vista, "Contacto actualizado.");
            }
        }
    }

    private void actualizarTabla() {
        String[] columnas = { "Nombre", "Teléfono", "Email", "Categoría", "Favorito" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Persona p : contactos) {
            String[] fila = {
                    p.getNombre(),
                    p.getTelefono(),
                    p.getEmail(),
                    p.getCategoria(),
                    p.isFavorito() ? "Sí" : "No"
            };
            modelo.addRow(fila);
        }

        vista.getTablaContactos().setModel(modelo);

        // Asignar TableRowSorter a la tabla
        sorter = new TableRowSorter<>(modelo);
        vista.getTablaContactos().setRowSorter(sorter);
    }


    private void exportarA_CSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        int seleccion = fileChooser.showSaveDialog(vista);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = new File(fileChooser.getSelectedFile() + ".csv");

            SwingWorker<Void, Void> exportWorker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    vista.getBarraProgreso().setIndeterminate(true);
                    vista.getBarraProgreso().setString("Exportando contactos...");

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                        bw.write("Nombre,Teléfono,Email,Categoría,Favorito");
                        bw.newLine();
                        for (Persona p : contactos) {
                            bw.write(p.getNombre() + "," +
                                    p.getTelefono() + "," +
                                    p.getEmail() + "," +
                                    p.getCategoria() + "," +
                                    p.isFavorito());
                            bw.newLine();
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(vista, "Error al exportar el archivo.");
                        ex.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    vista.getBarraProgreso().setIndeterminate(false);
                    vista.getBarraProgreso().setValue(100);
                    vista.getBarraProgreso().setString("Exportación completada");
                    JOptionPane.showMessageDialog(vista, "Archivo exportado exitosamente.");
                }
            };

            exportWorker.execute();
        }
    }

    private void filtrarTabla() {
        String texto = vista.getTxtBuscar().getText();
        if (sorter != null) {
            if (texto.trim().isEmpty()) {
                sorter.setRowFilter(null); // quitar filtro
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0)); // filtra por columna "Nombre"
            }
        }
    }

    private void actualizarEstadisticas() {
        int total = contactos.size();
        int favoritos = 0;
        int familia = 0, amigos = 0, trabajo = 0, otros = 0;

        for (Persona p : contactos) {
            if (p.isFavorito()) favoritos++;

            switch (p.getCategoria()) {
                case "Familia" -> familia++;
                case "Amigos"  -> amigos++;
                case "Trabajo" -> trabajo++;
                case "Otros"   -> otros++;
            }
        }

        vista.getLblTotal().setText("Total de contactos: " + total);
        vista.getLblFavoritos().setText("Contactos favoritos: " + favoritos);
        vista.getLblCategoriaFamilia().setText("Familia: " + familia);
        vista.getLblCategoriaAmigos().setText("Amigos: " + amigos);
        vista.getLblCategoriaTrabajo().setText("Trabajo: " + trabajo);
        vista.getLblCategoriaOtros().setText("Otros: " + otros);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        int index = vista.getTablaContactos().getSelectedRow();
        if (index != -1 && index < contactos.size()) {
            Persona p = contactos.get(index);
            vista.getTxtNombre().setText(p.getNombre());
            vista.getTxtTelefono().setText(p.getTelefono());
            vista.getTxtCorreo().setText(p.getEmail());
            vista.getCmbCategoria().setSelectedItem(p.getCategoria());
            vista.getChbFavoritos().setSelected(p.isFavorito());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == vista.getCmbCategoria()) {
            categoria = vista.getCmbCategoria().getSelectedItem().toString();
        } else if (e.getSource() == vista.getChbFavoritos()) {
            favorito = vista.getChbFavoritos().isSelected();
        }
    }
}
