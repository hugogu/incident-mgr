import {Component} from "react";
import IncidentDataService from "../service/IncidentDataService";
import {Field, Form, Formik} from "formik";
import {withRouter} from 'react-router-dom';

class IncidentComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            title: "",
            raiser: "",
            assignee: "",
            status: "OPEN",
            description: "",
            message: null,
        }

        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(values) {
        let incident = {
            title: values.title,
            raiser: values.raiser,
            assignee: values.assignee,
            status: values.status,
            description: values.description
        }

        IncidentDataService.fireIncident(incident).then(
            response => {
                console.log("Received response:" + response)
                this.props.history.push('/incidents?page=0')
            },
            error => {
                console.log("Received error:" + error.response)
                this.setState({ message: `Failed to create incident because ${error.response.data.detail}.` })
            }
        )
    }


    render() {
        let { description, status, assignee, raiser, title } = this.state

        return (
            <div>
                <h3>Incident</h3>
                {this.state.message && <div class="alert alert-success">{this.state.message}</div>}
                <div className="container">
                    <Formik
                        initialValues={{title, description, status, assignee, raiser }}
                        onSubmit={this.onSubmit}
                    >
                        {
                            (props) => (
                                <Form>
                                    <fieldset className="form-group">
                                        <label>Title</label>
                                        <Field className="form-control" type="text" name="title"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field className="form-control" as="textarea" rows="4" name="description"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Raiser</label>
                                        <Field className="form-control" type="text" name="raiser"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Assignee</label>
                                        <Field className="form-control" type="text" name="assignee"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Status</label>
                                        <Field className="form-control" type="text" name="status" disabled/>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>
                </div>
            </div>
        )
    }

}

export default withRouter(IncidentComponent)
