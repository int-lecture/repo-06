<?php
session_start();
session_regenerate_id(true);
?>
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

<div class="content" id="myContent">
    <header>
        <h1 id="messenger">Messenger</h1>
    </header>

<?php
    $name = $_POST['name'];
    $chatpartner = $_POST['chatpartner'];
    echo $name;
    echo $chatpartner;


    //https://www.w3schools.com/php/func_string_strcasecmp.asp
	if((strcasecmp($name,$chatpartner))>0){
		$pfad="Chatverlauf/".$name."-".$chatpartner;
		$datei=fopen($pfad, "a");
	}else{
		$pfad="Chatverlauf/".$chatpartner."-".$name;
		$datei=fopen($pfad, "a");
	}
	
	//https://www.w3schools.com/php/php_date.asp
	$date=date("Y-m-d h:i:sa");
	$text=$_POST['textf'];
	chmod($pfad,0767);
	if((isset($_POST["send"])==true) and $text!=""){
		fwrite($datei,"(".$date.") :".$name."_".$text."\n");
	}
	
	echo '<h3>Du chattest jetzt mit '.$chatpartner.'</h3>';
	
	$dir="Chatverlauf";
	//http://php.net/manual/de/function.scandir.php
	//"Gibt bei Erfolg ein Array von Dateinamen zur�ck"
	$arrayDateien= scandir($dir);
	
	//CSS Id's funktionieren nicht wenn iframe mit echo ausgegeben wird?	
	//$datei f�hrt beim iframe zu Fehler(Resource id)?
	echo "<iframe src=".$pfad." width='700px' height='430px' scrolling='yes' name='Chatbox'></iframe>";
	fclose($datei);	
	
	//http://stackoverflow.com/questions/32420145/how-to-refresh-the-current-page
	//header("Refresh: 7");
?>
	

		<form method="POST" action="">
		<br/>
            <?php echo session_id(); ?>
		      <input name="textf" type="text" size="50" maxlength="80">
		      <input name="send" type="Submit" value="Send" >
		      <input name="refresh" type="Submit" value="Refresh" onClick="window.location.reload()">
		</form>
		


</div>
</body>
</html>
