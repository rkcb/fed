
function getNowAsIsoDate() {
    let now = new Date().toISOString();
    return now.split('T')[0];
}
