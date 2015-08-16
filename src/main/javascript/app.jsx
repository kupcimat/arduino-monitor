var LogTable = React.createClass({

    loadLogsFromServer: function () {
        $.ajax({
            url: this.props.url + '/' + this.props.logType + '?limit=' + this.props.limit,
            cache: false,
            dataType: 'json',
            success: function (data) {
                this.setState({data: data});
            }.bind(this),
            error: function (xhr, status, error) {
                console.error(this.props.url, status, error);
            }.bind(this)
        });
    },

    getInitialState: function () {
        return {data: []};
    },

    componentDidMount: function () {
        this.loadLogsFromServer();
        setInterval(this.loadLogsFromServer, this.props.pollInterval);
    },

    render: function () {
        var rows = this.state.data.map(function (log) {
            return <LogRow log={log} logType={this.props.logType}/>
        }.bind(this));

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
});

var LogRow = React.createClass({

    render: function () {
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
});

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
