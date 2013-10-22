package java.org.usuario.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static{
      validatePresenceOf("id", "password", "name", "email");
      validateEmailOf("email");
  }
}
