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
            <table>
                <tr>
                    <td>Timestamp</td>
                    <td>{this.props.logType}</td>
                    <td></td>
                </tr>
                {rows}
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
        <LogTable url={'http://localhost:8080/logs'} logType={'temperature'} limit={5} pollInterval={2000}/>
        <LogTable url={'http://localhost:8080/logs'} logType={'humidity'} limit={5} pollInterval={2000}/>
    </div>,
    document.getElementById('content')
);
