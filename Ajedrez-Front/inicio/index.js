let existeJuego = false;
let juegoInfo = "";

function partidaEnCurso() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'http://localhost:8080/juego/existeJuegoEnCurso',
            type: 'GET',
            success: function (response) {
                console.log(response)
                existeJuego = response.existeJuego
                juegoInfo = response.infoJuego
                resolve(response);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
                reject(errorThrown);
            }
        });
    });

}
document.getElementById("jugar").onclick = function () {
    partidaEnCurso().then(function (res) {
        if (existeJuego) {
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-success ms-2 ',
                    cancelButton: 'btn btn-danger '
                },
                buttonsStyling: false
            })

            swalWithBootstrapButtons.fire({
                title: 'Se encontró una partida en curso ¿Quiere cargarla?',
                text: juegoInfo,
                icon: 'info',
                showCancelButton: true,
                confirmButtonText: 'Cargar partida',
                cancelButtonText: 'Crear una nueva',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    swalWithBootstrapButtons.fire(
                        'Cargando partida', '',
                        'success'
                    )
                    setTimeout(function () { location.href = "juego.html" }, 1000);
                } else if (
                    /* Read more about handling dismissals below */
                    result.dismiss === Swal.DismissReason.cancel
                ) {
                    location.href = "jugadores.html"
                }
            })
        }
        else {
            location.href = "jugadores.html"

        }
    })
        .catch(function (error) {
            console.log(error);
            return;
        });
    return;

};
document.getElementById("ayuda").onclick = function () {
    location.href = "ayuda.html";
};