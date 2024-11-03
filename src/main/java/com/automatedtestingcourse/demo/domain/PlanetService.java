package com.automatedtestingcourse.demo.domain;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Optional<Planet> findById(Long id) {
        return planetRepository.findById(id);
    }

    public Optional<Planet> findByName(String name) {
        return planetRepository.findByName(name);
    }

    public List<Planet> list(String terrain, String climate) {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate,terrain));
        return planetRepository.findAll(query);
    }

    public void remove(Long id) {
        planetRepository.deleteById(id);
    }
}
