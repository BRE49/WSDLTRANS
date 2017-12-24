<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script src="../easyUI/jquery.min.js"></script>
    <script src="../easyUI/jquery.easyui.min.js"></script>
</head>
<body>
<div>
    <button id="hiddenButton" style="display: none"></button>
</div>
<div id="list"/>
<script type="text/javascript">
    var hiddenButton = $("#hiddenButton");
    hiddenButton.bind("myClick",function () {
        $.get("/fileList",function (backInfo) {
            if(backInfo.code === 1) {
                var files = eval(backInfo);

                for (var i=0;i<files["data"].length;i++){
                    var fileName = files["data"][i];
                    var fileNameLink = document.createElement("a");
                    fileNameLink.innerHTML = fileName;
                    fileNameLink.setAttribute("href","index.jsp?"+fileName);
                    $("#list").append(fileNameLink,"<br/>");
                }
            }
        });
    });
    hiddenButton.trigger("myClick");
</script>
</body>
</html>
