<%@ page import="com.usuario.Login" %>

<%
	//Realizo las configuraciones y redirijo a jugar.jsp
	
	if (session.getAttribute("user") == null || (String)session.getAttribute("user") == ""){
		//Si el usuario NO esta logueado, lo redirijo al juego
		response.sendRedirect("/"); 
		return;
	}	
	
	String user = (String)session.getAttribute("user");
	
	Login userLogin = new Login();
	
	String userName = "";
	try{
		userName = userLogin.getName(user);
	}catch (Exception e){
		session.invalidate();
		response.sendRedirect("/"); 	
	}

%>

<html>
	<head>
		<title>Testing Websockets</title>
	</head>
	<body>
		<p>Hola <%= userName %>! | <a href="/logout.jsp">LogOut</a></p>
		<h1>REVERSI!</h1>
		<h2>Nuevo Juego</h2>
		<form action="play" method="post">
			<p>
				<label for="nivel">Nivel: </label>
				<select name="nivel">
					<option value="facil">Fácil</option>
					<option value="medio">Medio</option>
					<option value="dificil">Dificil</option>
				</select>
			</p>
			<p>
				<label for="tema">Tema: </label>
				<select name="tema">
					<option value="t1">Tema 1</option>
					<option value="t2">Tema 2</option>
					<option value="t3">Tema 3</option>
				</select>
			</p>
			<p>
				<input type="submit" value="Iniciar Partida">
			</p>
		</form>		
	</body>
</html>
