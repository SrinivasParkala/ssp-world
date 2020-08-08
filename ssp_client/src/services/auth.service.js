import axios from "axios";

const API_URL = "https://localhost:3001/oauth/";

const axiosConfig = {
		  headers: {
			  "Content-Type": "application/x-www-form-urlencoded"
		  },
		  auth: {
		      username: "ssp_domain",
		      password: "password"
		  }
		};

class AuthService {
  login(username, password) {
	
	var formData = new URLSearchParams();
	  formData.append('grant_type', 'password');
	  formData.append('username', 'vatika@ten_01');
	  formData.append('password', 'password');
	  
    return axios
      .post(API_URL + "token", formData ,axiosConfig )
      .then(response => {
        if (response.data) {
          console.log('user added to storage',response.data);
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
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
    return JSON.parse(localStorage.getItem('user'));
  }
}

export default new AuthService();
