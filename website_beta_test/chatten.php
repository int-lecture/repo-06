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
    <li><a class="active" href="chatten.php">Messenger</a></li>
</ul>

<div class="content" id "myContent">
    <header>
        <h1 id="messenger">Messenger</h1>
    </header>
	
<br/>
<!--iframe bildet Fenster für Unser Chat, und ruft html datei-->
<iframe src="chat.html" width="60%" height="40%" name="Nachrichten">
</iframe>

<?php

$eingabe1='<meta http-equiv="refresh" content="2;URL=chat.html">';
$textFarbe='<body bgcolor="#ffffff" text="#000000">';

$text=$_POST['textf'];

//möglichkeit alles leeren
if(isset($_GET['clear'])){
		//die log datei leeren
		//jedesmal wird gelöscht
		//und durch den command zum refreshen
		$leer = fopen('chat.html', 'w+');
		fputs($leer, $eingabe1."\n");
		fputs($leer, $textFarbe."\n");
		fclose($leer);
/// anderfalls wird nach Eingabe überprüft
}else{
	if($text!=""){
		$add=fopen('chat.html','a');
		fwrite($add, $_GET["user"]);
		fputs($add, ": <b>"."");
		fputs($add, $text."\n");
		fputs($add, "</b><br>"."\n");
		fclose($add);
	}
}
?>
<form action="" method="POST" target="">
<!-- TextFeld -->
<input name="textf" type="text" size="50" maxlength="80">
<!-- Senden Knopf -->
<input name="button" type="Submit" value="Send">
<!-- Nachrichtenfeld leeren -->
<input name="clear" type="Submit" value="clear">
<br/>

</center>
</body>
</html>