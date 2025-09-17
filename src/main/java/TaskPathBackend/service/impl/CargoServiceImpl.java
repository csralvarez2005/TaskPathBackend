package TaskPathBackend.service.impl;

import TaskPathBackend.entity.Cargo;
import TaskPathBackend.repository.CargoRepository;
import TaskPathBackend.service.CargoService;

import java.util.List;

public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository;

    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }
}
