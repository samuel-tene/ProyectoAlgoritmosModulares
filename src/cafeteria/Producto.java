package cafeteria;

public class Producto {
    private int codigo;
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;

    public Producto(int codigo, String nombre, double precio, int cantidad, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void restarStock(int cantidad) {
        this.cantidad -= cantidad;
    }

    public void sumarStock(int cantidad) {
        this.cantidad += cantidad;
    }

    public boolean estaAgotado() {
        return cantidad == 0;
    }
}
