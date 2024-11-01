import {Component} from "react";
import IncidentDataService from "../service/IncidentDataService";

class ListIncidentsComponent extends Component {

    constructor(props) {
        super(props);
        const queryParams = new URLSearchParams(this.props.location.search);
        this.state = {
            incidents: [],
            message: null,
            page: parseInt(queryParams.get('page')) || 0,
            size: parseInt(queryParams.get('size')) || 10,
            totalPages: 0
        }
        this.refreshIncidents = this.refreshIncidents.bind(this)
        this.deleteIncidentClicked = this.deleteIncidentClicked.bind(this)
        this.processIncidentClicked = this.processIncidentClicked.bind(this)
        this.resolveIncidentClicked = this.resolveIncidentClicked.bind(this)
        this.addIncidentClicked = this.addIncidentClicked.bind(this)
        this.handlePageChange = this.handlePageChange.bind(this)
    }

    componentDidMount() {
        this.refreshIncidents();
    }

    refreshIncidents() {
        console.log("refreshIncidents with page: " + this.state.page)
        IncidentDataService.retrieveAllIncidents("hugo", this.state.page, this.state.size).then(
            response => {
                console.log(response)
                this.setState({
                    incidents: response.data.content,
                    totalPages: response.data.totalPages
                })
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
        this.props.history.push(`/incident`)
    }

    handlePageChange(newPage) {
        this.setState({ page: newPage }, () => {
            this.props.history.push(`/incidents?page=${this.state.page}&size=${this.state.size}`);
            this.refreshIncidents();
        });
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
                    <div className="d-flex">
                        <button className="btn btn-success" onClick={this.addIncidentClicked}>Add</button>
                        <div className="ml-auto">
                            <button className="btn btn-primary"
                                    onClick={() => this.handlePageChange(this.state.page - 1)}
                                    disabled={this.state.page === 0}>Previous
                            </button>
                            <button className="btn btn-primary"
                                    onClick={() => this.handlePageChange(this.state.page + 1)}
                                    disabled={this.state.page + 1 >= this.state.totalPages}>Next
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ListIncidentsComponent
