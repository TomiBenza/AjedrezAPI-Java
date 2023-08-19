$(document).ready(() => {
/*
    const itemsPerPage = 10;

    // Función para cargar los próximos movimientos
    let totalMovimientosCargados = 0; // Variable para almacenar la cantidad total de movimientos cargados*/

    const loadMoreMovimientos = () => {
        const url = `http://localhost:8080/juego/getHistorialMovimientos`;

        $.ajax({
            url: url,
            type: 'GET',
            success: function (response) {
                response.forEach(movimiento => {
                    const li = document.createElement("li");
                    li.innerHTML = `<li class="list-group-item" style="decoration:none;">${movimiento}</li>`;
                    $("#historialMovimientosContainer").prepend(li);
                });

                totalMovimientosCargados += response.length;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log('Error en la solicitud AJAX:', errorThrown);
                alert("Error al cargar el historial de movimientos");
            }
        });
    };
/*
    // Función para verificar si el usuario ha llegado al final de la página
    const checkScrollEnd = () => {
        const scrollHeight = $(document).height();
        const scrollPosition = $(window).height() + $(window).scrollTop();

        if ((scrollHeight - scrollPosition) / scrollHeight === 0) {
            loadMoreMovimientos();
        }
    };

    // Agrega un evento de scroll para verificar cuando el usuario llega al final de la página
    $(window).scroll(checkScrollEnd);*/

    // Llama a loadMoreMovimientos para cargar los primeros movimientos
    loadMoreMovimientos();
})