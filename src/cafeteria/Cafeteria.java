package cafeteria;

public class Cafeteria {
    public static final int MAX_PRODUCTOS = 20;
    public static final String[] CATEGORIAS = {"Bebidas", "Panadería", "Snacks"};
    public static Producto[] inventario = new Producto[MAX_PRODUCTOS];
    public static int totalProductos = 0;
    public static double totalVendidoDia = 0.0;

    public static void ejecutar() {
        while (true) {
            mostrarMenu();
            int opcion = utils.Validaciones.leerEnteroEnRango("Opción: ", 0, 5);
            if (opcion == 0) {
                break;
            }
            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    realizarVenta();
                    break;
                case 3:
                    actualizarInventario();
                    break;
                case 4:
                    mostrarAgotados();
                    break;
                case 5:
                    mostrarTotalVendido();
                    break;
                default:
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("1. Registrar producto");
        System.out.println("2. Realizar venta");
        System.out.println("3. Actualizar inventario");
        System.out.println("4. Mostrar agotados");
        System.out.println("5. Total vendido del día");
        System.out.println("0. Volver al menú principal");
    }

    private static boolean hayCupo() {
        return totalProductos < MAX_PRODUCTOS;
    }

    private static int buscarProducto(int codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (inventario[i].getCodigo() == codigo) {
                return i;
            }
        }
        return -1;
    }

    private static String leerCategoria() {
        System.out.println("1. Bebidas  2. Panadería  3. Snacks");
        int opcion = utils.Validaciones.leerEnteroEnRango("Categoría: ", 1, 3);
        return CATEGORIAS[opcion - 1];
    }

    private static boolean hayStockSuficiente(Producto producto, int cantidad) {
        return cantidad <= producto.getCantidad();
    }

    private static double calcularSubtotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    private static void registrarProducto() {
        if (!hayCupo()) {
            System.out.println("Inventario lleno (20 productos)");
            return;
        }

        int codigo = totalProductos + 1;
        System.out.println("Código asignado: " + codigo);
        String nombre = utils.Validaciones.leerTexto("Nombre: ");
        double precio = utils.Validaciones.leerDoublePositivo("Precio: ");
        int cantidad = utils.Validaciones.leerEnteroMinimo("Cantidad: ", 0);
        String categoria = leerCategoria();

        inventario[totalProductos] = new Producto(codigo, nombre, precio, cantidad, categoria);
        totalProductos++;
        System.out.println("Producto registrado");
    }

    private static void realizarVenta() {
        if (totalProductos == 0) {
            System.out.println("No hay productos");
            return;
        }

        int codigo = utils.Validaciones.leerEntero("Código del producto: ");
        int pos = buscarProducto(codigo);
        if (pos == -1) {
            System.out.println("Producto no existe");
            return;
        }

        Producto producto = inventario[pos];
        if (producto.estaAgotado()) {
            System.out.println("Producto agotado");
            return;
        }

        int cantidad = utils.Validaciones.leerEnteroMinimo("Cantidad a vender: ", 1);
        if (!hayStockSuficiente(producto, cantidad)) {
            System.out.println("Stock insuficiente. Disponible: " + producto.getCantidad());
            return;
        }

        double subtotal = calcularSubtotal(producto.getPrecio(), cantidad);
        producto.restarStock(cantidad);
        totalVendidoDia += subtotal;
        System.out.println("Venta realizada. Subtotal: " + subtotal);
    }

    private static void mostrarInventario() {
        if (totalProductos == 0) {
            System.out.println("No hay productos");
            return;
        }

        for (int i = 0; i < totalProductos; i++) {
            Producto producto = inventario[i];
            System.out.println("Código: " + producto.getCodigo()
                    + ", Nombre: " + producto.getNombre()
                    + ", Categoría: " + producto.getCategoria()
                    + ", Precio: " + producto.getPrecio()
                    + ", Cantidad: " + producto.getCantidad());
        }
    }

    private static void actualizarInventario() {
        mostrarInventario();
        if (totalProductos == 0) {
            return;
        }

        int opcion = utils.Validaciones.leerEnteroEnRango("¿Reponer stock? 1. Sí  2. No: ", 1, 2);
        if (opcion == 1) {
            reponerStock();
        }
    }

    private static void reponerStock() {
        int codigo = utils.Validaciones.leerEntero("Código del producto: ");
        int pos = buscarProducto(codigo);
        if (pos == -1) {
            System.out.println("Producto no existe");
            return;
        }

        int cantidad = utils.Validaciones.leerEnteroMinimo("Cantidad a reponer: ", 1);
        inventario[pos].sumarStock(cantidad);
        System.out.println("Nuevo stock: " + inventario[pos].getCantidad());
    }

    private static void mostrarAgotados() {
        int contador = 0;
        for (int i = 0; i < totalProductos; i++) {
            Producto producto = inventario[i];
            if (producto.estaAgotado()) {
                System.out.println("Código: " + producto.getCodigo() + ", Nombre: " + producto.getNombre());
                contador++;
            }
        }
        if (contador == 0) {
            System.out.println("No hay productos agotados");
        }
    }

    private static void mostrarTotalVendido() {
        System.out.println("Total vendido hoy: " + totalVendidoDia);
    }
}
