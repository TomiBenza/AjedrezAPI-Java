package TPI.AjedrezApi.services.impl;

import TPI.AjedrezApi.controllers.exceptions.JugadorAlreadyExistsException;
import TPI.AjedrezApi.controllers.exceptions.JugadorNotFoundException;
import TPI.AjedrezApi.entities.JugadorEntity;
import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.repositories.jpa.JugadorJpaRepository;
import TPI.AjedrezApi.services.JugadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JugadorServiceImpl implements JugadorService {
    @Autowired
    private JugadorJpaRepository playerJpaRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Jugador getJugadorById(Long id) {
        Optional<JugadorEntity> jugadorEntityOptional = playerJpaRepository.findById(id);
        if (jugadorEntityOptional.isEmpty()) {
            throw new JugadorNotFoundException(JugadorEntity.class, id);
        }

        JugadorEntity jugadorEntity = jugadorEntityOptional.get();

        if (jugadorEntity.getNombre() == null) {
            throw new JugadorNotFoundException(JugadorEntity.class, id);
        }

        return modelMapper.map(jugadorEntity, Jugador.class);
    }

    @Override
    public List<Jugador> getJugadores() {
        List<JugadorEntity> lista = playerJpaRepository.findAll();

        List<Jugador> jugadores = new ArrayList<>();
        for (JugadorEntity x:lista) {
            Jugador jugador = new Jugador();
            jugador.setId(x.getId_jugador());
            jugador.setNombre(x.getNombre());
            jugador.setPuntos(x.getPuntos());
            jugador.setPartidas_ganadas(x.getPartidas_ganadas());
            jugador.setPartidas_perdidas(x.getPartidas_perdidas());
            jugadores.add(jugador);
        }
        jugadores.sort(new Comparator<Jugador>() {
            @Override
            public int compare(Jugador u1, Jugador u2) {
                return u1.getId().compareTo(u2.getId());
            }
        });
        return jugadores;
    }

    @Override
    public Jugador createJugador(Jugador jugador) {
        Optional<JugadorEntity> jugadorEntityOptional = playerJpaRepository.findByNombre(jugador.getNombre());

        if (jugadorEntityOptional.isPresent()) {
            throw new JugadorAlreadyExistsException("Ya existe un jugador con el nombre: " + jugador.getNombre());
        }

        JugadorEntity playerEntity = modelMapper.map(jugador, JugadorEntity.class);
        JugadorEntity playerEntitySaved = playerJpaRepository.save(playerEntity);

        return modelMapper.map(playerEntitySaved, Jugador.class);
    }

    @Override
    public Jugador updateJugadoresJuego(Jugador jugador,boolean esGanador,boolean huboEmpate) {
        Optional<JugadorEntity> jugadorEntityOptional = playerJpaRepository.findById(jugador.getId());

        if (jugadorEntityOptional.isEmpty()) {
            throw new JugadorNotFoundException(JugadorEntity.class, jugador.getId());
        }

        JugadorEntity jugadorEntity = jugadorEntityOptional.get();

        if (huboEmpate) {
            // Empate
            jugadorEntity.setPuntos(jugadorEntity.getPuntos() + 1);
        } else if (esGanador) {
            // El jugador ganó
            jugadorEntity.setPuntos(jugadorEntity.getPuntos() + 3);
            jugadorEntity.setPartidas_ganadas(jugadorEntity.getPartidas_ganadas() + 1);
        } else {
            // El jugador perdió
            jugadorEntity.setPartidas_perdidas(jugadorEntity.getPartidas_perdidas() + 1);
        }

        JugadorEntity playerEntitySaved = playerJpaRepository.save(jugadorEntity);
        return modelMapper.map(playerEntitySaved, Jugador.class);
    }


}
