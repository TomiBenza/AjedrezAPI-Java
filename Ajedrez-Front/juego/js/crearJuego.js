
$(document).ready(() => {
    $("#crearJuego").on("click", function () {
        crearJuego();
    })

    const limpiarJugadoresJuego = () => {
        $("#inputJ1").val("");
        $("#inputJ2").val("");
    }

    const crearJuego = () => {
         
        
        
        const datosJuegoNuevo = {
            id_jugador_blancas: J1.idJ1,
            id_jugador_negras: J2.idJ2,
        }
        $.ajax({
            url: 'http://localhost:8080/juego/crearJuego',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(datosJuegoNuevo),
            dataType: 'json'
        })
            .done(function (data) {
                limpiarJugadoresJuego()

                Swal.fire({
                    icon: 'success',
                    title: "¡Iniciando el juego!",
                    text: data.mensajeJuego,
                    showConfirmButton: false
                  });
                setTimeout(function(){ window.location = 'juego.html'; }, 1500)
                

            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                try {
                    var responseJson = JSON.parse(jqXHR.responseText);
                    var errorMessage = responseJson.message;
                    Swal.fire({
                        icon: 'error',
                        text: errorMessage
                      });
                } catch (error) {
                    console.log('Error al analizar la respuesta JSON:', error);
                    alert('Error en la solicitud AJAX');
                }
            })
    }

})