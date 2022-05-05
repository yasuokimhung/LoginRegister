<?php 
 
 /*
 * Created by Belal Khan
 * website: www.simplifiedcoding.net 
 * Retrieve Data From MySQL Database in Android
 */
 
 //database constants
 define('DB_HOST', 'localhost');
 define('DB_USER', 'root');
 define('DB_PASS', '');
 define('DB_NAME', 'loginregister');
 
 //connecting to database and getting the connection object
 $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
 
 //Checking if any error occured while connecting
 if (mysqli_connect_errno()) {
 echo "Failed to connect to MySQL: " . mysqli_connect_error();
 die();
 }
    
    // $username = $_POST['username'];
    //creating a query
    $stmt = $conn->prepare("SELECT id, fullname, username, "."password".", email FROM users WHERE username = '".$_POST['username']."';");
    if(!$stmt){
       echo "Prepare failed: (". $conn->errno.") ".$conn->error."<br>";
    }

    //executing the query 
    $stmt->execute();

    //binding results to the query 
    $stmt->bind_result($id, $fullname, $username, $password, $email);

    $products = array(); 

    //traversing through all the result 
    while($stmt->fetch()){
    $temp = array();
    $temp['id'] = $id; 
    $temp['fullname'] = $fullname; 
    $temp['username'] = $username; 
    $temp['password'] = $password; 
    $temp['email'] = $email; 
    
    array_push($products, $temp);
    }

    //displaying the result in json format 
    echo json_encode($products);
 