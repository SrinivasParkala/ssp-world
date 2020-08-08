import React, { Component } from "react";

import UserService from "../services/user.service";
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import paginationFactory from 'react-bootstrap-table2-paginator';

const { SearchBar } = Search;
const colFormatter = (cell, row) => {
	return (<div><a href={cell}>{cell}</a></div>)
}

function linkFormatter(cell, row) {
  return (
		  <div><a href="/userdetail">{cell}</a></div>
  );
}

export default class Home extends Component {
	
 
  
  constructor(props) {
    super(props);

    this.state = {
      content: "",
      products: [ {'id':'0','name':'item name 0','price':'2450'},{'id':'1','name':'item name 1','price':'2500'},{'id':'2','name':'item name 2','price':'3400'} ],
      columns: [{
    	  dataField: 'id',
    	  text: 'Product ID'
    	}, {
    	  dataField: 'name',
    	  text: 'Product Name',
    	  sort: true,
    	  formatter: linkFormatter
    	}, {
    	  dataField: 'price',
    	  text: 'Product Price',
    	  sort: true
    	}],
       defaultSorted: [{
    		  dataField: 'price',
    		  order: 'desc'
    		}]
    };
  }

  componentDidMount() {
    UserService.getPublicContent().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
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
          <h3>{this.state.content}</h3>
        </header>
        <div>
        <ToolkitProvider
        keyField="id"
        data={ this.state.products }
        columns={ this.state.columns }
        search
      >
        {
          props => (
            <div>
              <h3>Search on Name, Mobile, Site No</h3>
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
