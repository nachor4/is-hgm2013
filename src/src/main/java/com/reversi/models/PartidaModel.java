package com.reversi.models;

import org.javalite.activejdbc.Model;

public class PartidaModel extends Model {
  static{
      validatePresenceOf("id", "dificultad", "tablero", "tiempoUltMov", "elNegro", "elBlanco", "cantMovimientos", "estadoJuego", "turnoActual");
      }
}

    
