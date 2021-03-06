import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { Component, useEffect, useState } from "react";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import ListGroupItem from 'react-bootstrap/esm/ListGroupItem';
import Navbar from 'react-bootstrap/Navbar';
import Form from 'react-bootstrap/Form';
import ListGroup from 'react-bootstrap/ListGroup';
import Row from 'react-bootstrap/Row';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';


const PaidItems = () => {
  const [items, setItems] = useState([]);
  const fetchItem = () => {
    axios.get("http://localhost:8080/api/v1/Item/bought").then( res => {
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
        <Card bg = {"secondary"} 
          text = {"light"} 
          style={{ width: '18rem' }} 
          key={index}
          className="box">
          <Card.Body>
            <Card.Title>{card.itemName}</Card.Title>
            <Card.Text>{card.itemDesc}</Card.Text>
    
          </Card.Body>
        </Card> 
      </Col>
      
    );
  }
  return <Container><Row>{items.map(renderCard)}</Row></Container>
}; 

const Wallet = () => {
  const [items, setItems] = useState([]);
  const fetchItem = () => {
    axios.get("http://localhost:8080/api/v1/Item/wallet").then( res => {
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
        <Card 
          text = {"dark"} 
          style={{ width: '18rem' }} 
          key={index}
          className="box">
          <Card.Header>Current Planned Balance</Card.Header>
          <Card.Body>
            <Card.Title>{card.currBalance}</Card.Title>
            <Card.Text>{card.walletName}</Card.Text>
          </Card.Body>
        </Card> 
      </Col>
    );
  }
  return <Container><Row>{items.map(renderCard)}</Row></Container>
}; 

class ActiveItems extends Component {
  constructor(props) {
    super(props)
    this.state = {
      items: []
    }
  }
  componentDidMount() {
    axios.get("http://localhost:8080/api/v1/Item")
    .then( res => {
      console.log(res);
      this.setState({items: res.data});
    });
  }

  removeItem = (card, e) => {
    axios.delete(`http://localhost:8080/api/v1/Item/remove/${card.id}`)
    .then(res => {
      window.location.reload(false);
      console.log("Item Deleted");
    })
    .catch(err => {
      console.log("err");
    });
  }

  payItem = (card, e) => {
    axios.post(`http://localhost:8080/api/v1/Item/pay/${card.id}`)
    .then (res => {
      window.location.reload(false);
      console.log("Successfully Pay For Item");
    })
    .catch(err => {
      console.log("err");
    })
  }

  renderCard = (card, index) => {
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
          <Button onClick={(e) => this.removeItem(card, e)}>Remove</Button>
          <Button onClick={(e) => this.payItem(card, e)}>I bought this</Button>
        </Card> 

      </Col>
    );
  }
  render(){
    const {items} = this.state;
    return <Container><Row>{items.map(this.renderCard)}</Row></Container>
  } 
};
class CreateItem extends Component {
  constructor(props) {
    super(props)
    this.state = {
      itemName: '',
      itemDesc: '',
      itemPriority: '',           
      itemQuantity: '',
      itemState: 'add',
      itemType: 'need',
      itemDue: ''
    }
  }

  changeHandler = (e) => {
    console.log(this.state)
    this.setState({[e.target.name]: e.target.value})
  }
  onDateChange = (date) => {
    this.setState({
      itemDue: date
    })
  } 


  submitHandler = e => {
    // e.preventDefault()
    console.log(this.state)
    axios
    .post("http://localhost:8080/api/v1/Item/", this.state)
    .then(res => {
      console.log(res)
    })
    .catch(error => {
      console.log(error)
    })
  }
  render() {
    const {itemName, itemDesc, itemPriority, itemQuantity} = this.state
    return (
      <Container style={{ width: '20rem' }}>
        
        <Form onSubmit={this.submitHandler}>
          <Row className="mb-3">
            <Form.Label htmlFor="inlineFormInputName" visuallyHidden >Item Name</Form.Label>
            <Form.Control placeholder="Item Name" type = "text" name = "itemName" value = {itemName} onChange={this.changeHandler} />
          </Row>
          <Row className="mb-3">
            <Form.Label htmlFor="inlineFormInputName" visuallyHidden >Description</Form.Label>
            <Form.Control placeholder="Description" as = "textarea" rows={3} name = "itemDesc" value = {itemDesc} onChange={this.changeHandler} />
          </Row>
          
            <Form.Group as ={Col} md="4" className="position-relative">
              <Form.Label htmlFor="inlineFormInputName" visuallyHidden >Priority</Form.Label>
              <Form.Control placeholder="priority" type = "number" name = "itemPriority" value = {itemPriority} onChange={this.changeHandler} />
              <Form.Label htmlFor="inlineFormInputName" visuallyHidden >Quantity</Form.Label>
              <Form.Control placeholder="quantity" type = "number" name = "itemQuantity" value = {itemQuantity} onChange={this.changeHandler} />
              <Form.Label htmlFor="inlineFormInputName" >Due Date</Form.Label>
              <DatePicker
                onChange={this.onDateChange}
                name ="dueDate"
                selected = {this.state.itemDue }
                dateFormat="yyyy-MM-dd" />
            </Form.Group>
            <Row className="mb-3">
            <Button type = "submit">Create New</Button>
            </Row>
          
        </Form>
      </Container>
    )
  }
}


function App() {
  return (
    <div className="App">
      <Navbar bg="dark" variant="dark">
        <Container>
        <Navbar.Brand href="#home">WishList.ByMe</Navbar.Brand>
        </Container>
      </Navbar>
      <h1>Wish List</h1>
      <CreateItem />
      <Wallet />
      <h2>List of Current Items</h2>
      <ActiveItems />
      <h2>List of Bought Items</h2>
      <div>
      <PaidItems />
      {/* <ActiveItems /> */}
      </div>
    </div>
  );
}

export default App;
