if(typeof console === "undefined") console = {log: function(){}}; //Por si el navegador no tiene consola... por si IE

var tablero, color, colorAdversario, webSocket; //Variables  globales :s

$(document).ready(function(){ //Espero a que el DOM este generado
	/*
	 *	tablero: class (waiting|playing) 
	 */
	
	//Agrego los cuadros del tablero
	tablero = $("#tablero");
		
	for(var i=0; i<8; i++)  
		for(var j=0; j<8; j++)
			$(tablero).append('<li id="c'+i+j+'"><a href="#"></a></li>');	  	  

	//Configuro fichas iniciales
	$("#c33").addClass('blanca');
	$("#c34").addClass('negra');
	$("#c43").addClass('negra');
	$("#c44").addClass('blanca');
	
	//Binding click en celda
	$('li a',tablero).bind('click',clickCelda);
	
	$(tablero).addClass('waiting');
	
	//Conexion con el websocket
	webSocket = new WebSocket('ws://localhost:8080/wsreversi');

	if (typeof webSocket != undefined){
		//Binding de eventos del websocket
		webSocket.onerror = function(event) {wsError(event);};
		webSocket.onopen = function(event) {wsError(event);};
		webSocket.onclose = function(event) {wsError(event);};
		webSocket.onmessage = function(event) {wsError(event);};
		
		//Cleanup 
		$(window).unload(function() {
	    	wsClose(webSocket);
		});		
	}
});
	

	function wsError(event) {
		if (event.type == 'error') alert("Error en el servidor");
		console.log(event);
	};

	function wsOnOpen(event) {
		console.log("connected");
	};

	function wsOnClose(event) {
		console.log("disconnected");
	};

	function wsOnMessage(event) {
		data = $.parseJSON(event.data);
		
		if(data.operacion != undefined)
		switch (data.operacion){
			case "INIT":
				color = (data.turno)?'negra':'blanca';
				colorAdversario = (!data.turno)?'negra':'blanca';
			case "TIMEOUT":
				alert(data.mensaje);
				$(tablero).attr('class',(data.turno)?'playing turno':'waiting');
			break;
			
			case "CANCEL":
			case "END":
			case "QUIT":
				$(tablero).attr('class','finalizado');
				showScoring(data.data);
			break;
			
			case "ERROR":
			case "MSG":
				alert(data.data);
			break;
			
			case "MOVER":
				if ($(tablero).hasClass('turno')) //Respuesta de su jugada
					if (data.hecho){
						
						//Movimiento válido
						actualizarTablero(data.data, color);
						$(tablero).removeClass('class','turno'); 
																		
					}else{ 
						
						//El movimiento no era válido
						$("#c"+hecho.ficha.x + hecho.ficha.y).attr('class',''); 	//Quito la ficha
						$(tablero).attr('class','playing turno'); 					//habilito el tablero nuevamente
						alert(data.data); 											//Muestro el mensaje
					}
					
				else //Actualizacion por jugada del contrincante
					if (data.hecho){
						
						//El contrincante hizo un movimiento válido
						actualizarTablero(data.data, colorAdversario);
						$(tablero).attr('class','playing turno');
					}
			break;
		}
		
	}; //onMessage
	
	function wsSend(WS, obj) {
		if (WS.readyState == WS.OPEN)
			if (typeof obj == 'object') WS.send($(obj).serialize());
			else {alert("Error al enviar la operacion al servidor"); console.log("Se intento enviar infomacion no válida al websocket"); return false;}
		else {console.log("No hay conexion con el servidor"); return false;}
		
		return true;
	}

	function wsClose(WS){
		if (WS.readyState == WS.OPEN) WS.close();
	}

	function actualizarTablero(data, colorFicha){
		//Cambio las fichas
		$(data.modificaciones).each(function(i,ficha){
			$("#c"+ficha.x + ficha.y).attr('class',colorFicha);
		});
		
		//Actualizo el contador		
		$("#cuenta-fichas .blancas").innerHTML(data.cantBlancas);
		$("#cuenta-fichas .negras").innerHTML(data.cantNegras);
		
	}
	
	function showScoring(scoring){
		switch(scoring.estaPartida){
			case 3:
				resultado = "ganador";
			break;
			
			case -1:
				resultado = "perdedor";
			break;
			
			case -3:
				resultado = "cobarde";
			break;
		}
		
		$(tablero).addClass(resultado);
		
		$("#scoring")
			.append("Partidas Ganadas: " + scoring.ganadas)
			.append("<br>Partidas Perdidas: " + scoring.perdidas)
			.append("<br>Partidas Abandonadas: " + scoring.abandonadas)
			.append("<b>Puntaje General: </b>" + scoring.scoreMas);
			
		if (scoring.estaPartida == 3) alert("Felicitaciones!");
		else alert("Partida Finalizada");
	}
	
	function clickCelda(e){
		e.preventDefault();
		
		celda = e.target.parentElement; //li
		
		if ($(tablero).hasClass('waiting')) return false; //no es su turno
		if ($(celda).hasClass('blanca') || $(celda).hasClass('negra')) return; //La celda esta ocupada
		
		$(celda).attr('class',color);
		
		ok = wsSend({
			operacion:'MOVE', 
			posx: $(celda).attr('id').substr(1,1), 
			posy: $(celda).attr('id').substr(2,1)
		});
		
		if (ok) $(tablero).attr('class','waiting turno');
	}