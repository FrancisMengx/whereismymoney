<?php
$response = "work";
$conn = mysqli_connect('localhost', 'root', 'Francis3711','MoneyProject') or die("connection error");
$itemname=$_POST['itemName'];
$price=$_POST['price'];
$category=$_POST['category'];
$date=$_POST['date'];
$username = $_POST['userName'];
$username = $conn->real_escape_string($username);
// Make sure the password field is filled
$password = $_POST['password'];
$password = $conn->real_escape_string($password);

$accountName=$_POST['accountName'];
$user_results = mysqli_query($conn, "SELECT IDno FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "')")or die("UID selection failed");
$UID = mysqli_fetch_array($user_results)[0];
$account_results = mysqli_query($conn,"SELECT AccountID FROM Account WHERE IDno = '".$UID."' and AccountName = '".$accountName."'")or die("AID selection error");
$accountID = mysqli_fetch_array($account_results)[0];
mysqli_query($conn,"INSERT INTO Transaction ( Amount, IDno, AccountID, startDate, ItemName) VALUES ('".$price."','".$UID."','".$accountID."','".$date."','".$itemname."')" ) or die("insertion error");
echo json_encode($price);
?>
