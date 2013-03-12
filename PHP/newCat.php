<?php
$response = "nothing";
$conn = mysqli_connect('localhost', 'root', 'Francis3711','MoneyProject') or die("connection error");
$catName=$_POST['catName'];
$balance=$_POST['balance'];
$username = $_POST['userName'];

// Make sure the password field is filled
$password = $_POST['password'];

$user_results = mysqli_query($conn, "SELECT IDno FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "')")or die("selection failed");
$UID = mysqli_fetch_array($user_results)[0];
echo json_encode($UID);
mysqli_query($conn,"INSERT INTO Category (Name, Balance,IDno) VALUES ('".$catName."', '".$balance."', '".$UID."')") or die("insertion error");
?>
