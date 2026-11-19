import java.util.ArrayList;
import java.util.List;


public class ControladorPaqueteria {

    // operaciones que el controlador necesita de la lista (las implementa Lista)
    interface GestionPaquetes {
        void registrarPaquete(Paqueteria nuevo) throws Exception;
        ArrayList<Paqueteria> listarPaquetes();
        Paqueteria buscarPaquete(int tracking);
        void modificarPaquete(int tracking, double peso, String ciudadRecepcion,
                              String ciudadEntrega, String cedula) throws Exception;
        String cambiarEstado(int tracking) throws Exception;
        int contarPaquetes();
        double sumarPesos();
        double sumarPesoPorCiudad(String ciudad);
        int contarPorEstado(String estado);
        ArrayList<Paqueteria> ordenarPorTrackingBurbuja();
        ArrayList<Paqueteria> ordenarPorPesoInsercion();
        int busquedaBinaria(List<Paqueteria> listaOrdenada, int tracking);
        ArrayList<Paqueteria> buscarPorCedulaYEstado(String cedula, String estado);
    }

    private static final double PESO_MIN = 0.1;
    private static final double PESO_MAX = 100.0;

    private GestionPaquetes lista;

    public ControladorPaqueteria() {
        lista = new Lista();
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        try {
            lista.registrarPaquete(new Paqueteria(105, 25.0, "Quito", "Guayaquil", "1712345678"));
            lista.registrarPaquete(new Paqueteria(101, 12.5, "Cuenca", "Riobamba", "0102030405"));
            lista.registrarPaquete(new Paqueteria(110, 55.0, "Guayaquil", "Ibarra", "0912345678"));
            lista.registrarPaquete(new Paqueteria(103, 8.0, "Quito", "Ibarra", "1712345678"));
            lista.registrarPaquete(new Paqueteria(108, 30.2, "Quito", "Cuenca", "0102030405"));

            // estados variados para las pruebas
            lista.cambiarEstado(101);
            lista.cambiarEstado(110);
            lista.cambiarEstado(110);
            lista.cambiarEstado(103);
            lista.cambiarEstado(103);
        } catch (Exception e) {
            System.out.println("Error en datos de prueba: " + e.getMessage());
        }
    }

    // ================= VALIDACIONES =================

    private int validarTracking(String texto) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("Ingrese el numero de tracking.");
        }
        int tracking;
        try {
            tracking = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new Exception("El tracking debe ser un numero entero.");
        }
        if (tracking <= 0) {
            throw new Exception("El tracking debe ser mayor que 0.");
        }
        return tracking;
    }

    private double validarPeso(String texto) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("Ingrese el peso del paquete.");
        }
        double peso;
        try {
            peso = Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new Exception("El peso debe ser un numero (ej: 12.5).");
        }
        if (peso < PESO_MIN || peso > PESO_MAX) {
            throw new Exception("El peso debe estar entre " + PESO_MIN + " y " + PESO_MAX + " kg.");
        }
        return peso;
    }

    private void validarCiudades(String recepcion, String entrega) throws Exception {
        if (recepcion == null || entrega == null) {
            throw new Exception("Seleccione la ciudad de recepcion y la de entrega.");
        }
        if (recepcion.equals(entrega)) {
            throw new Exception("La ciudad de entrega debe ser distinta a la de recepcion.");
        }
    }

    private String validarCedula(String texto) throws Exception {
        if (texto == null || !texto.trim().matches("\\d{10}")) {
            throw new Exception("La cedula debe tener exactamente 10 digitos numericos.");
        }
        return texto.trim();
    }

    private void validarEstado(String estado) throws Exception {
        for (String e : Paqueteria.ESTADOS) {
            if (e.equals(estado)) {
                return;
            }
        }
        throw new Exception("Seleccione un estado valido.");
    }

    // ================= OPERACIONES =================

    public void registrar(String trackingTxt, String pesoTxt, String ciudadRecepcion,
                          String ciudadEntrega, String cedulaTxt) throws Exception {
        int tracking = validarTracking(trackingTxt);
        double peso = validarPeso(pesoTxt);
        validarCiudades(ciudadRecepcion, ciudadEntrega);
        String cedula = validarCedula(cedulaTxt);
        lista.registrarPaquete(new Paqueteria(tracking, peso, ciudadRecepcion, ciudadEntrega, cedula));
    }

    public void modificar(int tracking, String pesoTxt, String ciudadRecepcion,
                          String ciudadEntrega, String cedulaTxt) throws Exception {
        double peso = validarPeso(pesoTxt);
        validarCiudades(ciudadRecepcion, ciudadEntrega);
        String cedula = validarCedula(cedulaTxt);
        lista.modificarPaquete(tracking, peso, ciudadRecepcion, ciudadEntrega, cedula);
    }

    public String cambiarEstado(String trackingTxt) throws Exception {
        return lista.cambiarEstado(validarTracking(trackingTxt));
    }

    public ArrayList<Paqueteria> listar() {
        return lista.listarPaquetes();
    }

    public int totalPaquetes() {
        return lista.contarPaquetes();
    }

    public double pesoTotal() {
        return lista.sumarPesos();
    }

    public double pesoPorCiudad(String ciudad) throws Exception {
        if (ciudad == null) {
            throw new Exception("Seleccione una ciudad.");
        }
        return lista.sumarPesoPorCiudad(ciudad);
    }

    public int cantidadPorEstado(String estado) throws Exception {
        validarEstado(estado);
        return lista.contarPorEstado(estado);
    }

    public ArrayList<Paqueteria> ordenarPorTracking() {
        return lista.ordenarPorTrackingBurbuja();
    }

    public ArrayList<Paqueteria> ordenarPorPeso() {
        return lista.ordenarPorPesoInsercion();
    }

    public Paqueteria buscarBinaria(String trackingTxt) throws Exception {
        int tracking = validarTracking(trackingTxt);
        ArrayList<Paqueteria> ordenada = lista.ordenarPorTrackingBurbuja();
        int pos = lista.busquedaBinaria(ordenada, tracking);
        if (pos == -1) {
            throw new Exception("No se encontro ningun paquete con el tracking " + tracking + ".");
        }
        return ordenada.get(pos);
    }

    public ArrayList<Paqueteria> buscarPorCedulaYEstado(String cedulaTxt, String estado) throws Exception {
        String cedula = validarCedula(cedulaTxt);
        validarEstado(estado);
        return lista.buscarPorCedulaYEstado(cedula, estado);
    }
}
