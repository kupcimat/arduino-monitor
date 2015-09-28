import moment from 'moment';

export function formatDate(isoDateTimeString) {
    return moment(isoDateTimeString).format('D. M. YYYY');
}

export function formatTime(isoDateTimeString) {
    return moment(isoDateTimeString).format('HH:mm:ss');
}

export function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
