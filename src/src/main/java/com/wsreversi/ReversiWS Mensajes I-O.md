ReversiWS: Mensajes I/O
=======================


## Inputs
	operacion : INIT
	id : String -- User ID
	nivel : String (facil|medio|dificil)

	operacion : MOVE
	posx : String (1|2|3|4|5|6|7|8)
	posy : String (1|2|3|4|5|6|7|8)

	operacion : QUIT


## Outputs
	operacion : TIMEOUT|INIT
	turno : Boolean
	mensaje : String

	operacion : CANCEL|END|QUIT
	data : Scoring

	operacion : ERROR|MSG
	data: String

	operacion : MOVER
	hecho : Boolean

		hecho(true)  -> turno : Boolean
		hecho(true)  -> data  : ResultadoMovimiento

		hecho(false) -> data  : String
		hecho(false) -> ficha : Ficha
