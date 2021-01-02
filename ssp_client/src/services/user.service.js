import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'https://localhost:8080/api/test/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'all');
  }

  getUserBoard() {
    return axios.get(API_URL + 'user', { headers: authHeader() });
  }

  getModeratorBoard() {
    return axios.get(API_URL + 'mod', { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get(API_URL + 'admin', { headers: authHeader() });
  }
  
  getUserLangPref(){
	  return 10;
  }
  
  parseAddress(responseData){
	  var add = {addressLine:'',street:'',district:'',state:'',postalCode:''};
	  for (var i = 0; i < responseData.address.length; i++) {
		  add.addressLine = responseData.address[i].addressLine;
		  add.street = responseData.address[i].street;
		  add.district = responseData.address[i].district;
		  add.state = responseData.address[i].state;
		  add.postalCode = responseData.address[i].postalCode;
	  }
	  return add;
  }
  
  parseContacts(responseData){
	  var contacts = [{id:'',type:'',value:''}];
	  for (var i = 0; i < responseData.contacts.length; i++) {
		  var cont = {
				  id:responseData.contacts[i].keyId,
				  type:responseData.contacts[i].contactValue,
				  value:responseData.contacts[i].contactDataValue	  
		  };
		  contacts.push(cont);
		 
	  }
	  return contacts;
  }
  
  parseProperty(responseData){
	  var property = [{id:'',type:'',sqrft:''}];
	  for (var i = 0; i < responseData.resProperty.length; i++) {
		  var prop = {
				  id:responseData.resProperty[i].keyId,
				  type:responseData.resProperty[i].unitValue,
				  sqrft:responseData.resProperty[i].dimension	  
		  };
		  property.push(prop);
	  }
	  return property;
  }
}

export default new UserService();
