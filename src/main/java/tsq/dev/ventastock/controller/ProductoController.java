package tsq.dev.ventastock.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tsq.dev.ventastock.domain.producto.*;

import java.net.URI;
import java.util.List;

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
                producto.getPrecio(), producto.getStock());
        URI url = uriComponentsBuilder.path("/productos/{id}").buildAndExpand(producto.getId()).toUri();
        return ResponseEntity.created(url).body(dtoRespuestaProducto);
    }

    // este metodo obtiene todos los productos
    @GetMapping("/todos")
    public ResponseEntity<List<DTOListadoProducto>> listaTodosLosProducto(){
        List<DTOListadoProducto> productos = productoRepository.findAll()
                .stream()
                .map(DTOListadoProducto::new)
                .toList();
        return ResponseEntity.ok(productos);
    }

    // Este metodo trae los productos con stock>0
    @GetMapping("/con-stock")
    public ResponseEntity<List<DTOListadoProducto>> listaProductosPorStock() {
        List<DTOListadoProducto> productos = productoRepository.findByStockGreaterThan(0)
                .stream()
                .map(DTOListadoProducto::new)
                .toList();
        return ResponseEntity.ok(productos);
    }

    // Este metodo trae los productos con stock = 0
    @GetMapping("/sin-stock")
    public ResponseEntity<List<DTOListadoProducto>> listarProductosSinStock() {
        List<DTOListadoProducto> productos = productoRepository.findByStock(0)
                .stream()
                .map(DTOListadoProducto::new) // Convierte cada entidad a DTO
                .toList();
        return ResponseEntity.ok(productos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarProducto(@RequestBody @Valid DTOActualizarProducto dtoActualizarProducto){
        Producto producto = productoRepository.getReferenceById(dtoActualizarProducto.id());
        producto.actualizarDatos(dtoActualizarProducto);
        return ResponseEntity.ok(new DTOActualizarProducto(producto.getId(), producto.getCodigo(), producto.getNombre(),
                producto.getPrecio(), producto.getStock()));
    }

    @PutMapping("/actualizarStock")
    @Transactional
    public ResponseEntity actualizarStock(@RequestBody @Valid DTOActualizarStock dtoActualizarStock) {
        Producto producto = productoRepository.getReferenceById(dtoActualizarStock.id());
        producto.setStock(dtoActualizarStock.getStock());  // Solo actualizas el stock
        return ResponseEntity.ok(new DTOActualizarStock(producto.getId(), producto.getStock()));
    }

}
