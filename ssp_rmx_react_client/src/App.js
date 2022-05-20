import React, { Component } from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Login from './components/login.component'
import Predict from './components/predict.component'
import PredictResult from './components/predict-result.component'
import AuthService from "./services/auth.service";

class App extends Component {

 	constructor() {
    	super();
    	this.logOut = this.logOut.bind(this);
 	   	this.state = {
 	   		loginuser: undefined
 	   	};
  	}
  
	componentDidMount() {
	    const user = AuthService.getCurrentUser();
	  
	    if (user) {
	      this.setState({
	      	loginuser: user
	      });
	    }
	}
	
	logOut() {
    	AuthService.logout();
    }
	  
	render()  {
	  //const { loginuser } = this.state;
	
	  return (
	    <Router>
	      <div className="App">
	        <div className="auth-wrapper">
	          <div className="auth-inner">
	             <Switch>
		              <Route exact path={["/", "/sign-in"]} component={Login} />
		              <Route path="/predict" component={Predict} />
		              <Route path="/predictresult" component={PredictResult} />
		         </Switch>
	          </div>
	        </div>
	      </div>
	    </Router>
	  )
	}

}
export default App