import React, { Component } from 'react'
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import BootstrapTable from "react-bootstrap-table-next";

export default class SignUp extends Component {

  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);
 	 
 	this.state = {
      data: [ {'cement':'55','ggbs':'45','aggregate':'5mm','water':'7.6','predictedvalue':'56.86'},{'cement':'45','ggbs':'45','aggregate':'78mm','water':'7.6','predictedvalue':'76.86'}],
      columns: [
	    {
	      dataField: "cement",
	      text: "Cement"
	    },
	    {
	      dataField: "ggbs",
	      text: "Ggbs"
	    },
	    {
	      dataField: "aggregate",
	      text: "Aggregate"
	    },
	    {
	      dataField: "water",
	      text: "Water"
	    },
	    {
	      dataField: "predictedvalue",
	      text: "PredictedValue"
	    }
	  ]
    };
  }
  
  logOut() {
    	AuthService.logout();
  }
  	
  render() {
    return (
      <div>
	      <div>
	        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
	          <div className="container">
	            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
	              <ul className="navbar-nav ml-auto">
		               <li className="nav-item">
		                  <Link className="nav-link" to={'/sign-in'}>
		                    <span onClick={this.logOut}>Logout</span>
		                  </Link>
	                	</li>
	                	<li className="nav-item">
		                  <Link className="nav-link" to={'/predict'}>
		                    Home
		                  </Link>
	                	</li>
	              </ul>
	            </div>
	          </div>
	        </nav>
	      </div>
	      <div>
	        <h3>Predict Results</h3>
	        <div style={{ padding: "2px" }}>
		      <BootstrapTable keyField="cement" data={this.state.data} columns={this.state.columns} />
		    </div>
	      </div>
      </div>
    )
  }
}