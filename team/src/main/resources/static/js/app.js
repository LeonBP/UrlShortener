$(document).ready(
    function() {
        $("#shortener").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/",
                    data : {"url":document.getElementById("url").value, "mode":mode, "clientIP":"10.1.54.154", "precision":"4"},
                    success : function(msg) {
                        $("#shortURL").html(
                            "<div class='alert alert-success lead'><a target='_blank' href='"
                            + msg.title
                            + "'>"
                            + msg.title
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
                    data : {"redirect":redirect, "radio":document.getElementById("radio").value, "resultNumber":document.getElementById("resultNumber").value},
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
    
var redirect="false";
function redirectChange(requested){
   redirect=requested;
}
var mode="0";
function modeChange(code){
   mode=code;
}