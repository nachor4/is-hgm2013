<%@ page import="com.usuario.Login" %>
<%
	//Si hay POST, registro... sino, muestro el form
	//Si registro OK, redirijo a Jugar

	Boolean hayPOST = "POST".equalsIgnoreCase(request.getMethod());

	String mensajeRegistracion = "";
	
	if(!hayPOST){
		session.invalidate();
	}else{
		Login userLogin = new Login();
		
		String user = request.getParameter("user");
		String password = request.getParameter("password");		
		String nombre = request.getParameter("nombre");		
		String email = request.getParameter("email");		
				
		System.out.println(user + " | " + password + " | " + nombre + " | " + email);
		
		switch (Login.newUsuario(user, password, nombre, email)){
			case 0:
				//Se registro el usuario, lo redirijo a Jugar.
				session.setAttribute("user", user);
				pageContext.forward("/reversi/");
			break;
			
			case 1:
				mensajeRegistracion = "<p><b>USUARIO DUPLICADO.</b> Elige otro nombre de usuario</p>";
			break;
			
			case 2:
				mensajeRegistracion = "<p><b>Error Inesperado.</b> Intentalo nuevamente</p>";
			break;
		}
	}
%>
<html>
	<head>
		<meta charset="utf-8">
		<title>Testing Websockets</title>
	</head>
	<body>
		<h1>Nuevo Usuario</h1>
<%
	if (hayPOST) {
		//Se recibieron datos POST pero no se realizó la carga del usuario.
		out.println(mensajeRegistracion);
	}
%>		
		<form action="" method="post">
			<p>
				<label for="user">Usuario: </label>
				<input name="user" type="text">
			</p>
			<p>
				<label for="nombre">Nombre: </label>
				<input name="nombre" type="text">
			</p>
			<p>
				<label for="email">Email: </label>
				<input name="email" type="text">
			</p>			
			<p>
				<label for="password">Password: </label>
				<input name="password" type="text">
			</p>
			<p>
				<input type="submit" value="Registrarse">
			</p>			
		</form>
	</body>
</html>
