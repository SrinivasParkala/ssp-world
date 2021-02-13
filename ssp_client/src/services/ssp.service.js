import axios from 'axios';
import authHeader from './auth-header';
import userService from './user.service';

const API_URL = 'https://localhost:3001';
const SSP_ROOT = 'ssp';
const RESIDENT_SERVICE = 'resident';
const PATH_SEP = '/';
const landId = userService.getUserLangPref();

class SspService {
 
  getAllResidents() {	 
	 const path = PATH_SEP+RESIDENT_SERVICE+PATH_SEP+landId;
	 const options = {
	  method: 'get',
	  url: API_URL+PATH_SEP+SSP_ROOT+path,
	  headers: authHeader(path)
	 };
 
	 return axios(options);
  }
  
  getAllResidentDetais(residentId) {	 
	 const path = PATH_SEP+RESIDENT_SERVICE+PATH_SEP+residentId+PATH_SEP+landId;
	 const options = {
	  method: 'get',
	  url: API_URL+PATH_SEP+SSP_ROOT+path,
	  headers: authHeader(path)
	 };
 
	 return axios(options);
  }
  
  parseResidentDetails(responseData){
	  var respData = responseData;
	  var address = userService.parseAddress(responseData);
	  var contacts = userService.parseContacts(responseData);
	  var properties = userService.parseProperty(responseData);
	  
	  var residentDetails ={
		fname : respData.nameTitleValue +' '+respData.firstName,
		lname : respData.lastName,
		addressLine : address.addressLine,
		  street : address.street,
		  district : address.district,
		  state : address.state,
		  postalCode : address.postalCode,
		
		contacts : contacts,
		residents: properties
	  };
	  return residentDetails;
  }
  
}

export default new SspService();
