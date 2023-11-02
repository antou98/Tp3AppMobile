<?php
// Define your MySQL connection string
$dsn = "mysql:host=mysql-1ae6b180-antou98-d35e.aivencloud.com;port=20509;dbname=defaultdb;sslmode=REQUIRED";
$username = "avnadmin";
$password = "AVNS_bEHYz9ilE-mdt57XyrY";

try {
    // Create a PDO instance
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Error: " . $e->getMessage());
}

// Create user
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = json_decode(file_get_contents("php://input"), true);
    $sql = "INSERT INTO user (LastName, FirstName, email, password) VALUES (:lastName, :firstName, :email, :password)";
    $stmt = $pdo->prepare($sql);
    $stmt->execute($data);
    echo "User created successfully";
}

// Retrieve user 
if ($_SERVER['REQUEST_METHOD'] === 'GET') {
    if (isset($_GET['id'])) {
        $id = $_GET['id'];
        $sql = "SELECT * FROM user WHERE id = :id";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':id', $id, PDO::PARAM_INT);
        $stmt->execute();
        $user = $stmt->fetch(PDO::FETCH_ASSOC);
        echo json_encode($user);
    } else {
        $sql = "SELECT * FROM user";
        $stmt = $pdo->query($sql);
        $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
        echo json_encode($users);
    }
}

// Update user 
if ($_SERVER['REQUEST_METHOD'] === 'PUT') {
    $data = json_decode(file_get_contents("php://input"), true);
    $id = $data['id'];
    $sql = "UPDATE user SET LastName = :lastName, FirstName = :firstName, email = :email, password = :password WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->execute($data);
    echo "User updated successfully";
}

// Delete a user
if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
    $id = $_GET['id'];
    $sql = "DELETE FROM user WHERE id = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id, PDO::PARAM_INT);
    $stmt->execute();
    echo "User deleted successfully";
}
?>