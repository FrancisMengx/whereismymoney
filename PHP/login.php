<?php
$response = "nothing";
$conn = mysqli_connect('localhost', 'root', 'Francis3711','MoneyProject') or $response = die("connection error");
$response = "connection succeed";
// Make sure the username field is filled
$username = $_POST['username'];
$username = $conn->real_escape_string($username);

// Make sure the password field is filled
$password = $_POST['password'];
$password = $conn->real_escape_string($password);

// First, check for that username already being taken
$user_results = mysqli_query($conn, "SELECT IDno FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "')")or die("selection failed");
if ($user_results) {
        if (mysqli_fetch_array($user_results)) {
                echo json_encode("true");

        }
}
?>
