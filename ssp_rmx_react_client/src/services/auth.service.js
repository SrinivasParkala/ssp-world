import axios from "axios";

//cd ssp_rmx_react_client/src/proxy
//node httpsProxy.js

//curl --location --request POST https://localhost:3001/oauth/token --header 'Authorization: Basic c3NwX2RvbWFpbjpwYXNzd29yZA==' --header 'Content-Type: application/x-www-form-urlencoded' -d 'grant_type=password' -d 'username=system_sshettigar75@gmail' -d 'password=password' -k
//curl --location --request GET https://localhost:3001/sspService/rmxApis/v1.0/getOAuthDetails -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTE4Mjk5MDgsInVzZXJfbmFtZSI6InN5c3RlbV9zc2hldHRpZ2FyNzVAZ21haWwiLCJhdXRob3JpdGllcyI6WyJzdXBlcnVzZXIiXSwianRpIjoiNTQyNTJkYWUtNmYzZi00ZmFkLWJkOTEtODE1Zjc5MGE5MjE3IiwiY2xpZW50X2lkIjoic3NwX2RvbWFpbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.IQDa2ilGlFnvvtDxUBxzMvTy-TqG1Yv9RWaybpTLoNz-UIx7KGUldBPbBDAAZNYwEr_VNBAB_7IuN0ejIXJ27qhIbjsM7P8cGqU9v80t4RG3w3wlO9-5U6UATMB5bQP1dFaJ8CmmLo1vcxjIsplYxXGfaHJ5PAfS3fk1q3tQYsym1XwhyP97UzMrcIog8TPFFY9wox9ID5DNuelhhOvr7eneoHe4Zb2hZxB44Z61w4qxRJE_X9rokGO9YwjtIUxem7wI_zbfySwDIonbOtEPFTZskt5Po5UKSr8QZp9SbmGMELrLUZKV5RPe5j3sZefPcJFiR4DyFtmbjfOSAOmQ6Q' -k 

class AuthService {
	
  login(username, password, tenant) {
		
	console.log("Config Data:"+process.env.REACT_APP_OAUTH_URL);
	console.log("Tenant:"+tenant);
	console.log("User:"+username);
	console.log("Password:"+password);
	
	var  axiosConfig = {
	  headers: {
	      "Access-Control-Allow-Origin": "*",
		  "Content-Type": "application/x-www-form-urlencoded"
	  },
	  auth: {
	      username: process.env.REACT_APP_USER,
	      password: process.env.REACT_APP_OAUTH_PASSWORD
	  }
	};
	
	var formData = new URLSearchParams();
	  formData.append('grant_type', process.env.REACT_APP_GRANT_TYPE);
	  formData.append('username', tenant+"_"+username);
	  formData.append('password', password);
	
	console.log("formData:"+formData);
	
    return axios
      .post(process.env.REACT_APP_OAUTH_URL, formData ,axiosConfig )
      .then(response => {
        if (response.data) {
          console.log("User Stored:"+response.data);
          localStorage.setItem('user', JSON.stringify(response.data+'}'));
        }

        return JSON.stringify(response.data);
      });
  }

  logout() {
    console.log("Remove user from store");
    localStorage.removeItem("user");
  }

  getCurrentUser() { 
    console.log("User:"+localStorage.getItem('user'));
	var authObj = JSON.parse(localStorage.getItem('user'));
    return authObj;
  }
}

export default new AuthService();