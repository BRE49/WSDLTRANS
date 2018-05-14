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
    <form id="ff" enctype="multipart/form-data" style="margin: 0;display: inline">
    <input id="chooseWSDL" name="file" class="easyui-filebox" style="width:120px" data-options="buttonText:'选择文件'" title="file">
    </form>
    &nbsp; &nbsp;
    <a id="openWSDL" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">上传并打开</a>
    &nbsp; &nbsp;
    <a id="cloud" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cloud'">云文件</a>
    <button id="openCloudFile" style="display: none">打开云文件</button>
    &nbsp; &nbsp;
    <a id="ruleCheck" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">语法检查</a>
    &nbsp; &nbsp;
    <a id="transition" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">进行转换</a>
    &nbsp; &nbsp;
    <a id="clearWsdl" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">清空WSDL</a>
    &nbsp; &nbsp;
    <div id="dialog"></div>
    <input id="wsdl" type="text" multiline="true" class="easyui-textbox" style="width:100%;height:655px;">
</div>

<div id="center" data-options="region:'center',title:'RADL-WS',disabled:true" style="padding:5px;">
    <a id="bigger" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">字体增大</a>
    &nbsp; &nbsp;
    <a id="smaller" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">字体缩小</a>
    &nbsp; &nbsp;
    <input id="radl" type="text" multiline="true" class="easyui-textbox" style="width:100%;height:650px;">
</div>

<script type="text/javascript">
        $("#clearRadl").click(function () {
            var radl = $("#center");
            radl.empty();
        });

        $("#clearWsdl").click(function () {
            $("#wsdl").textbox("setValue", "");
        });
        $("#openWSDL").click(function () {
            var form = new FormData(document.getElementById("ff"));

            $.ajax({
                url:"/fileUpload",
                type:"post",
                data:form,
                processData:false,
                contentType:false,
                success:function(backInfo){
                    var fileName = backInfo.info;
                    var url = "/open?filePath=" + fileName;
                    $.get(url, function (result) {
                        var status = result.code;
                        if (status === 0) {
                            alert(result.info);
                        } else {
                            var wsdl = result.data;
                            $("#wsdl").textbox("setValue", wsdl);
                        }
                    });
                }
            });


        });

        $("#transition").click(function () {
            var wsdl = $("#wsdl").val();
            var url = "/trans";
            var radl = $("#radl");
            var sendData = {'wsdl': wsdl};
            $.post(url, sendData, function (backInfo) {
                radl.text("");
                if (backInfo.code === 1) {
                    alert(backInfo.info);
                    radl.textbox("setValue",backInfo.data);
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

        $("#bigger").click(function () {
            var radl = $("#radl").textbox("textbox");
            var size = parseInt(radl.css("font-size"));
            size++;
            radl.css("font-size",size+"px");
        });

        $("#smaller").click(function () {
            var radl = $("#radl").textbox("textbox");
            var size = parseInt(radl.css("font-size"));
            size--;
            radl.css("font-size",size+"px");
        });
        
    //cloud files
        $("#cloud").click(function () {
            var dialog = $("#dialog");
            dialog.dialog({
                title: 'Cloud Files',
                width: 400,
                height: 200,
                modal: true
            });

            dialog.dialog('refresh',"/jsp/cloud.jsp");
        });

        var openCloudFile = $("#openCloudFile");
        openCloudFile.bind("clickOnce",function () {
            var fileName = window.location.search.substring(1);
            if(fileName !== ""){
                var url = "/open?filePath=" + fileName;
                $.get(url, function (result) {
                    var status = result.code;
                    if (status === 0) {
                        alert(result.info);
                    } else {
                        var wsdl = result.data;
                        $("#wsdl").textbox("setValue", wsdl);
                    }
                });
            }
        });
        openCloudFile.trigger("clickOnce");
</script>
</body>

</html>
