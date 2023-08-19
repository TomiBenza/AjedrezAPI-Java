$(document).ready(() => {
    $("#btnSalir").hide();
    $("#mensajeEstado").hide()


    const getTablero = () => {
        $.ajax({
            url: 'http://localhost:8080/juego/ver-tablero/html',
            type: 'GET',
            success: function (response) {

                $('#showTablero').html(response.tableroHTML);
                checkEstadoDelJuego(response.estadoJuego);
                mostrarEstado(response.mensajeEstado);
                console.log(response.mensajeEstado)
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
            }
        });
    }

    const getTurno = () => {
        $.ajax({
            url: 'http://localhost:8080/juego/getTurno',
            type: 'GET',
            success: function (response) {
                $('#jugadorTurno').html(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
            }
        });
    }

    const realizarMovimiento = () =>{
        const datosMovimiento = {
            tipoPieza: $("#inputPieza").val(),
            posicion: $("#inputPosInicial").val(),
            moverHacia: $("#inputMoverA").val()
        }
        $.ajax({
            url: 'http://localhost:8080/juego/movimiento',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(datosMovimiento),
            dataType: 'json'
        })
            .done(function (data) {       
                //Mostrar el mensaje de la pieza movida:
                mostrarMensaje(data.piezaMovida)  
                getTablero();
                getTurno();
                limpiarDatos();
                getHistorialMovimientos();
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
                console.log(jqXHR.responseText);
                try {
                    var responseJson = JSON.parse(jqXHR.responseText);
                    var errorMessage = responseJson.message;

                    Swal.fire({
                        icon: 'error',
                        text: errorMessage
                    })
                } catch (error) {
                    console.log('Error al analizar la respuesta JSON:', error);
                    alert('Error en la solicitud AJAX');
                }
            })
    }
    $("#btnMover").on("click", function () {

        if (!validarMovimiento()) {
            return;
        }
        realizarMovimiento();  
    })

    const mostrarEstado = (estado) =>{
        $("#mensajeEstado").html('');
        if(estado==""){
            $("#mensajeEstado").hide()
        }
        else {
            $("#mensajeEstado").show()
            $("#mensajeEstado").append(estado)

        }
    }
    const validarMovimiento = () => {
        const pieza = document.getElementById("inputPieza").value
        const from = document.getElementById("inputPosInicial").value
        const to = document.getElementById("inputMoverA").value

        if (pieza == "" || from == "" || to == "") {
            Swal.fire({
                icon: 'error',
                text: "No se puede realizar el movimiento, asegúrese de haber llenado los 3 campos"
            });
            return false;
        }
        return true;
    }
    function mostrarMensaje(texto) {
        var mensaje = document.getElementById('mensajeExito');
        mensaje.textContent = texto;
        mensaje.classList.remove('oculto');


        // Desaparecer gradualmente después de 3 segundos
        setTimeout(function () {
            mensaje.classList.add('oculto');
        }, 3000);
    }
    const limpiarDatos = () => {
        $("#inputPieza").val("");
        $("#inputPosInicial").val("");
        $("#inputMoverA").val("");
    }

    const getHistorialMovimientos = () => {
        $.ajax({
            url: "http://localhost:8080/juego/getHistorialMovimientos",
            type: 'GET',
            success: function (response) {
                $("#historialMovimientosContainer").html("");
                response.reverse().forEach(movimiento => {

                    const li = document.createElement("li");
                    li.innerHTML = `<li class="list-group-item" style="decoration:none;">${movimiento}</li>`;
                    $("#historialMovimientosContainer").append(li);
                });


            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
                alert("Error al cargar más movimientos");
            }
        });
    };
    const checkEstadoDelJuego = (estado) =>{
        if(estado=="TERMINADO" || estado=="EMPATE"){
            $("#btnMover").hide();
            $("#btnSalir").show();
            $("#btnSalirJuegoCurso").hide();
        }
        
    }

    $("#btnSalir").on("click",function(){
        Swal.fire({
            icon: 'success',
            text: "Saliendo del juego..."
        });
        setTimeout(function(){ window.location = 'jugadores.html'; }, 1500)
    })

    $("#btnSalirJuegoCurso").on("click",function(){
        Swal.fire({
            icon: 'success',
            text: "Guardando y regresando al menu..."
        });
        setTimeout(function(){ window.location = 'index.html'; }, 1500)              
    })
    
    
    getTurno();
    getTablero();
    getHistorialMovimientos();
    
    
})


