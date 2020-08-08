'use strict';

const https = require('https');
const express = require('express');
const bodyParser = require('body-parser');
const httpsAgent = require('https-agent');
const qs = require('qs');
const fs = require('fs');
let cors = require('cors')


const options = {
  pfx: fs.readFileSync('/home/lenovo/user/srini/workspace/microservices/property-maintenance-service/src/test/keystore.p12'),
  passphrase: 'welcome2ss'
};

const proxyApp = express();
proxyApp.use(cors())

proxyApp.use(bodyParser.json());
proxyApp.use(bodyParser.urlencoded({extended: true}));

proxyApp.use('/oauth/*', (req, res) => {
    var agent = httpsAgent(options);
    
	var options = {
	  hostname: 'localhost',
	  port: 1025,
	  path: '/oauth/token',
	  method: 'POST',
	  agent: agent,
	  rejectUnauthorized: false,
	  headers: {
		  'Content-Type' : req.header('Content-Type'),
		  'Authorization': req.header('Authorization')
	  }
	}
	
	const data = qs.stringify(req.body);
	const reqhttp = https.request(options, (reshttp) => {
	  reshttp.on('data', (d) => {
		res.writeHead(reshttp.statusCode, reshttp.headers);	
		reshttp.pipe(res);
		res.end(d);
	  });
	})

	reqhttp.on('error', (error) => {
	  res.writeHead(500,'Post call to oauth/token failed');
	  res.end(qs.stringify(error));
	})

	reqhttp.write(data);
	reqhttp.end();
});

https.createServer( options, function(req,res){
	proxyApp.handle( req, res );
}).listen(3001);

