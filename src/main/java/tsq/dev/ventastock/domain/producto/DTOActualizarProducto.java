package tsq.dev.ventastock.domain.producto;

public record DTOActualizarProducto(
        Long id,
        String codigo,
        String nombre,
        Double precio,
        int stock
) {
}
