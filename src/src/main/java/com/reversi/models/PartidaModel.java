package com.reversi.models;

import org.javalite.activejdbc.Model;

public class PartidaModel extends Model {
  static{
	   
      validatePresenceOf("elNegro", "elBlanco", "dificultad","ReversiObserver");
      }
}

    
