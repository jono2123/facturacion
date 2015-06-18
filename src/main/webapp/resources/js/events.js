/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ws;
var usuario = null;
$(document).ready(function (e) {
    var url = "ws://localhost:90/impresionws/endpoint";

    ws = new WebSocket(url);
    ws.onopen = function (event) {
        console.log('Conectado');
    };
    ws.onerror = function (event) {
        console.log(event.data);
    };
    ws.onmessage = function (event) {
        var message = event.data;
        // pintamos mensaje
        addMessage(message);
    };


});
function enviaMensaje(mensaje)
{
    ws.send(mensaje);
}

