package controlador;

import modelo.Logica;
import java.util.ArrayList;
import vo.PersonaVo;
import vista.VentanaBuscar;
import vista.VentanaPrincipal;
import vista.VentanaRegistro;
import dao.PersonaDao;

public class Coordinador {

	private Logica miLogica;
	private VentanaPrincipal miVentanaPrincipal;
	private VentanaRegistro miVentanaRegistro;
	private VentanaBuscar miVentanaBuscar;
	private ArrayList codigos;

	public VentanaPrincipal getMiVentanaPrincipal() {
		return miVentanaPrincipal;
	}

	public void setMiVentanaPrincipal(VentanaPrincipal miVentanaPrincipal) {
		this.miVentanaPrincipal = miVentanaPrincipal;
	}

	public VentanaRegistro getMiVentanaRegistro() {
		return miVentanaRegistro;
	}

	public void setMiVentanaRegistro(VentanaRegistro miVentanaRegistro) {
		this.miVentanaRegistro = miVentanaRegistro;
	}

	public VentanaBuscar getMiVentanaBuscar() {
		return miVentanaBuscar;
	}

	public void setMiVentanaBuscar(VentanaBuscar miVentanaBuscar) {
		this.miVentanaBuscar = miVentanaBuscar;
	}

	public Logica getMiLogica() {
		return miLogica;
	}

	public void setMiLogica(Logica miLogica) {
		this.miLogica = miLogica;
	}

	public ArrayList getCodigos() {
		return codigos;
	}

	public void ListarCodigo() {
		this.codigos = new PersonaDao().listarCodigo();
	}

	//////////////////////////////////////////////////////////

	public void mostrarVentanaRegistro() {
		miVentanaRegistro.setVisible(true);
	}

	public void mostrarVentanaConsulta() {
		miVentanaBuscar.setVisible(true);
	}

	public void registrarPersona(PersonaVo miPersona) {
		miLogica.validarRegistro(miPersona);
	}

	public PersonaVo buscarPersona(Integer codigoPersona) {
		return miLogica.validarConsulta(codigoPersona);
	}

	public void modificarPersona(PersonaVo miPersona) {
		miLogica.validarModificacion(miPersona);
	}

	public void eliminarPersona(String codigo) {
		miLogica.validarEliminacion(codigo);
	}

}
