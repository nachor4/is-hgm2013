<html>
	<head>
		<title>Reversi</title>
		<link type="text/css" rel="stylesheet" href="/css/normalize.css" />
		<link type="text/css" rel="stylesheet" href="theme/base.css" />
		<link type="text/css" rel="stylesheet" href="theme/id/theme.css" />
		<link rel="shortcut icon" href="/favicon.ico" />
	</head>
	<body>
		<div id="header">
			<div class="wrapper">
				<h1>REVERSI!</h1>
				<a href="/logout.jsp" id="logout">Logout</a>
			</div>
		</div>
		
		<div id="wrapper" class="wrapper">
			<ul id="tablero" class=""> <!-- playing | waitingass-->
			</ul>
			
			<div id="sec"></div>
		</div>
		
		<div id="footer">
			<div class="wrapper">
				&copy; <a href="http://www.ignacioherrero.com.ar/">Ignacio Herrero</a> | Nicolás Gomez | Gullermo Morilla
			</div>
		</div>
		<!--<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>-->
		<script>window.jQuery || document.write('<script src="/js/jquery-2.0.3.min.js"><\/script>')</script>
		<script src="js/reversi.js"></script>
	</body>
</html>
