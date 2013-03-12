<?php
$response = "nothing";
$conn = mysqli_connect('localhost', 'root', 'Francis3711','MoneyProject') or die("connection error");
$username = $_POST['userName'];
$username = $conn->real_escape_string($username);

// Make sure the password field is filled
$password = $_POST['password'];
$password = $conn->real_escape_string($password);

$user_results = mysqli_query($conn, "SELECT IDno FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "')")or die("selection failed");
$UID = mysqli_fetch_array($user_results)[0];
$Accounts = mysqli_query($conn, "SELECT AccountName FROM Account WHERE IDno = '".$UID."'") or die("insertion error");
while($AccountList = mysqli_fetch_array($Accounts)){

echo json_encode($AccountList['AccountName']);
}
?>
