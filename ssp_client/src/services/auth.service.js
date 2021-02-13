import axios from "axios";

const API_URL = "https://localhost:3001/oauth/";

class AuthService {
	
  login(username, password, tenant) {
	
	var  axiosConfig = {
			  headers: {
				  "Content-Type": "application/x-www-form-urlencoded"
			  },
			  auth: {
			      username: tenant,
			      password: password
			  }
			};

	var formData = new URLSearchParams();
	  formData.append('grant_type', 'password');
	  formData.append('username', username);
	  formData.append('password', password);
	
	console.log("config:"+axiosConfig);
	console.log("formData:"+formData);
	
    return axios
      .post(API_URL + "token", formData ,axiosConfig )
      .then(response => {
        if (response.data) {
          localStorage.setItem('user', response.data+'}');
        }

        return JSON.stringify(response.data);
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username, email, password) {
    return axios.post(API_URL + "signup", {
      username,
      email,
      password
    });
  }

  getCurrentUser() { 
	var authObj = JSON.parse(localStorage.getItem('user'));
    return authObj;
  }
}

export default new AuthService();
