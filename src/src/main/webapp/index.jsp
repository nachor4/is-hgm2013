<%
	//Si hay POST, hago el login
	//Si login OK, redirijo a reversi

%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Testing Websockets</title>
	</head>
	<body>
		<h1>REVERSI!</h1>
		<h2>Login</h2>
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
