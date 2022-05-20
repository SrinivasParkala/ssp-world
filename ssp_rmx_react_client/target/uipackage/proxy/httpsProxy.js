'use strict';

const https = require('https');
const express = require('express');
const bodyParser = require('body-parser');
const httpsAgent = require('https-agent');
const qs = require('qs');
const fs = require('fs');
let cors = require('cors')


const options = {
  pfx: fs.readFileSync('/Users/srinivasaparkala/workspace/publisher-x/ssp_rmx_react_client/ssl_cert/keystore.p12'),
  passphrase: 'welcome2ss'
};

const proxyApp = express();
proxyApp.use(cors())

proxyApp.use(bodyParser.json());
proxyApp.use(bodyParser.urlencoded({extended: true}));

proxyApp.use('/sspService/*', (req, res) => {
	
    var agent = httpsAgent(options);
    console.log('/sspService'+req.originalUrl);
    
	var options = {
	  hostname: '9.30.206.99',
	  port: 30006,
	  path: req.originalUrl,
	  method: req.method,
	  agent: agent,
	  timeout: 5000,
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
	  hostname: '9.30.206.99',
	  port: 30005,
	  path: req.originalUrl,
	  method: req.method,
	  agent: agent,
	  timeout: 5000,
	  rejectUnauthorized: false,
	  headers: {
		  'Content-Type' : req.header('Content-Type'),
		  'Authorization': req.header('Authorization')
	  }
	}
	
	var data = qs.stringify(req.body);
	console.log("Request:"+data);
	
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
}).listen(3001);
