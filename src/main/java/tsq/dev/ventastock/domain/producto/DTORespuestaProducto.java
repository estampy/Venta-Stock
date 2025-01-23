package tsq.dev.ventastock.domain.producto;

public record DTORespuestaProducto(
        Long id,
        String codigo,
        String nombre,
        Double precio,
        int stock) {
}
