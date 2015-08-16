import $ from 'jquery';
import React from 'react';

class LogTable extends React.Component {

    constructor() {
        super();
        this.state = {data: []};
    }

    loadLogsFromServer() {
        $.ajax({
            url: `${this.props.url}/${this.props.logType}?limit=${this.props.limit}`,
            cache: false,
            dataType: 'json',
            success: (data) => {
                this.setState({data: data});
            },
            error: (xhr, status, error) => {
                console.error(this.props.url, status, error);
            }
        });
    }

    componentDidMount() {
        this.loadLogsFromServer();
        setInterval(this.loadLogsFromServer.bind(this), this.props.pollInterval);
    }

    render() {
        const rows = this.state.data.map(log =>
                <LogRow log={log} logType={this.props.logType}/>
        );

        return (
            <table className="table table-condensed table-hover">
                <thead>
                <tr>
                    <th>Timestamp</th>
                    <th>{this.props.logType}</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {rows}
                </tbody>
            </table>
        );
    }
}

class LogRow extends React.Component {

    render() {
        // TODO replace with real chart
        var bars = "";
        for (var i = 0; i < this.props.log.values[this.props.logType]; i++) {
            bars += "|";
        }

        return (
            <tr>
                <td>{this.props.log.timestamp}</td>
                <td>{this.props.log.values[this.props.logType]}</td>
                <td>{bars}</td>
            </tr>
        );
    }
}

// render log tables
React.render(
    <div>
        <div className="row">
            <div className="col-md-12">
                <LogTable url={'/logs'} logType={'temperature'} limit={5} pollInterval={2000}/>
            </div>
        </div>
        <div className="row">
            <div className="col-md-12">
                <LogTable url={'/logs'} logType={'humidity'} limit={5} pollInterval={2000}/>
            </div>
        </div>
        <div className="row">
            <div className="col-md-12">
                <LogTable url={'/logs'} logType={'light'} limit={5} pollInterval={2000}/>
            </div>
        </div>
    </div>,
    document.getElementById('app')
);
