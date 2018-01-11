//llamamos al paquete mysql que hemos instalado
var mysql = require('mysql');
var port = normalizePort(process.env.PORT || '3000');
function normalizePort(val) {
    var port = parseInt(val, 10);

    if (isNaN(port)) {
        // named pipe
        return val;
    }

    if (port >= 0) {
        // port number
        return port;
    }

    return false;
}
if(port === 3000){
    //creamos la conexion a nuestra base de datos con los datos de acceso de cada uno
    connection = mysql.createConnection(
        {
            host: 'localhost',
            user: 'root',
            password: 'toor',
            database: 'ingWeb'
        }
    );
}else{
    //connection = mysql.createConnection(
      //  {
          //  host: 'us-cdbr-iron-east-05.cleardb.net',
          //  user: 'bd017a21c402e9',
          //  password: 'dba55194821a283',
          //  database: 'heroku_815057acb052552'
       // }
    //);
}

//creamos un objeto para ir almacenando todo lo que necesitemos
var urlModel = {};

//obtenemos todos los jugadores
urlModel.getUrls = function(callback)
{
    if (connection)
    {
        connection.query('SELECT * FROM urls ORDER BY id', function(error, rows) {
            if(error)
            {
                throw error;
            }
            else
            {
                callback(null, rows);
            }
        });
    }
}

//obtenemos un jugador por su id
urlModel.getUrlGeohash = function(geohash,callback)
{
    if (connection)
    {
        var sql = 'SELECT * FROM urls WHERE urlAcortada = ' + connection.escape(geohash);
        connection.query(sql, function(error, row)
        {
            if(error)
            {
                throw error;
            }
            else
            {
                callback(null, row);
            }
        });
    }
}

//añadir un nuevo jugador
urlModel.insertUrl = function(urlData,callback)
{
    if (connection)
    {
        connection.query('INSERT INTO urls SET ?', urlData, function(error, result)
        {
            if(error)
            {
                throw error;
            }
            else
            {
//devolvemos la última id insertada
                callback(null,{"insertId" : result.insertId});
            }
        });
    }
}

urlModel.updateUrl = function(urlData, callback)
{
    if (connection)
    {
        var sql = 'UPDATE urls SET url = ' + connection.escape(urlData.url) + ',' +
            'clicks = ' + connection.escape(urlData.clicks) +',' +
            'latitud = ' + connection.escape(urlData.latitud) + ',' +
            'longitud = ' + connection.escape(urlData.longitud) +
            ' WHERE urlAcortada = ' + urlData.urlAcortada;

        connection.query(sql, function(error, result)
        {
            if(error)
            {
                throw error;
            }
            else
            {
                callback(null,{"msg":"success"});
            }
        });
    }
}


//eliminar un usuario pasando la id a eliminar
urlModel.deleteUrl = function(geohash, callback)
{
    if(connection)
    {
        var sqlExists = 'SELECT * FROM urls WHERE urlAcortada = ' + connection.escape(geohash);
        connection.query(sqlExists, function(err, row)
        {
//si existe la id del usuario a eliminar
            if(row)
            {
                var sql = 'DELETE FROM urls WHERE urlAcortada = ' + connection.escape(geohash);
                connection.query(sql, function(error, result)
                {
                    if(error)
                    {
                        throw error;
                    }
                    else
                    {
                        callback(null,{"msg":"deleted"});
                    }
                });
            }
            else
            {
                callback(null,{"msg":"notExist"});
            }
        });
    }
}

//exportamos el objeto para tenerlo disponible en la zona de rutas
module.exports = urlModel;