package tsq.dev.ventastock.domain.producto;

public record DTOActualizarStock(
        Long id,
        int stock
) {
    public int getStock() {
        return this.stock();
    }
}

