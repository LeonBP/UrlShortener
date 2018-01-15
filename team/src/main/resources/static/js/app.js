$(document).ready(
    function() {
        $("#shortener").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/new",
                    data : JSON.stringify({"url":document.getElementById("url").value, "mode":mode, "precision":"4"}),
                    contentType : "application/json",
                    success : function(msg) {
                        $("#shortURL").html(
                            "<div class='alert alert-success lead'><a target='_blank' href='"
                            + location.host
                            +"/"
                            + msg
                            + "'>"
                            + location.host
                            +"/"
                            + msg
                            + "</a></div>");
                    },
                    error : function(msg) {
                        $("#shortURL").html(
                                "<div class='alert alert-danger lead'><p>"
                                + msg.code
                                + "\n"
                                + msg.message
                                + "</p></div>");
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
                    data : JSON.stringify({"redirect":redirect, "radio":document.getElementById("radio").value, "resultNumber":document.getElementById("resultNumber").value}),
                    contentType : "application/json",
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
                            + " "
                            + msg.hash
                            + " "
                            + msg.url
                            + "</a></div>");
                    },
                    error : function(msg) {
                        $("#destination").html(
                            "<div class='alert alert-danger lead'>"
                            + msg.code
                            + " "
                            + msg.message
                            + "</div>");
                    }
                });
            });
    });
    
var redirect="false";
function redirectChange(requested){
   redirect=requested;
}
var mode="0";
function modeChange(code){
   mode=code;
}