import React, { Component } from 'react'
import { Link } from "react-router-dom";
import AuthService from "../services/auth.service";

export default class SignUp extends Component {

  constructor() {
    	super();
    	this.logOut = this.logOut.bind(this);
    	this.showResults = this.showResults.bind(this);
    	
    	this.onChangeCement = this.onChangeCement.bind(this);
    	this.onChangeGgbs = this.onChangeGgbs.bind(this);
    	this.onChangeAggregate = this.onChangeAggregate.bind(this);
    	this.onChangeWater = this.onChangeWater.bind(this);
    	
    	this.state = {
	      cement: "",
	      ggbs: "",
	      aggregate: "",
	      water: false,
	      data: []
	    };
 	 
  }
  
  onChangeCement(e) {
	    this.setState({
	      cement: e.target.value
	    });
	}
	
	onChangeGgbs(e) {
	    this.setState({
	      ggbs: e.target.value
	    });
	}
	
	onChangeAggregate(e) {
	    this.setState({
	    	aggregate: e.target.value
	    });
	}
  
  	onChangeWater(e) {
	    this.setState({
	    	water: e.target.value
	    });
	}
	
  logOut() {
    	AuthService.logout();
  }
  
  showResults(){
  	this.setState({
  	  data: [ {'cement': this.state.cement,'ggbs':this.state.ggbs,'aggregate':this.state.aggregate,'water':this.state.water,'predictedvalue':'56.86'} ]
  	});
  	this.props.history.push("/predictresult");
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
	              </ul>
	            </div>
	          </div>
	        </nav>
	      </div>
	      <div>
		      <form onSubmit={this.showResults}
	            ref={c => {
	              this.form = c;
	            }}>
		        <h3>Predict</h3>
		        <div className="mb-3">
		          <label>Cement</label>
		          <input
		            type="text"
		            className="form-control"
		            placeholder="Cement"
		            value={this.state.cement}
	            	onChange={this.onChangeCement}
		          />
		        </div>
		        <div className="mb-3">
		          <label>GGBS</label>
		          <input type="text" 
		          	className="form-control" 
		          	placeholder="GGBC" 
		          	value={this.state.ggbs}
	            	onChange={this.onChangeGgbs} />
		        </div>
		        <div className="mb-3">
		          <label>Aggregate</label>
		          <input
		            type="text"
		            className="form-control"
		            placeholder="Aggregate"
	            	value={this.state.aggregate}
	            	onChange={this.onChangeAggregate}
		          />
		        </div>
		        <div className="mb-3">
		          <label>Water</label>
		          <input
		            type="text"
		            className="form-control"
		            placeholder="Water"
		            value={this.state.water}
	            	onChange={this.onChangeWater}
		          />
		        </div>
		        <div className="d-grid">
		          <button type="submit" className="btn btn-primary">
		            Predict
		          </button>
		        </div>
		        <p className="forgot-password text-right">
		          Already registered <a href="/sign-in">sign in?</a>
		        </p>
		      </form>
	      </div>
      </div>
    )
  }
}