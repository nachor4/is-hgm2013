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
		return;
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
				<p class="right">Hola <%= userName %>! | <a href="/logout.jsp">LogOut</a></p>
				<form action="/reversi/play/" method="post">
					<p>
						<label for="nivel">Nivel </label>
						<select name="nivel">
							<option value="facil">Fácil</option>
							<option value="medio">Medio</option>
							<option value="dificil">Dificil</option>
						</select>
					</p>
					<p>
						<label for="tema">Tema </label>
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
			</div>
		</div>		
	</body>
</html>
