import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class VentanaPaqueteria {
    private JPanel ventana;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JTextField textField2;
    private JButton agregarButton;
    private JButton totalPaquetesButton;
    private JComboBox comboBox3;
    private JButton totalPesoPorCiudadButton;
    private JButton totalPesosButton;
    private JList list1;
    private JButton modificarButton;
    private JTextField textField3;
    private JButton modificarEstadoDelPaqueteButton;
    private JButton ordenarPorBurbujaButton;
    private JButton ordenarPorInserciónButton;
    private JList list2;
    private JList list3;
    private JComboBox comboBox4;
    private JButton calcularButton;
    private JTextField textField4;
    private JButton buscarButton;
    private JList list4;
    private JTextField textField5;
    private JList list5;
    private JComboBox comboBox5;
    private JButton buscarPorCedulaEstadoButton;

    private final Lista paquetes = new Lista();

    public VentanaPaqueteria() {
        // El tracking solo admite enteros positivos
        spinner1.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        quemarDatos();
        llenarJlist();
        limpiarDatos();

        // ------------------- 1. Registrar paquete -------------------
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    spinner1.commitEdit();
                    int tracking = (Integer) spinner1.getValue();
                    double peso = leerPeso();
                    paquetes.adicionarElementos(new Paqueteria(tracking, peso,
                            comboBox1.getSelectedItem().toString(),
                            comboBox2.getSelectedItem().toString(),
                            textField2.getText()));
                    JOptionPane.showMessageDialog(ventana, "Paquete agregado correctamente.");
                    llenarJlist();
                    limpiarDatos();
                    System.out.println(paquetes.listarPaquetes());
                } catch (java.text.ParseException ex) {
                    mostrarError("El número de tracking debe ser un entero positivo.");
                } catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        // ------------------- 3. Modificar paquete seleccionado -------------------
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedIndex() == -1) {
                    mostrarError("Seleccione un paquete de la lista para modificarlo.");
                    return;
                }
                Paqueteria paqueteSeleccionado = (Paqueteria) list1.getSelectedValue();
                int tracking = paqueteSeleccionado.getTracking();
                try {
                    double nuevoPeso = leerPeso();
                    String nuevaCiudadRecepcion = comboBox1.getSelectedItem().toString();
                    String nuevaCiudadEntrega = comboBox2.getSelectedItem().toString();
                    String nuevaCedula = textField2.getText();

                    paquetes.editar(tracking, nuevoPeso, nuevaCiudadRecepcion, nuevaCiudadEntrega, nuevaCedula);
                    llenarJlist();
                    limpiarDatos();
                    JOptionPane.showMessageDialog(ventana, "Paquete modificado:\n" + paquetes.buscarPaquete(tracking));
                } catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        // ------------------- 2. Seleccionar paquete existente -------------------
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && list1.getSelectedIndex() != -1) {
                    Paqueteria pa = (Paqueteria) list1.getSelectedValue();
                    spinner1.setValue(pa.getTracking());
                    textField1.setText(String.valueOf(pa.getPeso()));
                    comboBox1.setSelectedItem(pa.getCiudadRecepcion());
                    comboBox2.setSelectedItem(pa.getCiudadEntrega());
                    textField2.setText(pa.getCedulaReceptor());
                    textField3.setText(String.valueOf(pa.getTracking()));
                }
            }
        });

        // ------------------- 5. Total de paquetes (recursivo) -------------------
        totalPaquetesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "El total de paquetes es: " + paquetes.sumarTotalPaquetes());
            }
        });

        // ------------------- 6. Peso total (recursivo) -------------------
        totalPesosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana, "El peso total de los paquetes es: "
                        + String.format("%.2f", paquetes.sumarTotalPeso()) + " kg");
            }
        });

        // ------------------- 7. Peso total por ciudad (recursivo) -------------------
        totalPesoPorCiudadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ciudad = comboBox3.getSelectedItem().toString();
                JOptionPane.showMessageDialog(ventana, "Peso total de los paquetes recibidos en "
                        + ciudad + ": " + String.format("%.2f", paquetes.sumarTotalPesoCiudad(ciudad)) + " kg");
            }
        });

        // ------------------- 8. Cantidad de paquetes por estado (recursivo) -------------------
        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estadoSeleccionado = comboBox4.getSelectedItem().toString();
                int totalPaquetesEstado = paquetes.sumarTotalPaquetesEstado(estadoSeleccionado);
                JOptionPane.showMessageDialog(ventana, "Total de paquetes en estado '"
                        + estadoSeleccionado + "': " + totalPaquetesEstado);
            }
        });

        // ------------------- 4. Cambiar estado por tracking -------------------
        modificarEstadoDelPaqueteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tracking = leerEntero(textField3.getText());
                    String nuevoEstado = paquetes.estadoPaquete(tracking);
                    switch (nuevoEstado) {
                        case "Enviado":
                            JOptionPane.showMessageDialog(ventana, "El paquete con tracking " + tracking
                                    + " cambió a estado: Enviado.");
                            break;
                        case "Recibido":
                            JOptionPane.showMessageDialog(ventana, "El paquete con tracking " + tracking
                                    + " cambió a estado: Recibido.");
                            break;
                        default:
                            JOptionPane.showMessageDialog(ventana, "El paquete con tracking " + tracking
                                    + " ya fue Recibido.\nEl proceso de envío ha finalizado.");
                            break;
                    }
                    llenarJlist();
                } catch (Exception ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });

        // ------------------- 9. Ordenar por burbuja (tracking) -------------------
        ordenarPorBurbujaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarJlistOriginal();
                llenarJlistTracking();
            }
        });

        // ------------------- 9. Ordenar por inserción (peso) -------------------
        ordenarPorInserciónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarJlistOriginal();
                llenarJlistPeso();
            }
        });

        // ------------------- 10. Búsqueda binaria -------------------
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    llenarJlistBusqueda(leerEntero(textField4.getText()));
                } catch (Exception ex) {
                    list4.setModel(new DefaultListModel());
                    mostrarError(ex.getMessage());
                }
            }
        });

        // ------------------- 11. Filtro por cédula y estado -------------------
        buscarPorCedulaEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = textField5.getText().trim();
                String estado = comboBox5.getSelectedItem().toString();
                if (cedula.isEmpty()) {
                    mostrarError("Por favor, ingrese una cédula y seleccione un estado.");
                    return;
                }
                List<Paqueteria> resultado = paquetes.buscarPaquetesPorCedulaYEstado(cedula, estado);
                DefaultListModel resultadoModel = new DefaultListModel();
                for (Paqueteria p : resultado) {
                    resultadoModel.addElement(p);
                }
                if (resultado.isEmpty()) {
                    resultadoModel.addElement("No se encontraron paquetes con la cédula y estado proporcionados.");
                }
                list5.setModel(resultadoModel);
            }
        });
    }

    // =====================================================================
    // Métodos auxiliares
    // =====================================================================

    private double leerPeso() {
        String texto = textField1.getText().trim().replace(',', '.');
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Ingrese el peso del paquete.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El peso debe ser un número válido (ej. 12.5).");
        }
    }

    private int leerEntero(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Por favor, ingrese un número de tracking válido.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void limpiarDatos() {
        spinner1.setValue(paquetes.siguienteTracking());
        textField1.setText("");
        comboBox1.setSelectedIndex(0);
        comboBox2.setSelectedIndex(0);
        textField2.setText("");
    }

    /**
     * Paquetes de prueba cargados al iniciar la aplicación.
     */
    public void quemarDatos() {
        try {
            paquetes.adicionarElementos(new Paqueteria(5, 25, "Quito", "Guayaquil", "1712345678"));
            paquetes.adicionarElementos(new Paqueteria(2, 15, "Cuenca", "Riobamba", "0102030405"));
            paquetes.adicionarElementos(new Paqueteria(9, 55, "Guayaquil", "Ibarra", "0912345678"));
            paquetes.adicionarElementos(new Paqueteria(7, 8.5, "Quito", "Cuenca", "1712345678"));
            paquetes.adicionarElementos(new Paqueteria(3, 40, "Riobamba", "Quito", "0602030405"));
            // Algunos estados distintos para poder probar filtros y conteos
            paquetes.estadoPaquete(7);                              // Enviado
            paquetes.estadoPaquete(9);
            paquetes.estadoPaquete(9);                              // Recibido
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void llenarJlist() {
        DefaultListModel dlm = new DefaultListModel<>();
        for (Paqueteria p : paquetes.getServiEntrega())
            dlm.addElement(p);
        list1.setModel(dlm);
    }

    public void llenarJlistOriginal() {
        DefaultListModel dlm = new DefaultListModel<>();
        for (Paqueteria p : paquetes.getServiEntrega())
            dlm.addElement(p);
        list2.setModel(dlm);
    }

    public void llenarJlistTracking() {
        DefaultListModel dlm2 = new DefaultListModel();
        for (Paqueteria p : paquetes.ordenarPaquetesPorTracking())
            dlm2.addElement(p);
        list3.setModel(dlm2);
    }

    public void llenarJlistPeso() {
        DefaultListModel dlmOrdenada = new DefaultListModel();
        for (Paqueteria p : paquetes.ordenarPaquetesPorPeso())
            dlmOrdenada.addElement(p);
        list3.setModel(dlmOrdenada);
    }

    public void llenarJlistBusqueda(int tracking) throws Exception {
        List<Paqueteria> listaOrdenada = paquetes.ordenarPaquetesPorTracking();
        int indiceEncontrado = paquetes.busquedaBinaria(listaOrdenada, tracking);
        if (indiceEncontrado == -1) {
            throw new Exception("No existe un paquete con el tracking " + tracking + " (posición -1).");
        }
        DefaultListModel dlm3 = new DefaultListModel<>();
        dlm3.addElement(listaOrdenada.get(indiceEncontrado));
        list4.setModel(dlm3);
        JOptionPane.showMessageDialog(ventana, "Paquete encontrado en la posición "
                + indiceEncontrado + " de la lista ordenada por tracking.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Sistema de Gestión de Paquetería");
                frame.setContentPane(new VentanaPaqueteria().ventana);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(new Dimension(Math.max(frame.getWidth(), 800), Math.max(frame.getHeight(), 650)));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
