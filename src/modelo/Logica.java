package modelo;

import javax.swing.JOptionPane;

import dao.PersonaDao;
import vo.PersonaVo;
import controlador.Coordinador;

public class Logica {

    private Coordinador miCoordinador;
    public static boolean consultaPersona = false;
    public static boolean modificaPersona = false;

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;

    }

    private void showMessageDialog(String message) {
        modificaPersona = false;
        JOptionPane.showMessageDialog(null, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public void validarRegistro(PersonaVo miPersona) {
        // Validar Código: Reutilizamos el método validarConsulta para buscar y
        // verificar si ya existe
        PersonaVo personaExist = validarConsulta(miPersona.getIdPersona());

        if (personaExist != null) {
            // Si retorna un registro, el código ya está en la base de datos
            showMessageDialog("El código ya se encuentra registrado");
            return;
        }
        
        if (miPersona.getNombrePersona().length() < 5) {
            showMessageDialog("El nombre de la persona debe ser mayor a 5 digitos");
            return;
        }

        if (!miPersona.getNombrePersona().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,80}$")) {
            showMessageDialog("El nombre solo puede contener letras y espacios");
            return;
        }

        if (!miPersona.getProfesionPersona().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,120}$")) {
            showMessageDialog("La profesión solo puede contener letras y espacios");
            return;
        }

        if (miPersona.getEdadPersona() <= 0) {
            showMessageDialog("Edad no válida");
            return;
        }
        
        int digitos = String.valueOf(miPersona.getTelefonoPersona()).length();
        if (digitos < 10 || digitos > 10) {
            showMessageDialog("El número de teléfono solo debe tener 10 dígitos");
            return;
        }

        PersonaDao miPersonaDao = new PersonaDao();
        miPersonaDao.registrarPersona(miPersona);
    }

    public PersonaVo validarConsulta(Integer codigo) {
        PersonaDao miPersonaDao;
        int digitos = String.valueOf(codigo).length();
        try {
            if (digitos > 3) {
                miPersonaDao = new PersonaDao();
                return miPersonaDao.buscarPersona(codigo);
            } else {
                showMessageDialog("El documento de la persona debe ser mas de 3 digitos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se ha presentado un Error", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public void validarModificacion(PersonaVo miPersona) {
        PersonaDao miPersonaDao;
        
        if (!miPersona.getNombrePersona().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,80}$")) {
            showMessageDialog("El nombre solo puede contener letras y espacios");
            return;
        }
        
        if (miPersona.getNombrePersona().length() < 5) {
            showMessageDialog("El nombre de la persona debe ser mayor a 5 digitos");
            return;
        }
        
        if (!miPersona.getProfesionPersona().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,120}$")) {
            showMessageDialog("La profesión solo puede contener letras y espacios");
            return;
        }

        if (miPersona.getEdadPersona() <= 0) {
            showMessageDialog("Edad no válida");
            return;
        }
        
        int digitos = String.valueOf(miPersona.getTelefonoPersona()).length();
        if (digitos < 10 || digitos > 10) {
            showMessageDialog("El número de teléfono solo debe tener 10 dígitos");
            return;
        }
        
        modificaPersona = true;
        miPersonaDao = new PersonaDao();
        miPersonaDao.modificarPersona(miPersona);
    }

    public void validarEliminacion(String codigo) {
        PersonaDao miPersonaDao = new PersonaDao();
        miPersonaDao.eliminarPersona(codigo);
    }

}
