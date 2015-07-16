/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ws;

function creaWebSocket(url)
{
    if (ws == null) {
        url = "ws://"+url + "/impresionws/endpoint";
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
            alert(message);
        };
        
    }
}
;

function enviaMensaje(mensaje)
{
    ws.send(mensaje);
    console.log('Enviado');
}
;

