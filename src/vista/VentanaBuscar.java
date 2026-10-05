package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.util.ArrayList;

import modelo.Logica;
import vo.PersonaVo;

import controlador.Coordinador;

public class VentanaBuscar extends JFrame implements ActionListener {

    private Coordinador miCoordinador; // objeto miCoordinador que permite la relacion entre esta clase y la clase
                                       // coordinador
    private JLabel labelTitulo;
    private JComboBox textCod;
    private JTextField textNombre, textEdad, textTelefono, textProfesion;
    private JLabel refrescar, cod, nombre, edad, telefono, profesion;
    private JButton botonRefrescar, botonGuardar, botonCancelar, botonModificar, botonEliminar;

    /**
     * constructor de la clase donde se inicializan todos los componentes
     * de la ventana de busqueda
     */
    public VentanaBuscar() {
        
        botonRefrescar = new JButton();
        botonRefrescar.setBounds(270, 80, 100, 25);
        botonRefrescar.setText("Refrescar");
        
        botonGuardar = new JButton();
        botonGuardar.setBounds(50, 220, 120, 25);
        botonGuardar.setText("Guardar");

        botonCancelar = new JButton();
        botonCancelar.setBounds(190, 250, 120, 25);
        botonCancelar.setText("Cancelar");

        botonEliminar = new JButton();
        botonEliminar.setBounds(330, 220, 120, 25);
        botonEliminar.setText("Eliminar");

        botonModificar = new JButton();
        botonModificar.setBounds(190, 220, 120, 25);
        botonModificar.setText("Modificar");

        labelTitulo = new JLabel();
        labelTitulo.setText("ADMINISTRAR PERSONAS");
        labelTitulo.setBounds(120, 20, 380, 30);
        labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

        cod = new JLabel();
        cod.setText("Codigo");
        cod.setBounds(20, 80, 80, 25);
        add(cod);
        
        nombre = new JLabel();
        nombre.setText("Nombre");
        nombre.setBounds(20, 120, 80, 25);
        add(nombre);

        telefono = new JLabel();
        telefono.setText("telefono");
        telefono.setBounds(290, 160, 80, 25);
        add(telefono);

        profesion = new JLabel();
        profesion.setText("profesion");
        profesion.setBounds(20, 160, 80, 25);
        add(profesion);

        edad = new JLabel();
        edad.setText("Edad");
        edad.setBounds(290, 120, 80, 25);
        add(edad);

        textCod = new JComboBox();
        textCod.setBounds(80, 80, 150, 25);
        textCod.addActionListener(this);
        add(textCod);

        textNombre = new JTextField();
        textNombre.setBounds(80, 120, 190, 25);
        add(textNombre);

        textTelefono = new JTextField();
        textTelefono.setBounds(340, 160, 80, 25);
        add(textTelefono);

        textProfesion = new JTextField();
        textProfesion.setBounds(80, 160, 190, 25);
        add(textProfesion);

        textEdad = new JTextField();
        textEdad.setBounds(340, 120, 80, 25);
        add(textEdad);

        botonModificar.addActionListener(this);
        botonEliminar.addActionListener(this);
        botonGuardar.addActionListener(this);
        botonCancelar.addActionListener(this);
        botonRefrescar.addActionListener(this);

        add(botonCancelar);
        add(botonModificar);
        add(botonEliminar);
        add(botonGuardar);
        add(botonRefrescar);
        add(labelTitulo);
        limpiar();

        setSize(480, 320);
        setTitle("CoDejaVu : Patrones de Diseño/MVC");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }

