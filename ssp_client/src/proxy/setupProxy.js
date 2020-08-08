const querystring = require('querystring');
const httpProxy = require('http-proxy');
const express = require('express');
const logger = require('morgan');
const fs = require('fs');
const bodyParser = require('body-parser');

//process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

const proxy = httpProxy.createProxyServer({
  //ssl: false, 
	target: {
	      host: '127.0.0.1',
	      port: 1025,
	      protocol: 'https:',
	      pfx: fs.readFileSync('/home/lenovo/user/srini/workspace/microservices/property-maintenance-service/src/test/keystore.p12'),
	      passphrase: 'welcome2ss'
	    },
  secure: false,
  changeOrigin: true
});

const app = express();

app.use(logger('dev'));

proxy.on('proxyReq', function(proxyReq, req, res, options) {
	console.log("Overload:"+req.method);
	    let bodyData = querystring.stringify({
			grant_type: 'password',
			username: 'vatika@ten_01',
			password: 'password',
			client_id: 'ssp_domain'
		});
	    proxyReq.write(bodyData);
	    
	});
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use('/oauth/*', (req, res) => {
  
  console.log("Method:"+req.method);
  console.log("Content-Type:"+req.header('Content-Type'));
  console.log("Authorization:"+req.header('Authorization'));
  console.log("Body:"+req.data);
  var options = {
		    target: 'https://127.0.0.1:1025/oauth/token',
		    changeOrigin: true
		  };

  //proxy.web(req, res, options);
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

app.use('/ssp/*', (req, res) => {
	  console.log("Method:-"+req.method);
	  console.log("Content--Type:"+req.header('Content-Type'));
	  console.log("Authorization:-"+req.header('Authorization'));
	  console.log("Body:-"+req.data);
	  
	  proxy.web(req, res, {target: 'https://localhost:1025/ssp/token'});
	});

proxy.on('error', (err, req, res) => {
  console.log('Proxy server error: \n', err);
  res.status(500).json({ message: err.message });
});

app.use((err, req, res, next) => {
  console.log('App server error: \n', err);
  res.status(err.status || 500).json({ message: err.message });
});

app.listen(3001, () => {
  console.log("listening on port " + 3001);
});