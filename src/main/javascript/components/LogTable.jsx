import React from 'react';
import { fetchLogs } from '../utils/api';

export default class LogTable extends React.Component {

    constructor() {
        super();
        this.state = {data: []};
    }

    loadLogsFromServer() {
        fetchLogs(this.props.logType, this.props.limit).then(
            (data) => this.setState({data: data}),
            (error) => console.error('Error fetching logs from server:', error)
        )
    }

    componentDidMount() {
        this.loadLogsFromServer();
        setInterval(this.loadLogsFromServer.bind(this), this.props.pollInterval);
    }

    render() {
        return (
            <table className="table table-condensed table-hover">
                <thead>
                <tr>
                    <th>Timestamp</th>
                    <th>{this.props.logType}</th>
                </tr>
                </thead>
                <tbody>
                {this.state.data.map(log =>
                        <LogRow log={log} logType={this.props.logType}/>
                )}
                </tbody>
            </table>
        );
    }
}

class LogRow extends React.Component {

    render() {
        return (
            <tr>
                <td>{this.props.log.timestamp}</td>
                <td>{this.props.log.values[this.props.logType]}</td>
            </tr>
        );
    }
}
