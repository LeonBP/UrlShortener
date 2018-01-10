var express = require('express');
var router = express.Router();

router.route('/')
    .get( function(req, res, next) {
        var http = require('http');
        var options = {
            host: 'localhost',
            path: '/urls',
            port: '3000'
        };

        callback = function(response) {
            var str = '';

            //another chunk of data has been recieved, so append it to `str`
            response.on('data', function (chunk) {
                str += chunk;
            });

            //the whole response has been recieved, so we just print it out here
            response.on('end', function () {
                console.log(str);
            });

        }

        http.request(options, callback).end();
        res.json(200,"Petición GET realizada con éxito");
    });
module.exports = router;