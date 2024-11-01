import React, {Component} from "react";
import './App.css';
import IncidentApp from "./component/IncidentApp";

class App extends Component {
  render() {
    return (
        <div className="container">
            <IncidentApp />
        </div>
    );
  }
}


export default App;
