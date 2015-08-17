import React from 'react';
import LogTable from './LogTable';

export default class Index extends React.Component {

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-md-12">
                        <LogTable logType={'temperature'}
                                  limit={this.props.limit}
                                  pollInterval={this.props.pollInterval}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <LogTable logType={'humidity'}
                                  limit={this.props.limit}
                                  pollInterval={this.props.pollInterval}/>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-12">
                        <LogTable logType={'light'}
                                  limit={this.props.limit}
                                  pollInterval={this.props.pollInterval}/>
                    </div>
                </div>
            </div>
        );
    }
}

// render Index
React.render(
    <Index limit={5} pollInterval={2000}/>,
    document.getElementById('app')
);
