import React from 'react';
import { capitalizeFirstLetter } from '../utils/util';

export default class LogTable extends React.Component {

    render() {
        return (
            <table className="table table-condensed table-hover">
                <thead>
                <tr>
                    <th>Timestamp</th>
                    <th>{capitalizeFirstLetter(this.props.logType)}</th>
                </tr>
                </thead>
                <tbody>
                {this.props.data.map(log =>
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
