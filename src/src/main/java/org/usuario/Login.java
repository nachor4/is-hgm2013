package java.org.usuario;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Login extends User
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Login(){
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public boolean check(String usuario, String password) {
		// TODO : to implement
		return false;	
	}
	
	/**
	 * 0: OK<div>1: duplicado</div><div>2: email registrado</div><div>3: etc!</div>
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public int newUsuario(String usuario, String password, String nombre, String email) {
		// TODO : to implement
		return 0;	
	}
	
	/**
	 * 0: ok<div>1: email y usuario no coinciden</div><div>2: email o usuario no encontrado</div>
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public int reset(String usuario, String email) {
		// TODO : to implement
		return 0;	
	}
	
	/**
	 * si el ID no esta definido, throw error.
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public String getID() {
		// TODO : to implement
		return "";	
	}
	
}

