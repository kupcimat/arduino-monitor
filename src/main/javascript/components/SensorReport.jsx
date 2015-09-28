import React from 'react';
import {
    Sparklines,
    SparklinesLine,
    SparklinesSpots
} from 'react-sparklines';
import LogTable from './LogTable';
import {
    fetchLogs,
    getLogValues
} from '../utils/api';
import { capitalizeFirstLetter } from '../utils/util';

export default class SensorReport extends React.Component {

    constructor() {
        super();
        this.state = {data: [], showSummary: true};
    }

    loadLogsFromServer() {
        fetchLogs(this.props.logType, this.props.limit).then(
            (data) => this.setState({data: data, showSummary: this.state.showSummary}),
            (error) => console.error('Error fetching logs from server:', error)
        )
    }

    handleSensorClick() {
        // TODO helper method for changing state?
        this.setState({data: this.state.data, showSummary: !this.state.showSummary});
    }

    componentDidMount() {
        this.loadLogsFromServer();
        setInterval(this.loadLogsFromServer.bind(this), this.props.pollInterval);
    }

    renderSummary() {
        return (
            <div>
                <h4>{capitalizeFirstLetter(this.props.logType)}</h4>
                <Sparklines data={getLogValues(this.props.logType, this.state.data)} limit={this.props.limit}>
                    <SparklinesLine/>
                    <SparklinesSpots/>
                </Sparklines>
            </div>
        );
    }

    renderFull() {
        return (
            <LogTable logType={this.props.logType} data={this.state.data}/>
        );
    }

    render() {
        return (
            <button type="button" className="list-group-item" onClick={this.handleSensorClick.bind(this)}>
                {this.state.showSummary ? this.renderSummary() : this.renderFull()}
            </button>
        );
    }
}
