<?php
$var1 = $_POST['Vorname'];
$var2 = $_POST['Nachname'];
$var3 = $_POST['Email'];
$fileHandle = fopen("myDataFile" , 'a');
fwrite($fileHandle, $var1);
fwrite($fileHandle, $var2);
fwrite($fileHandle, $var3);
fclose($fileHandle);

$fileHandle = fopen("myDataFile", 'r');
$theInfo = fgets($fileHandle);
$fclose($fileHandle);
echo "erfolgreich registriert";
?>
