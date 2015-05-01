/**
 * Main function responsible for fetching the data from the server
 * and rendering the result in form of line chart.
 */
function drawChart() {
    var chart = new google.charts.Line(document.getElementById('arduino_linechart'));
    var options = {
        chart: {
            title: 'Arduino Monitor',
            subtitle: 'POT values'
        },
        width: 900,
        height: 500
    };

    $.ajax({
        url: "/logs",
        dataType: "json"
    }).done(function (logs) {
        var data = createDataTable(logs);
        chart.draw(data, options);
    });
}

/**
 * Create DataTable with logs returned by the server.
 *
 * @param {Array} logs
 * @returns {google.visualization.DataTable} DataTable filled with logs
 */
function createDataTable(logs) {
    var data = new google.visualization.DataTable();

    data.addColumn("datetime", "Time");
    data.addColumn("number", "Value");
    data.addRows(logsToRows(logs));

    return data;
}

/**
 * Convert logs returned by the server to DataTable rows.
 *
 * @param {Array} logs returned by the server
 * @returns {Array} DataTable rows
 */
function logsToRows(logs) {
    var rows = [];

    for (var i = 0; i < logs.length; i++) {
        rows.push([new Date(logs[i].timestamp), logs[i].value]);
    }

    return rows;
}
