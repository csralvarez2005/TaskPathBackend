package TaskPathBackend.controller;


import TaskPathBackend.dto.ListaValorDTO;
import TaskPathBackend.entity.ListaValor;
import TaskPathBackend.service.ListaValorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-valor")
public class ListaValorController {

    private final ListaValorService listaValorService;

    @Autowired
    public ListaValorController(ListaValorService listaValorService) {
        this.listaValorService = listaValorService;
    }

    @PostMapping
    public ResponseEntity<ListaValorDTO> crearListaValor(@RequestBody ListaValorDTO dto) {
        ListaValorDTO creado = listaValorService.crearListaValor(dto);
        return ResponseEntity.ok(creado);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> eliminarTodos() {
        listaValorService.eliminarTodos();
        return ResponseEntity.ok("Todos los registros de lista_valor fueron eliminados.");
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ListaValorDTO>> crearListaValores(@RequestBody List<ListaValorDTO> dtos) {
        List<ListaValorDTO> creados = listaValorService.crearListaValores(dtos);
        return ResponseEntity.ok(creados);
    }

    // 🔹 Nuevo endpoint para obtener tipos de identificación
    @GetMapping("/tipos-identificacion")
    public ResponseEntity<List<ListaValor>> obtenerTiposIdentificacion() {
        List<ListaValor> lista = listaValorService.obtenerTiposIdentificacion();
        return ResponseEntity.ok(lista);
    }
}
