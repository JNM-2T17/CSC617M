<?php 
	$f = fopen("Gradebook.txt","w");
	fwrite($f,$_POST['gb']);
	fclose($f);
?>