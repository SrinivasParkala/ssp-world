const express = require('express');
const path = require('path');
const https = require('https');
const http = require('http');
const fs = require('fs');
require('dotenv').config();

const app = express();

const options = {
  pfx: fs.readFileSync(process.env.KEYSTORE_FILE),
  passphrase: process.env.PASSPHRASE
};

app.use(express.static(path.join(__dirname, 'build')));

app.get('/*', function (req, res) {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

http.createServer(app).listen(9000);
https.createServer(options, app).listen(9001);
