<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2017/6/9
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../easyUI/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="../easyUI/themes/default/easyui.css" type="text/css"/>
    <script src="../easyUI/jquery.min.js"></script>
    <script src="../easyUI/jquery.easyui.min.js"></script>
</head>

<body class="easyui-layout">
<div id="north"
     data-options="region:'north',title:'WSDL2RADL Transition System',split:true,disabled:true,collapsible:false"
     style="height:100px;" >
    <p style="padding: 20px;margin: auto;font-size: large;">WSDL->Radl-WS转换系统</p>
</div>

<div id="west"
     data-options="region:'west',title:'WSDL',split:true,disabled:true,collapsible:false"
     style="width:700px;">
    &nbsp; &nbsp;
    <a id="ruleCheck" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">规则检查</a>
    &nbsp; &nbsp;
    <a id="transition" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">进行转换</a>
    <input id="wsdl" type="text" multiline="true" class="easyui-textbox" style="width:100%;height:590px;">

</div>

<div id="center" data-options="region:'center',title:'RADL-WS',disabled:true" style="padding:5px;">
</div>

<script type="text/javascript">

    $("#transition").click(function () {
        var wsdl = $("#wsdl").val();
        var url = "/trans";
        var radl = $("#center");
        var sendData = {'wsdl':wsdl};
        $.post(url,sendData,function (backInfo) {
            radl.text("");
            radl.append(backInfo.content);
        })

    });

    $("#ruleCheck").click(function () {
        alert("hello");
    });
</script>
</body>

</html>
