var UrlModel = require('../models/urls');

//GET - Return all registers
exports.findAll = function(req, res) {
    UrlModel.getUrls(function(err, clients) {
        if(err) return res.send(500, err.message);
        console.log('GET /urls')
        res.status(200).jsonp(clients);
    });
};

//GET - Return a register with specified geohash
exports.findByGeohash = function(req, res) {
    UrlModel.getUrlGeohash(req.params.id, function(err, client) {
        if(err) return res.send(500, err.message);
        console.log('GET /urls/' + req.params.id);
        res.status(200).jsonp(client);
    });
};

//POST - Insert a new register
exports.add = function(req, res) {
    console.log('POST');
    console.log(req.body);
    var url = {
        id: req.body.id,
        url: req.body.url,
        urlAcortada: req.body.urlAcortada,
        clicks: req.body.clicks,
        latitud: req.body.latitud,
        longitud: req.body.longitud
    };
    UrlModel.insertUrl(url, function(err, client) {
        if(err) return res.send(500, err.message);
        res.status(200).jsonp(client);
    });
};

//PUT - Update a register already exists
exports.update = function(req, res) {
    UrlModel.getUrlGeohash(req.params.id, function(err, url) {
        console.log(req.body);
        var url = {
            id: req.body.id,
            url: req.body.url,
            urlAcortada: req.body.urlAcortada,
            clicks: req.body.clicks,
            latitud: req.body.latitud,
            longitud: req.body.longitud
        };
        UrlModel.updateUrl(url, function(err) {
            if(err) return res.send(500, err.message);
            res.status(200).jsonp(url);
        });
    });
};

//DELETE - Delete a register with specified ID
exports.delete = function(req, res) {
    UrlModel.deleteUrl(req.params.id, function(err, url) {
        if(err) return res.send(500, err.message);
        res.json({ message: 'Successfully deleted' });
    });
};
