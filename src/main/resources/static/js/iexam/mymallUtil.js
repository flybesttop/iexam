function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '年' + m + '月' + d + ' ' + h + ':' + minute + ':' + second;
};

function getUrlParam(data) {
    let reg = new RegExp(`(^|&)${data}=([^&]*)(&|$)`);
    let url = window.location.search.substring(1);
    if (reg.test(url)) {
        url = url.split("=")[1];
    }

    return url;

}