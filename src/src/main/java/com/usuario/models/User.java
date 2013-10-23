package com.usuario.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static{
      validatePresenceOf("id", "password", "name", "email", "won", "lost", "abandoned");
      validateEmailOf("email");
  }
}
