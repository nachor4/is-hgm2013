<%@ page import="com.usuario.Login" %>

<%
	//Si hay POST, hago el login | Si login OK, redirijo a reversi

	if ("POST".equalsIgnoreCase(request.getMethod())) {
		// Form Enviado
		
		Login userLogin = new Login();
		
		if (userLogin.checkPassword(
			request.getParameter("user"),
			request.getParameter("password")) ){
			
			//Redirect
			session.setAttribute("user", request.getParameter("user"));
			pageContext.forward("/reversi/");
		}
	}

%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Testing Websockets</title>
	</head>
	<body>
		<h1>REVERSI!</h1>
		<h2>Login</h2>
<%
	if ("POST".equalsIgnoreCase(request.getMethod())) {
		//Se recibieron datos POST pero no se realizó login.
		out.Print("<p><b>Usuario y Password Icorrecto</b></p>");
	}
%>
		<form action="" method="post">
			<p>
				<label for="user">Usuario: </label>
				<input name="user" type="text">
			</p>
			<p>
				<label for="password">Password: </label>
				<input name="password" type="text">
			</p>
			<p>
				<input type="submit" value="Ingresar">
			</p>
		</form>
		
		<p>
			¿aún no tenes una cuenta? <a href="register.jps">REGISTRATE!</a>
		</p>
		
		<p>
			¿si no recuerdas tu eMail haz click <a href="reset.jsp">aquí</a>
		</p>
	</body>
</html>
