<%@ page import="com.usuario.Login" %>
<%
	//Si hay POST, registro... sino, muestro el form
	//Si registro OK, redirijo a Jugar

	Boolean hayPOST = "POST".equalsIgnoreCase(request.getMethod());

	String mensajeRegistracion = "";
	int res = 3;
	
	if(!hayPOST){
		session.invalidate();
	}else{
		Login userLogin = new Login();
		
		String user = request.getParameter("user");
		
		res = Login.resetPassword(user);
		
		switch (res){
			case 0:
				//Se reinicio el password, lo redirijo al login
				mensajeRegistracion = "<p><b>Su password ha sido reiniciado</b> La nueva contraseña es '12345'</p>";
			break;
			
			case 1:
				mensajeRegistracion = "<p><b>USUARIO INEXISTENTE.</b> Escriba nuevamente su usuario</p>";
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
		<h1>Reiniciar Password</h1>
<%
	if (hayPOST) {
		//Se recibieron datos POST y se proceso la información
		out.println(mensajeRegistracion);
		if (res == 0) out.println("<a href=\"/\">Volver al Home</a>");
	}
%>			
		<form action="" method="post">
			<p>
				<label for="user">Usuario: </label>
				<input name="user" type="text">
			</p>
			<p>
				<input type="submit" value="Reset Password">
			</p>
		</form>		
	</body>
</html>
