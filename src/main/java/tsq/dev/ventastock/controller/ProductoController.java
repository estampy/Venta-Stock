package tsq.dev.ventastock.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import tsq.dev.ventastock.domain.producto.DTORegistroProducto;
import tsq.dev.ventastock.domain.producto.DTORespuestaProducto;
import tsq.dev.ventastock.domain.producto.Producto;
import tsq.dev.ventastock.domain.producto.ProductoRepository;

import java.net.URI;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping
    public ResponseEntity<DTORespuestaProducto> registrarProducto(@RequestBody @Valid DTORegistroProducto dtoRegistroProducto,
                                                                  UriComponentsBuilder uriComponentsBuilder){
        Producto producto = productoRepository.save(new Producto(dtoRegistroProducto));
        DTORespuestaProducto dtoRespuestaProducto=new DTORespuestaProducto(producto.getId(), producto.getCodigo(), producto.getNombre(),
                producto.getPrecio(), producto.getStock(), producto.getDescripcion());
        URI url = uriComponentsBuilder.path("/productos/{id}").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(url).body(dtoRespuestaProducto);
    }
}
