"use strict";
$(document).ready(function(){
        $( function() {

            var ns = {
                header: "zmdi zmdi-chevron-down",
                activeHeader: "zmdi zmdi-chevron-up"
            };
            $("#multi-open" ).accordion({
                heightStyle: "content",
                ns: ns
            });
            $( "#sclae-accordion" ).accordion({
                heightStyle: "content",
                ns: ns
            });
            $( "#single-open" ).accordion({
                heightStyle: "content",
                ns: ns
            });
            $( "#color-accordion" ).accordion({
                heightStyle: "content",
                ns: ns
            });
        } );

        if($(".accordion-msg").attr('aria-expanded') === 'true'){
            $(".accordion-msg").addClass("scale_active");
        }
        else{
            $(".accordion-msg").removeClass("scale_active");
        }
    });
