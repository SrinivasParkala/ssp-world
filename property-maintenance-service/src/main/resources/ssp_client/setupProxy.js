const httpProxy = require('http-proxy');
const express = require('express');
const logger = require('morgan');

const proxy = httpProxy.createProxyServer({
  // ssl: true,
  secure: false
});

const app = express();

app.use(logger('dev'));

app.all('/oauth/*', (req, res) => {
  const options = {
    target: 'https://localhost:1025/oauth'
  };
  proxy.web(req, res, options);
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