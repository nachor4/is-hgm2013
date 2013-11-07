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
				mensajeRegistracion = "<p class=\"error\"><b>USUARIO DUPLICADO.</b> Elige otro nombre de usuario</p>";
			break;
			
			case 2:
				mensajeRegistracion = "<p class=\"error\"><b>Error Inesperado.</b> Intentalo nuevamente</p>";
			break;
		}
	}
%>

<!DOCTYPE html>
<html lang="es-AR">
	<head>
		<title>Reversi</title>
		<meta charset="UTF-8">
		<link type="text/css" rel="stylesheet" href="/css/normalize.css" />
		<link type="text/css" rel="stylesheet" href="/css/base.css" />
		<link rel="shortcut icon" href="/favicon.ico" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<div id="wrapper">
			<h1><a href="/">REVERSI!</a></h1>
			<div id="cont">
				<h2>Nuevo Usuario</h2>
				<%
					//Se recibieron datos POST pero no se realizó la carga del usuario.
					if (hayPOST) out.println(mensajeRegistracion);
				%>		
				<form action="" method="post">
					<p>
						<label for="user">Usuario </label>
						<input name="user" type="text" maxlength="20" required>
					</p>
					<p>
						<label for="nombre">Nombre </label>
						<input name="nombre" type="text" maxlength="60" required>
					</p>
					<p>
						<label for="email">Email </label>
						<input name="email" type="email" maxlength="60" required>
					</p>			
					<p>
						<label for="password">Password </label>
						<input name="password" type="text" maxlength="12" required>
					</p>
					<p>
						<input type="submit" value="Registrarse">
					</p>			
				</form>
			</div>
		</div>
	</body>
</html>
