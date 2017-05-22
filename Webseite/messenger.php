<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/stylesheet.css">
    <meta charset="UTF-8">
</head>
<body>

<ul class="sidenav">
    <li><a href="index.html">Willkommen</a></li>
    <li><a href="firma.html">Unsere Firma</a></li>
    <li><a href="produkt.html">Unsere Produkte</a></li>
    <li><a href="impressum.html">Impressum</a></li>
    <li><a href="registrieren.html">Registrieren</a></li>
    <li><a class="active" href="messenger.php">Messenger</a></li>
</ul>

<div class="content" id "myContent">
    <header>
        <h1 id="messenger">Messenger</h1>
    </header>
<form action="chatten.php" method="post">
	 <input type="text" name="name" placeholder="Name eingeben"/?><br><br>
	 <input type="text" name="chatpartner" placeholder="Chatpartner eingeben"/?> <br><br><br>
	 <input type="submit"  value="Chat starten!" />
</form>

</body>
</html>
