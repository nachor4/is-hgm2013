<%
	//Si hay POST, reinicio el password
	//Si reinicio OK, redirijo a home

%>
<html>
	<head>
		<meta charset="utf-8">
		<title>Testing Websockets</title>
	</head>
	<body>
		<h1>Reiniciar Password</h1>
		<form action="login.jsp" method="post">
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
