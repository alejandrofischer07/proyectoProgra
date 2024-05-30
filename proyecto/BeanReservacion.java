public class BeanReservacion {
    private String codigo;
    private String nombre;
    private String fecha;
    private String hora;
    private int numeroPersonas;

    // Constructor sin parámetros
    public BeanReservacion() { }

    // Constructor con parámetros
    public BeanReservacion(String codigo, String nombre, String fecha, String hora, int numeroPersonas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }
}