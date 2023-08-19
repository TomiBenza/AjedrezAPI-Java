let ultimo=1;
let idJugador;
let nombreJugador;
const J1 = document.getElementById("inputJ1")
const J2 = document.getElementById("inputJ2")
J1.setAttribute("idJ1","-")
J1.setAttribute("idJ2","-")

const cargarJugador=(evento)=>{
    idJugador=evento.target.getAttribute("data-id") 
    nombreJugador=evento.target.getAttribute("nombre") 
    
    if($("#inputJ1").val()==""||ultimo!=1){
        
        $("#inputJ1").val(nombreJugador)
        J1.idJ1=idJugador
        console.log("P1 ID: "+J1.idJ1)
        ultimo=1;
    }
    else if($("#inputJ2").val()==""|| ultimo!=2){
        
        $("#inputJ2").val(nombreJugador)
        J2.idJ2=idJugador
        console.log("P2 ID: "+J2.idJ2)
        ultimo=2;
    }
}

$(document).ready(() => {

    const save = () => {
        $("#agregarJugador").on("click", function () {

            const datosJugador = {
                id: 0,
                nombre: $("#inputNombre").val(),
                puntos: 0,
                partidas_ganadas: 0,
                partidas_perdidas: 0,
            }
            $.ajax({
                url: 'http://localhost:8080/jugadores/crearJugador',
                contentType: 'application/json',
                type: 'POST',
                data: JSON.stringify(datosJugador),
                dataType: 'json'
            })
            .done(function (data) {
                console.log(data)
                $("#mensajes").html("Jugador creado").css('display', 'block');
                list();
                limpiarDatos();
                console.log('Jugador registrado')
                console.log(datosJugador)
                $('#tablaJugadores').DataTable().destroy();
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
                      });
                } catch (error) {
                    console.log('Error al analizar la respuesta JSON:', error);
                    alert('Error en la solicitud AJAX');
                }
            })
            
        })
    }

// Listado jugadores
const list = () => {
    $.ajax({
        url: 'http://localhost:8080/jugadores/getAllJugadores',
        type: 'GET',
        dataType: 'json',
        success: function (res) {
            
            console.log(res);
            let data = '';
            res.forEach(element => {
                data += `
                    <tr>
                        <td>${element.nombre}</td>
                        <td>${element.puntos}</td>
                        <td>${element.partidas_ganadas}</td>
                        <td>${element.partidas_perdidas}</td>
                        <td>
                            <button class="btn btn-primary" data-id="${element.id}" nombre="${element.nombre}" onclick="cargarJugador(event)">
                                Seleccionar
                            </button>
                        </td>
                    </tr>
                `
            });
            $('#tbodyJugadores').html(data);
            cargarDataTable()
        }
    })

}
const cargarDataTable = () =>{
    // Inicializar DataTables
    $('#tablaJugadores').DataTable({
        language: {
            search: "",
              "sProcessing": "Procesando...",
              "sLengthMenu": "Mostrar _MENU_ jugadores",
              "sZeroRecords": "No se encontraron resultados",
              "sEmptyTable": "Ningún dato disponible en esta tabla",
              "sInfo": "Mostrando jugadores del _START_ al _END_ de un total de _TOTAL_ jugadores",
              "sInfoEmpty": "Mostrando jugadores del 0 al 0 de un total de 0 jugadores",
              "sInfoFiltered": "(filtrado de un total de _MAX_ jugadores)",
              "sInfoPostFix": "",
              "sSearch": "Buscar:",
              "sUrl": "",
              "sInfoThousands": ",",
              "sLoadingRecords": "Cargando...",
              "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
              },
              "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
              }
            
          },
          lengthChange: false
      }).order([[2, 'desc']]).draw();
    }
    var tablaJugadores = $('#tablaJugadores')
    tablaJugadores.on('draw.dt', function() {
      var input = $('#tablaJugadores_wrapper .dataTables_filter input');
      input.attr('placeholder', 'Buscar Jugadores');
    });
  



//Limpiar datos
const limpiarDatos = () => {
    $("#inputNombre").val('');
}

// Llamadas a funciones
list();
save();
})