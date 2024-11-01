import axios from 'axios'

const HOST_URL = 'http://localhost:8080'
const INCIDENT_API_URL = `${HOST_URL}/api`

class IncidentDataService {
    fireIncident(incident) {
        return axios.post(`${INCIDENT_API_URL}/incident`, incident);
    }

    editIncident(id, incident) {
        return axios.put(`${INCIDENT_API_URL}/incident/${id}`, incident);
    }

    retrieveAllIncidents(name, page, size) {
        return axios.get(`${INCIDENT_API_URL}/incident?page=${page}&size=${size}`);
    }

    retrieveCourse(id) {
        return axios.get(`${INCIDENT_API_URL}/incident/${id}`);
    }

    deleteIncident(id) {
        return axios.delete(`${INCIDENT_API_URL}/incident/${id}`);
    }

    processIncident(id) {
        return axios.patch(`${INCIDENT_API_URL}/incident:process/${id}`);
    }

    closeIncident(id) {
        return axios.patch(`${INCIDENT_API_URL}/incident:close/${id}`);
    }
}

export default new IncidentDataService()
