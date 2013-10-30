<%
	//Si hay POST, registro... sino, muestro el form
	//Si registro OK, redirijo a Jugar

%>
<html>
	<head>
		<meta charset="utf-8">
		<title>Testing Websockets</title>
	</head>
	<body>
		<h1>Nuevo Usuario</h1>
		<form action="" method="post">
			<p>
				<label for="user">Usuario: </label>
				<input name="user" type="text">
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
