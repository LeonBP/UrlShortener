$(document).ready(
    function() {
        $("#shortener").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/",
                    data : $(this).serialize(),
                    success : function(msg) {
                        $("#shortURL").html(
                            "<div class='alert alert-success lead'><a target='_blank' href='"
                            + msg.uri
                            + "'>"
                            + msg.uri
                            + "</a></div>");
                    },
                    error : function() {
                        $("#shortURL").html(
                                "<div class='alert alert-danger lead'>ERROR</div>");
                    }
                });
            });
    });
    
$(document).ready(
    function() {
        $("#pinpoint").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/pinpoint",
                    dataType: "json",
                    data : JSON.stringify({"redirect":false, "radio":100, "resultNumber":2}),
                    contentType: "application/json",
                    success : function(msg) {
                        $("#destination").html(
                            "<p class='lead'>"
                            + msg.ip
                            + " "
                            + msg.city
                            + " "
                            + msg.latitude
                            + " "
                            + msg.longitude
                            + "</a></div>");
                    },
                    error : function() {
                        $("#destination").html(
                                "<div class='alert alert-danger lead'>ERROR</div>");
                    }
                });
            });
    });