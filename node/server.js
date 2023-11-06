const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');
const app = express();
const port = process.env.PORT || 5000;

app.use(bodyParser.json());

var connPool = mysql.createPool({
    connectionLimit: 15,
    database: 'defaultdb',
    host: "mysql-1ae6b180-antou98-d35e.aivencloud.com",
    user: "user-formatif",
    password: "AVNS_HOMMmMbEe4mV3SMTm_n",
    port : "20509"
});

// trouve un joueur avec son email et son password 
app.get('/api/joueurs', (req, res) => {
    const { email, password } = req.query;
  
    const sql = 'SELECT * FROM Joueur WHERE email = ? AND password = ?';
    connPool.query(sql, [email, password], (err, rows) => {
      if (err) {
        console.error('Error recherche Joueur:', err);
        res.status(500).json({ error: 'Server error' });
      } else if (rows.length > 0) {
        console.error("Succes retour du joueur");
        res.status(200).json(rows[0]);
      } else {
        res.status(404).json({ error: 'Pas un compte' });
      }
    });
  });

// creation d'un nouveau Joueur
app.post('/api/joueurs', (req, res) => {
    const { nom, prenom, email, password, pays } = req.body;
  
    const sql = 'INSERT INTO Joueur (nom, prenom, email, password, pays) VALUES (?, ?, ?, ?, ?)';
    connPool.query(sql, [nom, prenom, email, password, pays], (err, result) => {
      if (err) {
        console.error('Error création Joueur:', err);
        res.status(404).json({ error: 'Peut pas créé Joueur' });
      } else {
        console.error('Création Joueur succes');
        res.status(201).json({ message: 'Joueur créé succes' });
      }
    });
  });

  app.get("/api",(req,res)=>{
    res.status(201).send("succes")
  })

app.listen(port, () => {
    console.log(`Serveur écoute sur le port : ${port}`);
});

