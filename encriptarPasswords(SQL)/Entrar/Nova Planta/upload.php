<?php
session_start();

include('../../../.config.php');
$host = "db.ist.utl.pt";
$user = $CONFIG['istid'];
$password = $CONFIG['pass'];
$dbname = $user;
$db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

echo "<p>Entrei aqui</p>";

$target_dir = "../../imagens/";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
$picture = "../../imagens/plantinha.png";

echo "<p>Entrei aqui</p>";

// Check if image file is a actual image or fake image
if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check !== false) {
        echo "File is an image - " . $check["mime"] . ".";
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
}
// Check if file already exists
while (file_exists($target_file)) {
    list($old_name, $extension) = split('[.]', basename($_FILES["fileToUpload"]["name"]);
    $new_name = rand() . $extension;
    $target_file = $target_dir . $new_name;
    $uploadOk = 1;
}
// Check file size
if ($_FILES["fileToUpload"]["size"] > 3000000) {
    echo "Sorry, your file is too large.";
    $uploadOk = 0;
}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
    echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
        echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";

        $serial_number = $_REQUEST['serial_number'];
        $name = $_REQUEST['name'];

        $sql=$db->prepare("INSERT INTO plant (serial_number, name, image) VALUES (?, ?, ?);");
        $sql->execute(array($serial_number, $name, $picture));

        $sql=$db->prepare("INSERT INTO own (email, serial_number) VALUES (?, ?);");
        $sql->execute(array($_SESSION['email']), $serial_number);
        header("Location:../As%20Minhas%20Plantas");


    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}
?>
