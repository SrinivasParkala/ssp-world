'use strict';

const axios = require('axios');
const https = require('https');
const express = require('express');
const bodyParser = require('body-parser');
const httpProxy = require('http-proxy');
const morgan = require('morgan');

/////////////////////////////////// PROXY SERVER ///////////////////////////////
const fs = require('fs');

const httpsAgent = new https.Agent({ 
	 pfx: fs.readFileSync('/home/lenovo/user/srini/workspace/microservices/property-maintenance-service/src/test/keystore.p12'),
	 passphrase: 'welcome2ss'
	});
	
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
	var axiosConfig = {
	  headers: {
		  "Content-Type" : req.header('Content-Type'),
		  "Authorization": req.header('Authorization')
	  },
	  data : req.body,
	  httpsAgent : httpsAgent
	};
    
	console.log('Config:',axiosConfig);
	
    axios.post("https://127.0.0.1:1025/oauth/token", axiosConfig ).then(response => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
   });    
});

https.createServer( options, function(req,res){
	console.log("Proxy Started at 3001")
	proxyApp.handle( req, res );
}).listen(3001);

