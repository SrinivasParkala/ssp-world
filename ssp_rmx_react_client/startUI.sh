#kill -9 $(ps aux | grep httpsProxy | awk '{print $2}')
#kill -9 $(ps aux | grep startup | awk '{print $2}')
node ./proxy/httpsProxy_v1.0.js &
node ./ui-project/startup.js 
