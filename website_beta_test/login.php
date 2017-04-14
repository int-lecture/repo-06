<?php
	
	
	$check = $_POST['Name'];
	if ($check == "Toto" || $check == "Tata" || $check == "Titi") {
		
		
           header("Location: http://localhost/dashboard/website_beta_test/chatten.php"); /* Redirect browser */
           exit();
        
		
	} else {
		
		echo"<html>
		<head><title>Anmeldung NICHT erfolgreich !</title></head>
		<body>
		<br>
		<center> <h3>Anmeldung NICHT erfolgreich ! </h3></center> <br />
		<center> <h3>Sie sind entweder noch nicht registriert oder der Name ist falsch! </h3></center> <br />
		<a href=\"messenger.php\"><center>nochmal probieren?</center></a>
		</body>
		</html>";
	}
?>