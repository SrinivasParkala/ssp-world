const https = require('https');
const fs = require('fs');

const options = {
  pfx: fs.readFileSync('/home/lenovo/user/srini/workspace/microservices/property-maintenance-service/src/test/keystore.p12'),
  passphrase: 'welcome2ss'
};

https.createServer(options, (req, res) => {
	console.log("Method:-"+req.method);
	  console.log("Content--Type:"+req.header);
	  console.log("Body:-"+req.data);
  console.log('Started');
  res.writeHead(200);
  res.end('hello world\n');
}).listen(3001);

