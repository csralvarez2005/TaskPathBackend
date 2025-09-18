package TaskPathBackend.controller;

import TaskPathBackend.entity.Cargo;
import TaskPathBackend.service.CargoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
@CrossOrigin(origins = "*") // opcional: para que Postman/Frontend pueda acceder
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    // GET /cargos → listar todos
    @GetMapping
    public List<Cargo> listarTodos() {
        return cargoService.listarTodos();
    }
}
