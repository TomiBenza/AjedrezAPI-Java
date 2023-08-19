package TPI.AjedrezApi.controllers;

import TPI.AjedrezApi.models.Jugador;
import TPI.AjedrezApi.services.JugadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/jugadores")
@CrossOrigin(origins = {"*"})
public class JugadorController {
    @Autowired
    private JugadorService jugadorService;
    @GetMapping("getJugador/{id}")
    public ResponseEntity<Jugador> getById(@PathVariable Long id){
        return ResponseEntity.ok(jugadorService.getJugadorById(id));
    }

    @CrossOrigin
    @GetMapping("/getAllJugadores")
    public List<Jugador> getAll(){
        return jugadorService.getJugadores();
    }


    @PostMapping("/crearJugador")
    @Validated
    public ResponseEntity<Jugador> savePlayer(@RequestBody @Valid Jugador jugador){
        Jugador jugadorCreado = jugadorService.createJugador(jugador);
        if(jugadorCreado == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ya existe un jugador con ese nombre");
        }
        return ResponseEntity.ok(jugadorCreado);
    }

}
