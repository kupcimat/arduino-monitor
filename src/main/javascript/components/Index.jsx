import React from 'react';
import SensorReport from './SensorReport';

export default class Index extends React.Component {

    render() {
        return (
            <div className="list-group">
                <SensorReport logType={'temperature'} limit={this.props.limit} pollInterval={this.props.pollInterval}/>
                <SensorReport logType={'humidity'} limit={this.props.limit} pollInterval={this.props.pollInterval}/>
                <SensorReport logType={'light'} limit={this.props.limit} pollInterval={this.props.pollInterval}/>
            </div>
        );
    }
}

// render Index
React.render(
    <Index limit={10} pollInterval={2000}/>,
    document.getElementById('app')
);
