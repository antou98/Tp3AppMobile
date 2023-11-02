var express = require('express');
var app = express();
const bodyParser = require('body-parser');
const fs = require('fs');
//app.use(express.json());
app.use(bodyParser.json());
var mysql =require('mysql');

var connPool = mysql.createPool({
    connectionLimit: 15,
    database: 'defaultdb',
    host: "mysql-1ae6b180-antou98-d35e.aivencloud.com",
    user: "user-formatif",
    password: "AVNS_HOMMmMbEe4mV3SMTm_n",
    port : "20509"
});

app.get("/api",(req,res)=>{
    console.log("connection");
    res.send({"val":"succes"}).end();
})

app.get("/api/connect/:mail/:password", (req, res) => {
    connPool.getConnection((err, connection) => {
        if (err) {
            res.status(500).json({ error: "Database connection error" });
            return;
        }

        let query = "select * from FROM defaultdb.user u WHERE u.email = ? AND u.password = ?";
        connPool.query(query,[req.params.mail, req.params.password],(err, rows, fields)=>{
            if (err){
                res.status(500).json({ error: "Database query error" });
            }

            if(rows!==undefined){
                res.json({ message: "Connected to the database" });
            }
            else{
                res.json({ message: "no data " });
            }
            
        });
        connection.release();
    });
});

app.listen(5000,()=>{
    console.log("listening on port 5000");
})

