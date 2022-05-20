import React, { Component } from 'react'
import AuthService from "../services/auth.service";

import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";

const required = value => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

export default class Login extends Component {

	constructor(props) {
	    super(props);
	      
	    this.handleLogin = this.handleLogin.bind(this);
	    this.onChangeUsername = this.onChangeUsername.bind(this);
	    this.onChangePassword = this.onChangePassword.bind(this);
	    this.onChangeTenant = this.onChangeTenant.bind(this);
	
	    this.state = {
	      username: "",
	      password: "",
	      tenant: "",
	      loading: false,
	      message: ""
	    };
	}
	
	onChangeUsername(e) {
	    this.setState({
	      username: e.target.value
	    });
	}
	
	onChangePassword(e) {
	    this.setState({
	      password: e.target.value
	    });
	}
	
	onChangeTenant(e) {
	    this.setState({
	    	tenant: e.target.value
	    });
	}
  
	handleLogin(e) {
	    e.preventDefault();
	    this.setState({
	      message: "",
	      loading: true
	    });
	
	    this.form.validateAll();
		    
      	if (this.checkBtn.context._errors.length === 0) {
	      AuthService.login(this.state.username, this.state.password,this.state.tenant ).then(
	        () => {
	          this.props.history.push("/predict");
	          window.location.reload();
	        },
	        error => {
	          const resMessage =
	            (error.response &&
	              error.response.data &&
	              error.response.data.message) ||
	            error.message ||
	            error.toString();
	
	          this.setState({
	            loading: false,
	            message: resMessage
	          });
	        }
	      );
	    } else {
	      this.setState({
	        loading: false
	      });
	    }
	    
	}
  
	render() {
	    return (
	    	
	      <Form
	      	onSubmit={this.handleLogin}
            ref={c => {
              this.form = c;
            }}
	      >
	        <h3>Sign In</h3>
	        <div className="mb-3">
	          <label>Tenant Id</label>
	          <input
	            type="text"
	            className="form-control"
	            placeholder="Enter tenant id"
	            name="tenant"
	            value={this.state.tenant}
	            onChange={this.onChangeTenant}
	            validations={[required]}
	          />
	        </div>
	        <div className="mb-3">
	          <label>Email address</label>
	          <input
	            type="email"
	            className="form-control"
	            placeholder="Enter email"
	            name="username"
                value={this.state.username}
                onChange={this.onChangeUsername}
                validations={[required]}
	          />
	        </div>
	        <div className="mb-3">
	          <label>Password</label>
	          <input
	            type="password"
	            className="form-control"
	            placeholder="Enter password"
	            name="password"
                value={this.state.password}
                onChange={this.onChangePassword}
                validations={[required]}
	          />
	        </div>
	        <div className="mb-3">
	          <div className="custom-control custom-checkbox">
	            <input
	              type="checkbox"
	              className="custom-control-input"
	              id="customCheck1"
	            />
	            <label className="custom-control-label" htmlFor="customCheck1">
	              Remember me
	            </label>
	          </div>
	        </div>
	        <div className="d-grid">
	          <button className="btn btn-primary btn-block"
                disabled={this.state.loading} >
	             {this.state.loading && (
                  <span className="spinner-border spinner-border-sm"></span>
                )}
                <span>Login</span>
	          </button>
	        </div>
	        
	        <p className="forgot-password text-right">
	          Forgot <a href="/predict">password?</a>
	        </p>
	        
	        {this.state.message && (
              <div className="form-group">
                <div className="alert alert-danger" role="alert">
                  {this.state.message}
                </div>
              </div>
            )}
	        
	        <CheckButton
              style={{ display: "none" }}
              ref={c => {
                this.checkBtn = c;
              }}
            />
	      </Form>
	     
	    )
	}
}
