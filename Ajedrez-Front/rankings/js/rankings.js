$(document).ready(function() {
    const listar = () => {
      $.ajax({
        url: 'http://localhost:8080/jugadores/getAllJugadores',
        type: 'GET',
        dataType: 'json',
        success: function(res) {
          console.log(res);
          let data = '';
          res.forEach(element => {
            data += `
              <tr>
                <td>${element.nombre}</td>
                <td>${element.puntos}</td>
                <td>${element.partidas_ganadas}</td>
                <td>${element.partidas_perdidas}</td>
              </tr>
            `;
          });
          $('#tbodyJugadores').html(data);

          
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
      });
      
    }
    var tablaJugadores = $('#tablaJugadores')
          tablaJugadores.on('draw.dt', function() {
            var input = $('#tablaJugadores_wrapper .dataTables_filter input');
            input.attr('placeholder', 'Buscar Jugadores');
          });
    listar();
  });
  