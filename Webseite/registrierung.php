
<html>

<body>
<?php


	if(isset($_POST['Submit'])){
		
	    if ($_POST['check']) {
			
			$var1 = $_POST['Vorname'];
            $var2 = $_POST['Nachname'];
            $var3 = $_POST['Email'];
		


            if($var1=="" or $var2=="" or $var3==""){
			
			
					print "<br><br><br><br><center><h2>Leider haben Sie entweder ihren Namen oder ihre E-mail Adresse vergessen !</h2></center>";
		
		    }else{
                    $$fileHandle  = fopen("myDataFile.txt", 'a'); //a = append //w=write
			
						fwrite($fileHandle, "[ Vornme:" . $var1 . " " . "Nachme:" . $var2 . "E-mail:" . $var3 . "] ");
			
						fclose($fileHandle);
						
                        echo "<br><br><br><br><center><h2> Registrierung erfolgreich!</h2></center>";
		
			}
		
	    }else {
		
		      echo '<br><br><br><br><center><h2>Sie müssen erstmal die Datenschutzbedingungen akzeptieren!</h2></center>';
	    }
	}
?>
</body>
</html>