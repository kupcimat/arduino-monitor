import moment from 'moment';

export function formatDate(isoDateTimeString) {
    return moment(isoDateTimeString).format('D. M. YYYY');
}

export function formatTime(isoDateTimeString) {
    return moment(isoDateTimeString).format('HH:mm:ss');
}

export function formatLogValue(logType, value) {
    if (logType === 'temperature') {
        return value.toFixed(1) + ' °C';
    } else if (logType === 'humidity') {
        return value.toFixed(0) + ' %';
    } else if (logType === 'light') {
        return value.toFixed(0) + ' %';
    } else {
        throw 'unknown log type: ' + logType;
    }
}

export function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
