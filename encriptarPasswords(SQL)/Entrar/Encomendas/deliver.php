<?php
session_start();

$host = "db.ist.utl.pt";
include('../../.config.php');
$user = $CONFIG['istid'];
$password = $CONFIG['pass'];
$dbname = $user;

$db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$delivers = $_REQUEST['delivers'];
$address = $_REQUEST['address'];
$nif = $_REQUEST['nif'];
$email=$_SESSION['email'];

echo "<p>Email: " . $nif . "</p>";

	/*loop print plants*/
	/*$sql=$db->prepare("SELECT serial_number, name, image, batery, water FROM plant natural join own WHERE email=?");
	$sql->execute(array($_SESSION['email']));
	$nrows = $sql->rowCount();
	if($nrows > 0 ) {
		while($r=$sql->fetch()){
			$serial_number=$r['serial_number'];
			$name=$r['name'];
			$image=$r['image'];
			$batery=$r['batery'];
			$water=$r['water'];

			echo "<div class='col-sm-3'>";
	  echo "<center><strong>";
		    echo "<p>N&uacute;mero de S&eacute;rie: " . $serial_number . "</p>";
		    echo "<p>Nome: " . $name . "</p>";
		    echo "<img src='../../imagens/" . $image . "' class='img-responsive' style='width: 200px; height: 200px;' alt='Image'>";
		   	echo "<p>Bateria: " . $batery . "</p>";
		   	echo "<p>&Aacute;gua: " . $water . "</p>";
	    echo "</strong></center>";
			echo "</div>";
		}
	}else {
		echo "<br><br>";
		echo "<center><strong>";
		echo "<h2>Ainda n&atilde;o tens plantas no nosso sistema</h2>";
		echo "</strong></center>";
	}*/
?>