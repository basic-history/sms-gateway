<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
</head>
<body>

<h4>userId:</h4><h4 id="userId">${userId}</h4>
<h4>token:</h4><h4 id="token">${token}</h4>

<script>
    $(document).ready(function () {

        var userId = $("#userId").val();
        var token = $("#token").val();

        var url = '<c:url value="/login/authc"/>';

        if(!userId || !token){
            window.location.href= url;
        }

    });

    // (function ($) {
    //     $.getUrlParam = function (name) {
    //         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //         var r = window.location.search.substr(1).match(reg);
    //         if (r != null) return unescape(r[2]); return null;
    //     }
    // })(jQuery);
</script>
</body>


</html>