import $ from 'jquery';

export function getLogValues(logType, data) {
    return data.map(log => log.values[logType]);
}

export function fetchLogs(logType, limit) {
    return wrapjQueryPromise($.ajax({
        url: `/logs/${logType}?limit=${limit}`,
        cache: false,
        dataType: 'json'
    }));
}

function wrapjQueryPromise(jQueryPromise) {
    return new Promise((resolve, reject) => {
        jQueryPromise.then(
            (data) => resolve(data),
            (xhr, status, error) => reject(error)
        );
    })
}
