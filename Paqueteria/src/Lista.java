import java.util.ArrayList;
import java.util.List;

public class Lista {

    private ArrayList<Paqueteria> serviEntrega;

    public Lista() {
        serviEntrega = new ArrayList<>();
    }

    public ArrayList<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    // 1. REGISTRAR PAQUETES
    public void adicionarElementos(Paqueteria paquete) throws Exception {
        if (buscarPaquete(paquete.getTracking()) != null) {
            throw new Exception("El tracking ya existe");
        }
        serviEntrega.add(paquete);
    }

    // 2. LISTAR PAQUETES
    public String listarPaquetes() {
        String resultado = "";

        for (Paqueteria p : serviEntrega) {
            resultado += p.toString() + "\n";
        }

        return resultado;
    }

    // 3. BUSCAR POR TRACKING
    public Paqueteria buscarPaquete(int tracking) {
        for (Paqueteria p : serviEntrega) {
            if (p.getTracking() == tracking) {
                return p;
            }
        }
        return null;
    }
    //Modificar Info
    public void editar(int tracking, double peso,
                       String ciudadRecepcion,
                       String ciudadEntrega,
                       String cedulaReceptor) throws Exception {

        Paqueteria p = buscarPaquete(tracking);

        if (p == null) {
            throw new Exception("Paquete no encontrado");
        }
        p.setPeso(peso);
        p.setCiudadRecepcion(ciudadRecepcion);
        p.setCiudadEntrega(ciudadEntrega);
        p.setCedulaReceptor(cedulaReceptor);

    }
    // 5. CAMBIAR ESTADO
    public String estadoPaquete(int tracking) {
        Paqueteria p = buscarPaquete(tracking);

        if (p == null) {
            throw new IllegalArgumentException("Paquete no encontrado");
        }

        if (p.getEstado().equals("Receptado")) {
            p.setEstado("Enviado");
            return "Enviado";
        }

        if (p.getEstado().equals("Enviado")) {
            p.setEstado("Recibido");
            return "Recibido";
        }

        return "Finalizado";
    }
    // 6. RECURSIVIDAD: TOTAL DE PAQUETES
    public int sumarTotalPaquetes() {
        return contarPaquetes(0);
    }

    private int contarPaquetes(int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }

        return 1 + contarPaquetes(indice + 1);
    }
    // 7. RECURSIVIDAD: PESO TOTAL
    public double sumarTotalPeso() {
        return calcularPeso(0);
    }

    private double calcularPeso(int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }

        return serviEntrega.get(indice).getPeso()
                + calcularPeso(indice + 1);
    }


}