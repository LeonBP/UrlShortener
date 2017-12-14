$(document).ready(
    function() {
        $("#shortener").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/link",
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
                    type : "GET",
                    url : "/pinpoint",
                    data : $(this).serialize(),
                    success : function(msg) {
                        $.ajax({
                             type : "GET",
                             url : "msg.uri"
                         });
                    },
                    error : function() {
                        $("#destination").html(
                                "<div class='alert alert-danger lead'>ERROR</div>");
                    }
                });
            });
    });