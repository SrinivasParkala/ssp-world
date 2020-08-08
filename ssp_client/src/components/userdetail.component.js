import React, { Component } from "react";
import Form from "react-bootstrap/Form";
import Col from "react-bootstrap/Col";
import InputGroup from "react-bootstrap/InputGroup";
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import BootstrapTable from 'react-bootstrap-table-next';
import cellEditFactory, { Type } from 'react-bootstrap-table2-editor';
import Accordion from 'react-bootstrap/Accordion';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';

export default class UserDetail extends Component {

	constructor(props) {
	    super(props);
	    this.onChangeButton = this.onChangeButton.bind(this);
	    this.onTextChange = this.onTextChange.bind(this);
	    
	    this.state = {
	      edit: 2,
	      disabled : true,
	      contacts: [ {'id':'0','type':'E-mail','value':'sshettigar@gmail.com'},{'id':'1','type':'Mobile','value':'9945440831'}],
	      selectRow: {
	    		  mode: 'radio',
	    		  clickToSelect: true,
	    		  clickToEdit: true
	    		},
	      contactheader: [{
	    	  dataField: 'id',
	    	  text: 'No'
	    	}, {
	    	  dataField: 'type',
	    	  text: 'Contact Type',
	    	  editor: {
	    		    type: Type.SELECT,
	    		    options: [{
	    		      value: 'E-mail',
	    		      label: 'E-mail'
	    		    }, {
	    		      value: 'Mobile',
	    		      label: 'Mobile'
	    		    }]}
	    	}, {
	    	  dataField: 'value',
	    	  text: 'Contact Value',
	    	  sort: true
	    	}],
	      sites: [ {'id':'520','type':'30*40','sqrft':'2400'},{'id':'521','type':'60*40','sqrft':'2400'}],
		      
		      siteheader: [{
		    	  dataField: 'id',
		    	  text: 'No'
		    	}, {
		    	  dataField: 'type',
		    	  text: 'Dimension'
		    	}, {
		    	  dataField: 'sqrft',
		    	  text: 'Sqft',
		    	  sort: true
		    	}],
	       defaultSorted: [{
	    		  dataField: 'type',
	    		  order: 'desc'
	    		}]
	    };
	  }
	
	onTextChange(e) {
		this.setState({
			disabled : false
		});
	}
	
	onChangeButton(e) {
		
		if ( this.state.edit === 1) {
		    this.setState({
		    	edit: 2,
		    	disabled: true
		    });
		}else{
			this.setState({
		    	edit: 1,
		    	disabled: true
		    });
		}
	}
	
