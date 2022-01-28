const express = require("express");
const mssql = require("mysql");
var app = express();
app.use(express.static("public"));

const config = {
    user: 'SA',
    password: 'Your_Password',
    server: 'titan.csse.rose-hulman.edu',
    database: 'MovieTheaterReservation'
};


mssql.createConnection(config, function (err) {
 
    // Create Request object to perform
    // query operation
    var request = new mssql.Request();

    // Query to the database and get the records
    request.query('select * from student',
        function (err, records) {

            if (err) console.log(err)

            // Send records as a response
            // to browser
            res.send(records);

        });
});



app.get("/api/getreservations/", (req, res) => {
    const boardString = req.params.board;
    const openLocs = getOpenLocations(boardString);
    const moveSelected = openLocs[Math.floor(Math.random() * openLocs.length)];

    res.send({"move":moveSelected});
});

app.get("/api/getemployeedata/", (req, res) => {
    console.log("implement get employee data");
});

app.get("/api/getmovietheaters/", (req, res) => {
    console.log("print names of theaters here");
    res.send("get movies");
});

app.get("/api/getmovieschedulefortheater/", (req, res) => {
    console.log("get movie schedule for chosen theater");
});

app.get("/api/loadmovie/", (req, res) => {
    console.log("print movie details, show seat arrangements");
});

app.get("/api/reservemovie/", (req, res) => {
    console.log("reserve movie here, with chosen seat, movie and time");
});

app.listen(3000);