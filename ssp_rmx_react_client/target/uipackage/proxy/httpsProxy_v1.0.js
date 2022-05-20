'use strict';

const https = require('https');
const express = require('express');
const bodyParser = require('body-parser');
const httpsAgent = require('https-agent');
const qs = require('qs');
const fs = require('fs');
let cors = require('cors');
require('dotenv').config();

const options = {
  pfx: fs.readFileSync(process.env.KEYSTORE_FILE),
  passphrase: process.env.PASSPHRASE
};

const proxyApp = express();
proxyApp.use(cors())

proxyApp.use(bodyParser.json());
proxyApp.use(bodyParser.urlencoded({extended: true}));

proxyApp.use('/sspService/*', (req, res) => {
	
    var agent = httpsAgent(options);
    
	var options = {
	  hostname: process.env.SSP_SERVICE_HOST,
	  port: process.env.SSP_SERVICE_PORT,
	  path: req.originalUrl,
	  method: req.method,
	  agent: agent,
	  timeout: parseInt(process.env.TIMEOUT_IN_MS),
	  rejectUnauthorized: false,
	  headers: {
		  'Content-Type' : 'application/json',
		  'Authorization': req.header('Authorization')
	  }
	}
	
	var data = qs.stringify(req.body);
	const reqhttp = https.request(options, (reshttp) => {
	  reshttp.on('data', (d) => {
		return res.end(d+"}");
	  });
	})

	reqhttp.on('error', (e) => {
	  	console.log('Errored:');
	  	return res.send(e);
	})

	reqhttp.on('timeout', () => {
		console.log('Timeout:');
		res.status(408);
		return res.status(408).send('Request Timed Out');
	})
	
	reqhttp.write(data);
	reqhttp.end();
});

proxyApp.use('/oauth/*', (req, res) => {
    var agent = httpsAgent(options);
   
	var options = {
	  hostname: process.env.OAUTH_HOST,
	  port: process.env.OAUTH_PORT,
	  path: req.originalUrl,
	  method: req.method,
	  agent: agent,
	  timeout: parseInt(process.env.TIMEOUT_IN_MS),
	  rejectUnauthorized: false,
	  headers: {
		  'Content-Type' : req.header('Content-Type'),
		  'Authorization': req.header('Authorization')
	  }
	}
	
	var data = qs.stringify(req.body);
	
	const reqhttp = https.request(options, (reshttp) => {
	  reshttp.on('data', (d) => {
		return res.end(d+'}');
	  });
	})

	reqhttp.on('error', (e) => {
	  	console.log('Errored:');
	  	return res.send(e);
	})

	reqhttp.on('timeout', () => {
		console.log('Timeout:');
		return res.status(408).send('Request Timed Out');
	})
	
	reqhttp.write(data);
	reqhttp.end();
});

https.createServer( options, function(req,res){
	proxyApp.handle( req, res );
}).listen(process.env.PROXY_PORT);
