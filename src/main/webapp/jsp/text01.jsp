<%--
  Created by IntelliJ IDEA.
  User: XPS
  Date: 2017/6/9
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../easyUI/themes/icon.css" type="text/css"/>
    <link rel="stylesheet" href="../easyUI/themes/default/easyui.css" type="text/css"/>
    <script src="../easyUI/jquery.min.js"></script>
    <script src="../easyUI/jquery.easyui.min.js"></script>
</head>
<body>

<div id="p" style="padding:10px;">
    <p>panel content.</p>
    <p>panel content.</p>
</div>
<br/>
<a id="ruleCheck" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true">规则检查</a>
<script>
$('#p').panel({
width:700,
height:150,
title:'My Panel',
tools:[{
iconCls:'icon-add',
handler:function(){alert('new')}
},{
iconCls:'icon-save',
handler:function(){alert('save')}
}]
});
</script>
</body>
</html>
