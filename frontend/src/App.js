import React, {useState,  useEffect, Component} from "react";
import logo from './logo.svg';
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

  return items.map((item, index) => {
    return  (
    <div key={index}> 
      <p>{item.name}</p>
      <p>{item.itemDesc}</p>
      <p>{item.itemQuantity}</p>
      <p>{item.currPrice}</p>
    </div>
    )
  })
}; 

// TODO 
// POST Request Send to Add new Item
class PostForm extends Component {
  constructor(props) {
    super(props)
    this.state = {
      name: '',
      itemDesc: '',
      itemPriority: ''
    }
  }

  changeHandler = (e) => {
    this.setState({[e.target.name]: e.target.value})
  }

  submitHandler = e => {
    // e.preventDefault()
    console.log(this.state)
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
    const {name, itemDesc, itemPriority} = this.state
    return (
      <div>
        <form onSubmit={this.submitHandler}>
          <div>
            <input type = "text" name = "name" value = {name} onChange={this.changeHandler} />
          </div>
          <div>
            <input type = "text" name = "itemDesc" value = {itemDesc} onChange={this.changeHandler} />
          </div>
          <div>
            <input type = "number" name = "itemPriority" value = {itemPriority} onChange={this.changeHandler} />
          </div>
          <button type = "submit">Create New Item</button>
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
      <Items />
      <PostForm />
    </div>
  );
}

export default App;
