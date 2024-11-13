package com.automatedtestingcourse.demo.domain;

import static com.automatedtestingcourse.demo.commom.PlanetConstants.PLANET;
import static com.automatedtestingcourse.demo.commom.PlanetConstants.INVALID_PLANET;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

     /*
     convenção p/ nomes de métodos ref. ao testes ->  operacao_estado_retorno
      */

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Test // annotation p/ métodos de testes
    public void createPlanet_WithValidData_ReturnsPlanet() {
        //Principio AAA
        // Arrange
        Mockito.when(planetRepository.save(PLANET)).thenReturn(PLANET);

        //ACT
        //system under test
        Planet sut = planetService.create(PLANET);

        // ASSERT
        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        Mockito.when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findById_WithIdExisting_ReturnsPlanet() {
        Mockito.when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.findById(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void findById_WithIdNonExistent_ReturnsEmpty() {
        Mockito.when(planetRepository.findById(1l)).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.findById(1L);
        assertThat(sut).isEmpty();
    }

    @Test
    public void findByName_WithNameExisting_ReturnsPlanet() {
        Mockito.when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.findByName(PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void findByName_WithIdNonExistent_ReturnsEmpty() {
        Mockito.when(planetRepository.findByName("")).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.findByName("");
        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> planets = new ArrayList<>() {{
            add(PLANET);
        }};

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        Mockito.when(planetRepository.findAll(query)).thenReturn(planets);

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        Mockito.when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());

        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        //assertThatCode verifica se não lança nenhuma exceção
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_ThrowsException() {
        Mockito.doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);
        assertThatThrownBy(() -> planetService.remove(99l)).isInstanceOf(RuntimeException.class);
    }
}
