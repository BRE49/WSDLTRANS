<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2017/6/9
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
    <input id="chooseWSDL" class="easyui-filebox" style="width:130px" data-options="buttonText:'选择WSDL文件'">
    &nbsp; &nbsp;
    <a id="openWSDL" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">打开文件</a>
    &nbsp; &nbsp;
    <a id="ruleCheck" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">语法检查</a>
    &nbsp; &nbsp;
    <a id="transition" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">进行转换</a>
    &nbsp; &nbsp;
    <a id="clearWsdl" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清空WSDL</a>
    <input id="wsdl" type="text" multiline="true" class="easyui-textbox" style="width:100%;height:580px;">
</div>

<div id="center" data-options="region:'center',title:'RADL-WS',disabled:true" style="padding:5px;">
</div>

<script type="text/javascript">

        $("#clearWsdl").click(function () {
            $("#wsdl").textbox("setValue", "");
        });

        $("#openWSDL").click(function () {
            var filePath = $("#chooseWSDL").filebox('getValue');
            var url = "/open?filePath=" + filePath;
            $.get(url, function (result) {
                var status = result.code;
                if (status === 0) {
                    alert(result.info);
                } else {
                    var wsdl = result.data;
                    $("#wsdl").textbox("setValue", wsdl);
                }
            });
        });

        $("#transition").click(function () {
            var wsdl = $("#wsdl").val();
            var url = "/trans";
            var radl = $("#center");
            var sendData = {'wsdl': wsdl};
            $.post(url, sendData, function (backInfo) {
                radl.text("");
                if (backInfo.code === 1) {
                    alert(backInfo.info);
                    radl.append(backInfo.data);
                } else {
                    alert(backInfo.info);
                }
            })
        });

        $("#ruleCheck").click(function () {
            var wsdl = $("#wsdl").val();
            var url = "/ruleCheck";
            var sendData = {'wsdl': wsdl};
            $.post(url, sendData, function (backInfo) {
                var status = backInfo.code;
                if (status === 1) {
                    alert("语法检查通过");
                } else {
                    alert(backInfo.info);
                }
            });
        });

</script>
</body>

</html>
