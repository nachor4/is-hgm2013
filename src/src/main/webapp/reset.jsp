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
				mensajeRegistracion = "<p class=\"ok\"><b>Su password ha sido reiniciado</b> La nueva contraseña es '12345'</p>";
			break;
			
			case 1:
				mensajeRegistracion = "<p class=\"error\"><b>USUARIO INEXISTENTE.</b> Escriba nuevamente su usuario</p>";
			break;
			
			case 2:
				mensajeRegistracion = "<p class=\"error\"><b>Error Inesperado.</b> Intentalo nuevamente</p>";
			break;
		}
	}
%>
<html>
	<head>
		<title>Reversi</title>
		<meta charset="UTF-8">
		<link type="text/css" rel="stylesheet" href="/css/normalize.css" />
		<link type="text/css" rel="stylesheet" href="/css/base.css" />
	</head>
	<body>
		<div id="wrapper">
			<h1>REVERSI!</h1>
			<div id="cont">	
				<h2>Reiniciar Password</h2>
				<%
					//Se recibieron datos POST y se proceso la información
					if (hayPOST) {
						out.println(mensajeRegistracion);
						if (res == 0) out.println("<p class=\"center\"><a href=\"/\">Volver al Home</a></p>");
					}
				%>			
				<form action="" method="post">
					<p>
						<label for="user">Usuario </label>
						<input name="user" type="text">
					</p>
					<p>
						<input type="submit" value="Reset Password">
					</p>
				</form>		
				<p class="center"><a href="/">Volver al home</a></p>
			</div>
		</div>
	</body>
</html>
