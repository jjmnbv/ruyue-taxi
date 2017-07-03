<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传demo</title>
</head>
<body>
	<img style="width:500px;height:500px;background-color:gray;" id="imgback"></img>
	<input id="fileupload" type="file" name="file" multiple style="display:none;"/>
	<input id="clear" type="button" name="clear" value="删除"/>
	<script type="text/javascript" src="content/js/jquery.js"></script>
	<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
	<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
	<script src="content/plugins/fileupload/jquery.fileupload.js"></script>
	<script type="text/javascript">
	$('#fileupload').fileupload({
		url:"FileUpload/uploadFile",
	    dataType: 'json',
	    done: function(e, data) {
	        if(data.result.status=="success"){
	        	$("#imgback").attr("src",data.result.basepath+"/"+data.result.message[0]);
	        }
	    }
	});
	$("#imgback").click(function(){
		$("#fileupload").click();
	});
	$("#clear").click(function(){
		$("#fileupload").val("");
		$("#imgback").attr("src","");
	});
	</script>
</body>
</html>