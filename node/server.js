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


/*app.post("/connect/:mail/:password",(req,res)=>{

})*/




app.get("/api",(req,res)=>{
    console.log("connection");
    res.send({"val":"succes"}).end();
})

app.listen(5000,()=>{
    console.log("listening on port 5000");
})

