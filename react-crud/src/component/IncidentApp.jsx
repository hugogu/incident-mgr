import React, { Component } from 'react';
import ListIncidentsComponent from "./ListIncidentsComponent";
import IncidentComponent from "./IncidentComponent";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

class IncidentApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <h1>Incident Application</h1>
                    <Switch>
                        <Route path="/" exact component={ListIncidentsComponent} />
                        <Route path="/incident" exact component={ListIncidentsComponent} />
                        <Route path="/incident/:id" component={IncidentComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default IncidentApp
