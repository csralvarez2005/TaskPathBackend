package TaskPathBackend.controller;

import TaskPathBackend.entity.Cargo;
import TaskPathBackend.service.CargoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping
    public List<Cargo> listarCargos() {
        return cargoService.listarTodos();
    }
}
