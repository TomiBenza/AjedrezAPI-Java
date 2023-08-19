package TPI.AjedrezApi;

import TPI.AjedrezApi.controllers.JuegoController;
import TPI.AjedrezApi.controllers.JugadorController;
import TPI.AjedrezApi.services.JuegoService;
import TPI.AjedrezApi.services.JugadorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private JugadorController jugadorController;

    @Autowired
    private JuegoController juegoController;

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private JuegoService juegoService;

    @Test
    public void contextLoads() throws Exception{
        assertThat(jugadorController).isNotNull();
        assertThat(jugadorService).isNotNull();
        assertThat(juegoService).isNotNull();
        assertThat(juegoController).isNotNull();
    }
}
