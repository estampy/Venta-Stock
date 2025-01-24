package tsq.dev.ventastock.domain.producto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByStockGreaterThan(int stock);
    List<Producto> findByStock(int stock);
    Optional<Producto> findByCodigo(String codigo);
}
