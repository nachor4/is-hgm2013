<%
	Boolean hayPOST = "POST".equalsIgnoreCase(request.getMethod());
	String user = "";
	String nivel = "";
	String tema = "";		
	
	if (hayPOST){
		session.setAttribute("nivel", request.getParameter("nivel"));
		session.setAttribute("tema", request.getParameter("tema"));
		response.sendRedirect("/reversi/play/"); 
		return;
	}else{
		if (session.getAttribute("user") == null || (String)session.getAttribute("user") == ""){
			//Si el usuario NO esta logueado, lo redirijo al juego
			System.out.println("no user");
			response.sendRedirect("/"); 
			return;
		}	

		if (session.getAttribute("nivel") == null || (String)session.getAttribute("nivel") == ""
			|| session.getAttribute("tema") == null || (String)session.getAttribute("tema") == ""){
			System.out.println("no nivel o theme");
			//Si el nivel NO esta logueado, lo redirijo al configurar el juego
			response.sendRedirect("/reversi"); 
			return;
		}	
		
		user = (String)session.getAttribute("user");
		nivel = (String)session.getAttribute("nivel");
		tema = (String)session.getAttribute("tema");		
	}

%>
<html>
	<head>
		<title>Reversi</title>
		<link type="text/css" rel="stylesheet" href="/css/normalize.css" />
		<link type="text/css" rel="stylesheet" href="themes/base.css" />
		<link type="text/css" rel="stylesheet" href="themes/<%= tema %>/theme.css" />
		<link rel="shortcut icon" href="/favicon.ico" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
	<div id="mainwrap" data-user="<%= user %>" data-nivel="<%= nivel %>">
		<div id="header">
			<div class="wrapper">
				<h1>REVERSI!</h1>
				<a href="/logout.jsp" id="logout">Logout</a>
			</div>
		</div>
		
		<div id="main" class="wrapper clearfix">
			<div id="tablero-wrapper">
				<ul id="tablero" class=""> <!-- playing | waiting -->
				</ul>
			</div>
			
			<div id="sec">
				<div id="cuenta-fichas">
					<i>Fiachas</i>
					<div class="blancas"><b>Blancas</b><span>2</span></div>
					<div class="negras"><b>Negras</b><span>2</span></div>
				</div>
				<div id="scoring"></div>
			</div>
		</div>
		
		<div id="footer">
			<div class="wrapper">
				&copy; <a href="http://www.ignacioherrero.com.ar/">Ignacio Herrero</a> | Nicolás Gomez | Gullermo Morilla
			</div>
		</div>
	</div>
	<!--<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>-->
	<script>window.jQuery || document.write('<script src="/js/jquery-2.0.3.min.js"><\/script>')</script>
	<script src="js/reversi.js"></script>
	</body>
</html>
