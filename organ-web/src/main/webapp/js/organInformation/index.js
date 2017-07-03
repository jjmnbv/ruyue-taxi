/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		ignore:'',
		rules: {
			creditcardnum1: {required: true, maxlength: 23,accountcheck:true},
			creditcardname1: {required: true,maxlength:30,namecheck:true},
			bankname1: {required: true,maxlength: 30,namecheck:true},
		},
		messages: {
			creditcardnum1: {required: "开户账号不能为空",maxlength: "最大长度不能超过19个字符",accountcheck:"开户账号格式错误"},
			creditcardname1: {required: "开户名称不能为空",maxlength:"最大长度不能不超过30个汉字",namecheck:"开户名称格式错误"},
			bankname1: {required: "开户银行不能为空",maxlength:"最大长度不能不超过30个汉字",namecheck:"开户银行格式错误"},
		}
	})
}
$.validator.addMethod("accountcheck", function(value, element, param) {
	if(value.length<=23 && value.length>=19) {
	  return true;
	}
	return false;
}, "");
$.validator.addMethod("namecheck",function(value,element,params){
    var namecheck = /^[\u4e00-\u9fa5]+$/;
       return this.optional(element)||(namecheck.test(value));},"");
function edit(){
 $(".popup_box").show();
 $(".popup_box .add_department").show();
}
/**
 * 取消
 */
function canel() {
	 $(".popup_box").hide();
	 $(".popup_box .add_department").hide();
	 location.reload(true);
}
/*$("#cancel").click(function(){
	 $(".popup_box").hide();
	 $(".popup_box .add_department").hide();
})*/
function save(){
	validateForm();
	var form = $("#editForm");
	var a = !form.valid();
	if(!form.valid()) return;
	var editForm = $("#editForm").validate();
		var creditcardnum = $("#creditcardnum1").val();
		var creditcardname = $("#creditcardname1").val();
		var bankname = $("#bankname1").val();
		var organId = $("#organId").val();
		 var data = {
				 creditcardnum : creditcardnum,
				 creditcardname : creditcardname,
				 bankname : bankname,
				 organId: organId
		}
		$.ajax({
			type: "POST",
			url:"OrganInformation/InsertOrgInformation",
			cache: false,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data:JSON.stringify(data),
			success: function (response) {
				if (response.ResultSign == "Successful") {
					var message = response.MessageKey == null ? response : response.MessageKey;
	            	toastr.options.onHidden = function() {
	            		window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "/OrganInformation/Index";
					}
					toastr.success(message, "提示");

				} else {
					
				}	
			},
			error: function (xhr, response, error) {
				toastr.error("网络中断", "提示");
				return;
			}
	 });
		/*//隐藏并且刷新页面
		$("#organInformation").hide();
		location.reload(true);*/
	}
//卡号每4位一组格式化
$("#creditcardnum").on("keyup", formatBC);
$("#creditcardnum1").on("keyup", formatBC);

function formatBC(e){

  $(this).attr("data-oral", $(this).val().replace(/\ +/g,""));
  //$("#bankCard").attr("data-oral")获取未格式化的卡号

  var self = $.trim(e.target.value);
  var temp = this.value.replace(/\D/g, '').replace(/(....)(?=.)/g, '$1 ');
  if(self.length > 23){
    this.value = self.substr(0, 23);
    return this.value;
  }
  if(temp != this.value){
    this.value = temp;
  }
}
