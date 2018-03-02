var exec = require("cordova/exec");

module.exports = {
    restart: function (callback) {
        exec(function (result) {
                callback(null, result)
            },
            function (error) {
                callback(error)
            }, 'AppRestart', 'restart', []);
    },
    autorestart: function (value, callback) {
        value = value === false || value === true ? value : true;
        exec(function (result) {
                callback(null, result)
            },
            function (error) {
                callback(error)
            }, 'AppRestart', 'autorestart', [value]);
    }
};
