import React from 'react';

export default class LogRow extends React.Component {

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
