<%@ page import="com.usuario.Login" %>

<%
	if (session.getAttribute("user") != null && (String)session.getAttribute("user") != ""){
		//Si el usuario esta logueado, lo redirijo al juego
		pageContext.forward("/reversi/");
		return;
	}
	
	
	//Si hay POST, hago el login | Si login OK, redirijo a reversi

	Boolean hayPOST = "POST".equalsIgnoreCase(request.getMethod());

	if (hayPOST) {
		// Form Enviado
		
		Login userLogin = new Login();
		
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
		if (userLogin.checkPassword(user,password)){
			//Redirect
			session.setAttribute("user", user);
			pageContext.forward("/reversi/");
			return;
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
	if (hayPOST) {
		//Se recibieron datos POST pero no se realizó login.
		out.println("<p><b>Usuario y Password Incorrecto</b></p>");
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
			¿aún no tenes una cuenta? <a href="register.jsp">REGISTRATE!</a>
		</p>
		
		<p>
			¿si no recuerdas tu password haz click <a href="reset.jsp">aquí</a>
		</p>
	</body>
</html>
