import React, { Component } from "react";

import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import paginationFactory from 'react-bootstrap-table2-paginator';
import SspService from "../services/ssp.service";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
const { SearchBar } = Search;


function linkFormatter(cell, row) {
  return (
		  <div>
		  	<Link to={{
				  		pathname:'/userdetail/',	
				  		userProps:{
				  			residentId:	row.residentId
				  		}
			  		}} >{cell}</Link>
		  	</div>  
  );
}

export default class Home extends Component {
	
  constructor(props) {
    super(props);
    
    this.state = {
      error: "",
      title: "Pride Vatika Residents",
      residents: [ {'id':'0','name':'item name 0','price':'2450'},{'id':'1','name':'item name 1','price':'2500'},{'id':'2','name':'item name 2','price':'3400'} ],
      columns: [
    	{
    		dataField: 'residentId',
        	hidden: true
        },
    	{
    	  dataField: 'name',
    	  text: 'Name'
    	}, {
    	  dataField: 'unitNo',
    	  text: 'Site No',
    	  sort: true,
    	  formatter: linkFormatter
    	}, 
    	{
    	  dataField: 'unitDescription',
    	  text: 'Dimension',
    	  sort: true
    	},
    	{
    	  dataField: 'mobileNo',
    	  text: 'Mobile',
    	  sort: true
    	},
    	{
    	  dataField: 'email',
    	  text: 'Email',
    	  sort: true
    	}],
        defaultSorted: [{
    		  dataField: 'unitNo',
    		  order: 'desc'
    	}]
    };
  }

  componentDidMount() {
	  SspService.getAllResidents().then(
      response => {
        this.setState({
        	residents: response.data
        });
      },
      error => {
        this.setState({
        	error:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>{this.state.title}</h3>
        </header>
        <div>
        <ToolkitProvider
        keyField="id"
        data={ this.state.residents }
        columns={ this.state.columns }
        search
      >
        {
          props => (
            <div>
              <h3>Search on Name, Mobile, Site No </h3>
              <SearchBar { ...props.searchProps } />
              <hr />
              <BootstrapTable
                { ...props.baseProps } pagination={ paginationFactory() } defaultSorted={ this.state.defaultSorted } 
              />
            </div>
          )
        }
      </ToolkitProvider>
	      </div>
      </div>
      
    );
  }
}
