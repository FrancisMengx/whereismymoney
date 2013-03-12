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
$user_results = mysqli_query($conn, "SELECT * FROM Users 
WHERE HashCode = saltedHash('" . $username . "', '" . $password . "'))");
if ($user_results) {
        if (mysqli_fetch_array($user_results)) {
                echo json_encode("Username already taken");
        }
    // If no duplicates are found, go ahead and create the new user
    }else {
        mysqli_query($conn,"INSERT INTO Users (HashCode) VALUES (saltedHash('" . $username . "', '" . $password . "'))") or $response = "insertion fail" ;
        // Show a success message
     }echo json_encode($response);
?>
