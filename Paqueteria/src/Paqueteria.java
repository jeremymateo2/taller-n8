
public class Paqueteria {

    public static final String RECEPTADO = "Receptado";
    public static final String ENVIADO = "Enviado";
    public static final String RECIBIDO = "Recibido";

    private int tracking;
    private double peso;
    private String ciudadRecepcion;
    private String ciudadEntrega;
    private String cedulaReceptor;
    private String estado;


    public Paqueteria(int tracking, double peso, String ciudadRecepcion,
                      String ciudadEntrega, String cedulaReceptor) {
        setTracking(tracking);
        setPeso(peso);
        setCiudadRecepcion(ciudadRecepcion);
        setCiudadEntrega(ciudadEntrega);
        setCedulaReceptor(cedulaReceptor);
        this.estado = RECEPTADO;
    }
    @Override
    public String toString() {
        return "Tracking: " + tracking
                + " | Peso: " + String.format("%.2f", peso) + " kg"
                + " | Recepción: " + ciudadRecepcion
                + " | Entrega: " + ciudadEntrega
                + " | Cédula: " + cedulaReceptor
                + " | Estado: " + estado;
    }


    public int getTracking() {
        return tracking;
    }

    public double getPeso() {
        return peso;
    }

    public String getCiudadRecepcion() {
        return ciudadRecepcion;
    }

    public String getCiudadEntrega() {
        return ciudadEntrega;
    }

    public String getCedulaReceptor() {
        return cedulaReceptor;
    }

    public String getEstado() {
        return estado;
    }



    public void setTracking(int tracking) {
        if (tracking <= 0) {
            throw new IllegalArgumentException("El número de tracking debe ser mayor que 0.");
        }
        this.tracking = tracking;
    }

    public void setPeso(double peso) {
        if (peso <= 0 || Double.isNaN(peso) || Double.isInfinite(peso)) {
            throw new IllegalArgumentException("El peso debe ser un número mayor que 0.");
        }
        this.peso = peso;
    }

    public void setCiudadRecepcion(String ciudadRecepcion) {
        if (ciudadRecepcion == null || ciudadRecepcion.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar la ciudad de recepción.");
        }
        this.ciudadRecepcion = ciudadRecepcion.trim();
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        if (ciudadEntrega == null || ciudadEntrega.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe indicar la ciudad de entrega.");
        }
        this.ciudadEntrega = ciudadEntrega.trim();
    }

    public void setCedulaReceptor(String cedulaReceptor) {
        if (cedulaReceptor == null || !cedulaReceptor.trim().matches("\\d{10}")) {
            throw new IllegalArgumentException("La cédula del receptor debe tener 10 dígitos numéricos.");
        }
        this.cedulaReceptor = cedulaReceptor.trim();
    }

    public void setEstado(String estado) {
        if (!RECEPTADO.equals(estado) && !ENVIADO.equals(estado) && !RECIBIDO.equals(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado
                    + ". Use Receptado, Enviado o Recibido.");
        }
        this.estado = estado;
    }


}