    @Override
    public void setVisible(boolean b) {
        if (b && miCoordinador != null) {
            miCoordinador.ListarCodigo();
            ArrayList codigos = miCoordinador.getCodigos();
            textCod.removeAllItems();
            textCod.addItem("Seleccione un código");
            if (codigos != null) {
                for (int i = 0; i < codigos.size(); i++) {
                    textCod.addItem(codigos.get(i));
                }
            }
        }
        super.setVisible(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRefrescar){
            this.setVisible(true);
        }
        
        if (e.getSource() == botonGuardar) {
            PersonaVo miPersona = new PersonaVo();
            String Nomcampo = "";
            miPersona.setIdPersona(Integer.parseInt(textCod.getSelectedItem().toString()));
            String nombre = textNombre.getText();
            String profecion = textProfesion.getText();
            String edad = textEdad.getText();
            String telefono = textTelefono.getText();

            try {
                if (nombre.length() == 0 || profecion.length() == 0 || edad.length() == 0 || telefono.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Error en el ingreso de Datos, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                miPersona.setNombrePersona(nombre);
                Nomcampo = "Edad";
                miPersona.setEdadPersona(Integer.parseInt(edad));
                miPersona.setProfesionPersona(profecion);
                Nomcampo = "telefono";
                miPersona.setTelefonoPersona(Long.parseLong(telefono));

                miCoordinador.modificarPersona(miPersona);

                if (Logica.modificaPersona == true) {
                    habilita(true, false, false, false, false, true, false, true, true);
                }
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El campo " + Nomcampo + " debe ser númerico", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Error en el Ingreso de Datos", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
        
        if (e.getSource() == textCod) {
            
            if (textCod.getSelectedIndex() <= 0) {
                limpiar();
                return;
            }
            PersonaVo miPersona = miCoordinador.buscarPersona(Integer.valueOf(textCod.getSelectedItem().toString()));
            if (miPersona != null) {
                muestraPersona(miPersona);
            }
        }

        if (e.getSource() == botonModificar) {
            if (textCod.getSelectedIndex() > 0) {
                habilita(false, true, true, true, true, false, true, false, false);
            }
        }

        if (e.getSource() == botonEliminar) {
            if (textCod.getSelectedIndex() > 0) {
                int respuesta = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar la Persona?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_NO_OPTION) {
                    miCoordinador.eliminarPersona(textCod.getSelectedItem().toString());
                    this.setVisible(true);
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un numero de Documento", "Informaci�n",
                        JOptionPane.WARNING_MESSAGE);
            }

        }
        if (e.getSource() == botonCancelar) {
            limpiar();
            this.dispose();
        }

    }

    /**
     * permite cargar los datos de la persona consultada
     * 
     * @param miPersona
     */
    private void muestraPersona(PersonaVo miPersona) {
        textNombre.setText(miPersona.getNombrePersona());
        textEdad.setText(miPersona.getEdadPersona() + "");
        textTelefono.setText(miPersona.getTelefonoPersona() + "");
        textProfesion.setText(miPersona.getProfesionPersona());
        habilita(true, false, false, false, false, true, false, true, true);
    }

    /**
     * Permite limpiar los componentes
     */
    public void limpiar() {
        if (textCod.getItemCount() > 0) {
            textCod.setSelectedIndex(0);
        }
        textNombre.setText("");
        textEdad.setText("");
        textTelefono.setText("");
        textProfesion.setText("");
        habilita(true, false, false, false, false, true, false, true, true);
    }

    /**
     * Permite habilitar los componentes para establecer una modificacion
     * 
     * @param codigo
     * @param nombre
     * @param edad
     * @param tel
     * @param profesion
     * @param cargo
     * @param bBuscar
     * @param bGuardar
     * @param bModificar
     * @param bEliminar
     */
    public void habilita(boolean codigo, boolean nombre, boolean edad, boolean tel, boolean profesion, boolean bBuscar,
            boolean bGuardar, boolean bModificar, boolean bEliminar) {
        textCod.setEnabled(codigo);
        textNombre.setEditable(nombre);
        textEdad.setEditable(edad);
        textTelefono.setEditable(tel);
        textProfesion.setEditable(profesion);
        botonGuardar.setEnabled(bGuardar);
        botonModificar.setEnabled(bModificar);
        botonEliminar.setEnabled(bEliminar);
    }
}
