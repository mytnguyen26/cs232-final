import React, {useState,  useEffect, Component} from "react";
import DatePicker from 'react-datepicker';

import "react-datepicker/dist/react-datepicker.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from 'react-bootstrap/Form'
import Container from 'react-bootstrap/Container';
import ListGroup from 'react-bootstrap/ListGroup';
import ListGroupItem from 'react-bootstrap/esm/ListGroupItem';
import './App.css';
import axios from "axios";

const Items = () => {
  const [items, setItems] = useState([]);
  const fetchItem = () => {
    axios.get("http://localhost:8080/api/v1/Item").then( res => {
      console.log(res);
      setItems(res.data);
    });
  }
  useEffect(() => {
    fetchItem();
  }, []);

  const renderCard = (card, index) => {
    return (
      <Col md="auto">
        <Card bg = {"primary"} 
          text = {"light"} 
          style={{ width: '18rem' }} 
          key={index}
          className="box">
          <Card.Body>
            <Card.Title>{card.itemName}</Card.Title>
            <Card.Text>{card.itemDesc}</Card.Text>
          </Card.Body>
          <ListGroup>
            <ListGroupItem>{card.itemQuantity}</ListGroupItem>
            <ListGroupItem>{card.currPrice}</ListGroupItem>
          </ListGroup>
        </Card> 
      </Col>
      
    );
  }
  return <Container><Row>{items.map(renderCard)}</Row></Container>
}; 


class CreateItem extends Component {
  constructor(props) {
    super(props)
    this.state = {
      name: '',
      itemDesc: '',
      itemPriority: '',           
      itemQuantity: '',
      // currPrice: '',
      itemState: '',
      itemDue: ''
    }
  }

  changeHandler = (e) => {
    this.setState({[e.target.name]: e.target.value})
  }
  onDateChange = (date) => {
    this.setState({
      itemDue: date
    })
  } 


  submitHandler = e => {
    // e.preventDefault()
    console.log(this.state.itemState)
    axios
    .post("http://localhost:8080/api/v1/Item", this.state)
    .then(res => {
      console.log(res)
    })
    .catch(error => {
      console.log(error)
    })
  }
  render() {
    const {name, itemDesc, itemPriority, itemQuantity} = this.state
    return (
      <div>
        <form onSubmit={this.submitHandler}>
          <div>
            <label>Item Name</label>
            <input type = "text" name = "itemName" value = {name} onChange={this.changeHandler} />
          </div>
          <div>
            <label>Description</label>
            <input type = "text" name = "itemDesc" value = {itemDesc} onChange={this.changeHandler} />
          </div>
          <div>
            <label>Priority</label>
            <input type = "number" name = "itemPriority" value = {itemPriority} onChange={this.changeHandler} />
          </div>
          <div>
            <label>Quantity</label>
            <input type = "text" name = "itemQuantity" value = {itemQuantity} onChange={this.changeHandler} />
          </div>
          <div>
            <input type = "hidden" name = "itemState" value = "add" onChange={this.changeHandler} />
          </div>
          <div>
            <label>Due Date</label>
            
            <DatePicker
              onChange={this.onDateChange}
              name ="dueDate"
              selected = {this.state.itemDue }
              dateFormat="yyyy-MM-dd" />
          </div>
          <button type = "submit" className = "btn btn-primary" >Create New Item</button>
        </form>
      </div>
    )

  }
}


// TODO
// PUT REQUEST TO UPDATE ITEM STATE BY ID


// 

function App() {
  return (
    <div className="App">
      <h1>Hello</h1>
      <CreateItem />
      <h2>List of Current Items</h2>
      <Items />
    </div>
  );
}

export default App;
