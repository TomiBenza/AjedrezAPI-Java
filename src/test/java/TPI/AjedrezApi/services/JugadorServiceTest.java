package TPI.AjedrezApi.services;

import TPI.AjedrezApi.controllers.exceptions.JugadorAlreadyExistsException;
import TPI.AjedrezApi.controllers.exceptions.JugadorNotFoundException;
import TPI.AjedrezApi.entities.JugadorEntity;
import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.repositories.jpa.JugadorJpaRepository;
import TPI.AjedrezApi.services.JugadorService;
import TPI.AjedrezApi.services.impl.JugadorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

public class JugadorServiceTest {
    @Mock
    private JugadorJpaRepository jugadorJpaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private JugadorService jugadorService = new JugadorServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetJugadorById_ExistingId_ReturnsJugador() {
        Long jugadorId = 1L;
        JugadorEntity jugadorEntity = new JugadorEntity();
        jugadorEntity.setId_jugador(jugadorId);
        jugadorEntity.setNombre("Robertito");
        Jugador jugador = new Jugador();
        jugador.setNombre("Robertito");

        when(jugadorJpaRepository.findById(jugadorId)).thenReturn(Optional.of(jugadorEntity));
        when(modelMapper.map(jugadorEntity, Jugador.class)).thenReturn(jugador);

        Jugador result = jugadorService.getJugadorById(jugadorId);

        assertNotNull(result);
        assertEquals("Robertito", result.getNombre());
    }
    @Test
    public void testGetJugadorById_NonExistingId_ThrowsException() {
        Long jugadorId = 1L;
        when(jugadorJpaRepository.findById(jugadorId)).thenReturn(Optional.empty());

        assertThrows(JugadorNotFoundException.class, () -> jugadorService.getJugadorById(jugadorId));
    }

    @Test
    public void testGetJugadores_ReturnsListOfJugadores() {
        List<JugadorEntity> jugadorEntityList = new ArrayList<>();
        jugadorEntityList.add(new JugadorEntity(1L, "Robertito", 10, 5, 3));
        jugadorEntityList.add(new JugadorEntity(2L, "Carlitos", 15, 7, 2));

        when(jugadorJpaRepository.findAll()).thenReturn(jugadorEntityList);

        List<Jugador> result = jugadorService.getJugadores();

        assertNotNull(result);
        assertEquals(jugadorEntityList.size(), result.size());
    }

    @Test
    public void testCreateJugador_ValidJugador_ReturnsCreatedJugador() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Robertito");
        JugadorEntity jugadorEntity = new JugadorEntity();
        jugadorEntity.setNombre("Robertito");

        when(jugadorJpaRepository.findByNombre(jugador.getNombre())).thenReturn(Optional.empty());
        when(modelMapper.map(jugador, JugadorEntity.class)).thenReturn(jugadorEntity);
        when(jugadorJpaRepository.save(jugadorEntity)).thenReturn(jugadorEntity);
        when(modelMapper.map(jugadorEntity, Jugador.class)).thenReturn(jugador);

        Jugador result = jugadorService.createJugador(jugador);

        assertNotNull(result);
        assertEquals(jugador.getNombre(), result.getNombre());
    }
    @Test
    public void testCreateJugador_ExistingJugador_ThrowsJugadorAlreadyExistsException() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Robertito");

        when(jugadorJpaRepository.findByNombre(jugador.getNombre())).thenReturn(Optional.of(new JugadorEntity()));

        assertThrows(JugadorAlreadyExistsException.class, () -> {
            jugadorService.createJugador(jugador);
        });
    }

}
