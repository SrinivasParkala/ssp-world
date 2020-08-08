'use strict';

const https = require('https');
const express = require('express');
const bodyParser = require('body-parser');
const httpProxy = require('http-proxy');
const morgan = require('morgan');

/////////////////////////////////// PROXY SERVER ///////////////////////////////
const fs = require('fs');
process.env.NODE_TLS_REJECT_UNAUTHORIZED='0'
	
const options = {
  pfx: fs.readFileSync('/home/lenovo/user/srini/workspace/microservices/property-maintenance-service/src/test/keystore.p12'),
  passphrase: 'welcome2ss'
};

const proxy = httpProxy.createProxyServer({});

const proxyApp = express();
proxyApp.use(bodyParser.json());
proxyApp.use(bodyParser.urlencoded({extended: true}));
proxyApp.use(function(req, res){
    console.log('proxy body:',req.body.username);
    var options = {
    	    host: "127.0.0.1",
    	    path: "/oauth/token",
    	    port: 1025,
    	    method: "POST",
    	    target: "https://127.0.0.1:1025/oauth/token"
    	};
    
    //proxy.web(req, res, options)
    proxy.web(req, res, options, function(err) {
	    console.log('oh nooooo: ' + err.toString());

	    // capture the encoded form data
	    req.on('data', (data) => {
	      console.log(data.toString());
	    });
	    
	    if (!res.headersSent) {
	      res.statusCode = 502;
	      res.end('bad gateway');
	    }
	  });
  });

https.createServer( options, function(req,res)
		{
	proxyApp.handle( req, res );
		} ).listen(3001);