	render() {
	    return (
    		<div class="panel panel-default">
    		  <div class="panel-heading">
    		    <h3 class="panel-title">Owner Details</h3>
    		  </div>
    		  <div class="panel-body">

    		  <form>
	    		  <div class="form-row">
	    		   
	    		    <div class="form-group col-md-6">
	    		      <label for="inputEmail4">First Name</label>
	    		      { this.state.edit < 2 && <input type="text" onChange={this.onTextChange} class="form-control" id="fname"/>}
	    		      { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="Srinivas"/>}
	    		    </div>
	    		    
	    		    <div class="form-group col-md-6">
	    		      <label for="inputPassword4">Last Name</label>
	    		      { this.state.edit < 2 && <input type="text" class="form-control" id="lname"/> }
	    		      { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="Shettigar"/>}	  
	    		    </div>
	    		    
	    		  </div>
    		  
	    		  <div class="form-group">
	    		    <label for="inputAddress">Address Line One</label>
	    		    { this.state.edit < 2 && <input type="text" class="form-control" id="address1" placeholder="1234 Main St"/> }
	    		    { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="#520"/>}
	    		  </div>
	    		  <div class="form-group">
	    		    <label for="inputAddress2">Address Line Two</label>
	    		    { this.state.edit < 2 && <input type="text" class="form-control" id="address2" placeholder="Apartment, studio, or floor"/>}
	    		    { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="Jigani"/>}
	    		  </div>
	    		  
	    		  <div class="form-row">
	    		    <div class="form-group col-md-6">
	    		      <label for="inputCity">City</label>
	    		      { this.state.edit < 2 && <input type="text" class="form-control" id="city"/> }
	    		      { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="Bangalore"/>}
	    		    </div>
	    		    <div class="form-group col-md-4">
	    		      <label for="inputCity">State</label>
	    		      { this.state.edit < 2 && <input type="text" class="form-control" id="state"/>}
	    		      { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="Karnataka"/>}
	    		    </div>
	    		    <div class="form-group col-md-2">
	    		      <label for="inputZip">Zip</label>
	    		      { this.state.edit < 2 && <input type="text" class="form-control" id="zip"/>}
	    		      { this.state.edit > 1 && <input type="text" readonly class="form-control-plaintext" id="staticEmail2" value="560083"/>}
	    		    </div>
	    		  </div>
	    		  { this.state.edit < 2 && <button type="submit" onClick={this.onChangeButton} disabled={this.state.disabled} class="btn btn-primary">Save</button>}
	    		  { this.state.edit > 1 && <button type="submit" onClick={this.onChangeButton} class="btn btn-primary">Edit</button>}
	    		  
		    		 <Accordion>
		    		  <Card>
		    		    <Card.Header>
		    		    <div class="container-fluid">
	    		    	<div class="row">
	    		        <div class="col-2">
		    		      <Accordion.Toggle as={Button} variant="link" eventKey="0">
		    		       Contact Details
		    		      </Accordion.Toggle>
		    		       </div>
		    		       <div class="col-7"></div>
		    		       <div class="col-2">
			    		      
		    		       <div class="btn-group" role="group" aria-label="...">
		    		       <button type="button" class="btn btn-default">
			    		       <span class="glyphicon glyphicon-plus"></span>Add
			    		   </button>
			    		       <button type="button" class="btn btn-default">
			    		       <span class="glyphicon glyphicon-floppy-save"></span>Save
			    		   </button>
			    		   <button type="button" class="btn btn-default">
			    		       <span class="glyphicon glyphicon-remove"></span>Delete
			    		   </button>
			    		       
		    		       </div>
		    		       </div> 
		    		       </div>
		    		    </div>
		    		    </Card.Header>
		    		    		
		    		    		<Accordion.Collapse eventKey="0">
				    		    <ToolkitProvider
					    	        keyField="id"
					    	        data={ this.state.contacts }
					    	        columns={ this.state.contactheader }
					    	        search
					    	        >
						    	        {
						    	          props => (
					    	        		  
						    	            
								    	              <BootstrapTable
								    	                { ...props.baseProps } defaultSorted={ this.state.defaultSorted } selectRow={ this.state.selectRow }
								    	                cellEdit={ cellEditFactory({ mode: 'dbclick' }) }
								    	              />
								    	         
						    	          )
						    	        }
				    	        </ToolkitProvider>
		    	        </Accordion.Collapse>
		      		  </Card>
		      		 </Accordion>
		      		<Accordion>
		    		  <Card>
		    		    <Card.Header>
		    		    	<div class="container-fluid">
		    		    	<div class="row">
		    		        <div class="col-2">
		    		        
		    		    	<Accordion.Toggle as={Button} variant="link" eventKey="0">
			    		       Sites
			    		      </Accordion.Toggle>
			    		       </div>
			    		       <div class="col-7"></div>
			    		       <div class="col-2">
				    		      
			    		       <div class="btn-group" role="group" aria-label="...">
			    		       <button type="button" class="btn btn-default">
			    		       <span class="glyphicon glyphicon-plus"></span>Add
				    		   </button>
				    		       <button type="button" class="btn btn-default">
				    		       <span class="glyphicon glyphicon-floppy-save"></span>Save
				    		   </button>
				    		   <button type="button" class="btn btn-default">
				    		       <span class="glyphicon glyphicon-remove"></span>Delete
				    		   </button>
			    		       </div>
			    		       </div> 
			    		       </div>
			    		    </div>
		    		    </Card.Header>
		    		    <Accordion.Collapse eventKey="0">
				    		    <ToolkitProvider
					    	        keyField="id"
					    	        data={ this.state.sites }
					    	        columns={ this.state.siteheader }
					    	        search
					    	        >
						    	        {
						    	          props => (
					    	        		  
						    	            
								    	              <BootstrapTable
								    	                { ...props.baseProps } defaultSorted={ this.state.defaultSorted } selectRow={ this.state.selectRow }
								    	                cellEdit={ cellEditFactory({ mode: 'dbclick' }) }
								    	              />
								    	         
						    	          )
						    	        }
				    	        </ToolkitProvider>
		    	        </Accordion.Collapse>
		      		  </Card>
		      		 </Accordion>
	    		 
    		</form>
    		  
    		
			 </div>
		   </div>
    );
	}
}