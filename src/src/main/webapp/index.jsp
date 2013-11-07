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

				<h2>Login</h2>
				<%
					//Se recibieron datos POST pero no se realizó login.
					if (hayPOST) out.println("<p class=\"error\"><b>Usuario y Password Incorrecto</b></p>");
				%>
				<form action="" method="post">
					<p>
						<label for="user">Usuario </label>
						<input name="user" type="text">
					</p>
					<p>
						<label for="password">Password </label>
						<input name="password" type="text">
					</p>
					<p>
						<input type="submit" value="Ingresar">
					</p>
				</form>
				<br>
				<p>
					<a href="register.jsp">REGISTRATE!</a>
				</p>
				
				<p>
					<a href="reset.jsp">Obtener nuevo Password</a>
				</p>
			</div>
		</div>
	</body>
</html>
