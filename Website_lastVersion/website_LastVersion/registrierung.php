
<html>

<body>
<?php


	if(isset($_POST['Submit'])){
		
	    if ($_POST['check']) {
			
			$var1 = $_POST['Vorname'];
            $var2 = $_POST['Nachname'];
            $var3 = $_POST['Email'];
		


            if($var1=="" or $var2=="" or $var3==""){
			
			
					print "Geben Sie ihren Namen und ihre E-mail Adresse ein.";
		
		    }else{
                    $$fileHandle  = fopen('myDataFile.txt', 'a'); //a = append //w=write
			
						fwrite($fileHandle, "[ Vornme:" . $var1 . " " . "Nachme:" . $var2 . "E-mail:" . $var3 . "] ");
			
						fclose($fileHandle);
						
                        echo "Herzlichen Glückwunsch! Die Anmeldung zu unserem Newsletter war erfolgreich!";
		
			}
		
	    }else {
		
		      echo 'Sie müssen die Datenschutzbedingungen akzeptieren, um sich registrieren zu können.';
	    }
	}
?>
</body>
</html>