<?php
	$var1 = $_POST['Vorname'];
$var2 = $_POST['Nachname'];
$var3 = $_POST['Email'];

	$check = $_POST['check'];
	if ($check) {
		


        $fileHandle = fopen("myDataFile.txt" , 'a');


        fwrite($fileHandle, $var1);
        fwrite($fileHandle, $var2);
        fwrite($fileHandle, $var3);
        fclose($fileHandle);


	
		$link = "index.html";
		echo "<html >
		<head><title><center>Registrierung erfolgreich</center></title></head>
		<body>
		<center><h3>Ihre Registrierung war Erfolgreich! </h3></center><br />
	

		<center>Mit Ihrem Nachnamen koennen Sie sich jetzt bei Login anmelden! </center><br />
		<a href=$link><center>Zur Hauptseite </center></a> <br />
		<a href=\"registrieren.html\"><center>Link zum Login</center></a>
		</body>
		</html>";
	} else {
		$link = "registrieren.html";
		echo"<html>
		<head><title><center>Registrierung NICHT Erfolgreich</center></title></head>
		<body>
		<center> <h3>registrierung fehlgeschlagen! </h3></center> <br />
		
		<a href=$link> <center>nochmal probieren</center></a>
		</body>
		</html>";
	}
?>