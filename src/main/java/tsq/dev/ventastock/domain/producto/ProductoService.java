package tsq.dev.ventastock.domain.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public Producto guardarCodigo(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }
}
