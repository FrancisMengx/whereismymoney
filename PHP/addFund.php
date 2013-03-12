<?php
$response = "nothing";
$conn = mysqli_connect('localhost', 'root', 'Francis3711','MoneyProject') or die("connection error");
$accountName=$_POST['accountName'];
$accountName= $conn->real_escape_string($accountName);
$fund=$_POST['fund'];
$username = $_POST['userName'];
$username = $conn->real_escape_string($username);

// Make sure the password field is filled
$password = $_POST['password'];
$password = $conn->real_escape_string($password);

$user_results = mysqli_query($conn, "SELECT IDno FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "')")or die("selection failed");
$UID = mysqli_fetch_array($user_results)[0];
echo json_encode($accountName);
mysqli_query($conn,"UPDATE Account SET Amount = Amount + '".$fund."' WHERE IDno = '".$UID."' and AccountName = '".$accountName."'") or  die("insertion error");
?>
