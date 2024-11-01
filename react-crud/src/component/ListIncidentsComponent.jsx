import {Component} from "react";
import IncidentDataService from "../service/IncidentDataService";

class ListIncidentsComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            incidents: [],
            message: null
        }
        this.refreshIncidents = this.refreshIncidents.bind(this)
        this.deleteIncidentClicked = this.deleteIncidentClicked.bind(this)
        this.processIncidentClicked = this.processIncidentClicked.bind(this)
        this.resolveIncidentClicked = this.resolveIncidentClicked.bind(this)
        this.addIncidentClicked = this.addIncidentClicked.bind(this)
    }

    componentDidMount() {
        this.refreshIncidents();
    }

    refreshIncidents() {
        IncidentDataService.retrieveAllIncidents("hugo").then(
            response => {
                console.log(response)
                this.setState({incidents: response.data.content})
            }
        )
    }

    deleteIncidentClicked(id) {
        IncidentDataService.deleteIncident(id)
            .then(
                response => {
                    this.setState({ message: `Delete of incident ${id} Successful` })
                    this.refreshIncidents()
                }
            )
    }

    processIncidentClicked(id) {
        IncidentDataService.processIncident(id)
            .then(
                response => {
                    this.setState({ message: `You have picked incident ${id} for processing successful` })
                    this.refreshIncidents()
                }
            )
    }

    resolveIncidentClicked(id) {
        IncidentDataService.closeIncident(id)
            .then(
                response => {
                    this.setState({ message: `You have resolved incident ${id}.` })
                    this.refreshIncidents()
                }
            )
    }

    addIncidentClicked() {
        this.props.history.push(`/incident/-1`)
    }

    render() {
        return (
            <div className="container">
                <h3>All Incidents</h3>
                {this.state.message && <div class="alert alert-success">{this.state.message}</div>}
                <div className="container">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Raiser</th>
                            <th>Status</th>
                            <th>Assignee</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.incidents.map(
                                incident =>
                                    <tr key={incident.id}>
                                        <td>{incident.title}</td>
                                        <td>{incident.raiser}</td>
                                        <td>{incident.status}</td>
                                        <td>{incident.assignee}</td>
                                        <td>{incident.description}</td>
                                        <td>
                                            <button className="btn"
                                                    onClick={() => this.processIncidentClicked(incident.id)}>Process
                                            </button>
                                        </td>
                                        <td>
                                            <button className="btn"
                                                    onClick={() => this.resolveIncidentClicked(incident.id)}>Resolve
                                            </button>
                                        </td>
                                        <td>
                                            <button className="btn btn-warning"
                                                    onClick={() => this.deleteIncidentClicked(incident.id)}>Delete
                                            </button>
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addIncidentClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default ListIncidentsComponent
