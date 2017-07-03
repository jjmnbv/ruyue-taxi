	$(document).ready(function() {
		/* //  	关联帐号点击
		$(".add_link span").click(function() {
			$(".popup_box,.link_user").show();
			$(".link_user").siblings().hide();
		})
		//		解除关联点击
		$(".remove_link span").click(function() {
			$(".popup_box,.link_remove1").show();
			$(".link_remove1").siblings().hide();
		})
		//		确定按钮点击
		$(".popup_footer .sure").click(function() {
			$(".popup_box,.link_user").hide();
		}) */
		//		取消按钮点击
		$(".popup_footer .cancel").click(function() {
			$(".popup_box,.link_user").hide();
		})
	});
	function removeLink(id,orgOrganCompanyRefId,mainaccount){
		if(mainaccount == '0'){
			$("#orgOrganCompanyRefIdRemove1").val(orgOrganCompanyRefId);
			$(".select_val").attr("value","");
			$(".popup_box,.link_remove1").show();
			$(".link_remove1").siblings().hide();
		}else{
			$("#orgOrganCompanyRefIdRemove").val(orgOrganCompanyRefId);
			$(".popup_box,.link_remove2").show();
			$(".link_remove2").siblings().hide();
		}
		
	};
	function addLink(id,orgOrganCompanyRefId,mainaccount){
		$("#passwordOld").val("");
		$("#passwordNew").val("");
		$("#passwordNew1").val("");
		$(".popup_box,.link_user").show();
		$(".link_user").siblings().hide();
		$.post("SupplierManagement/GetById", {"id": id}, function (data) {
	       $("#addLinkName").text(data.account+"("+data.name+")");
	       $("#orgOrganCompanyRefIdAdd").val(data.orgOrganCompanyRefId);
	       $("#userId").val(data.id);
	    })
	}
	function saveAddLinkName(){
		var passwordOld = $("#passwordOld").val();
		var passwordNew = $("#passwordNew").val();
		var passwordNew1 = $("#passwordNew1").val();
		if(passwordOld != ''){
		  if(passwordNew != ''){
			  if(passwordNew1 != ''){
			if(passwordNew.length < 6 || passwordNew1.length < 6){
				toastr.error("密码必须大于6位", "提示");
				return;
			}
			if(passwordOld === passwordNew){
				toastr.error("新密码不能和原密码相同", "提示");
				return;
			}
			if(passwordNew.length > 16 || passwordNew1.length > 16){
				toastr.error("密码必须小于16位", "提示");
				return;
			}
			var reg = /[\u4e00-\u9fa5]/g;
			if(reg.test(passwordOld)){
				toastr.error("密码格式不正确", "提示");
				return;
			}
			if(reg.test(passwordNew)){
				toastr.error("新密码格式不正确", "提示");
				return;
			}
			if(passwordNew === passwordNew1){
				var userId = $("#userId").val();
				$.post("SupplierManagement/CheckPassword", {"id": userId,"userpassword":passwordOld}, function (data) {
					if (data > 0) {// 代表 密码正确
						var orgOrganCompanyRefIdAdd = $("#orgOrganCompanyRefIdAdd").val();
						$.post("SupplierManagement/AddLink", {"orgOrganCompanyRefId": orgOrganCompanyRefIdAdd,"id": userId,"userpassword":passwordNew}, function (data) {
							if (data.ResultSign == "Successful") {
//								$(".popup_box,.link_user").show();
//								$(".link_user").siblings().hide();
								$(".popup_box").hide();
								$(window.parent.document).find(".pop_index").hide();
								var message = data.MessageKey == null ? data
										: data.MessageKey;
								toastr.options.onHidden = function() {
									window.location.href = base+"SupplierManagement/Index";
								}
								toastr.success(message, "提示");
							} else {
								
							}
					    });
					} else {
						toastr.error("密码不正确", "提示");
					}
			    })
			}else{
				toastr.error("两次密码不一样", "提示");
			}
		   }else{
			   toastr.error("重复新密码不能为空", "提示");
		   }
		  }else{
			  toastr.error("新密码不能为空", "提示");
		  }
		}else{
			toastr.error("密码不能为空", "提示");
		}
	}
	
	function saveRemoveLink(){
		var orgOrganCompanyRefIdRemove = $("#orgOrganCompanyRefIdRemove").val();
		$.post("SupplierManagement/RemoveLink", {"id": orgOrganCompanyRefIdRemove}, function (data) {
			if (data.ResultSign == "Successful") {
				$(".popup_box,.link_remove2").show();
				$(".link_remove1").siblings().hide();
				var message = data.MessageKey == null ? data
						: data.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = base+"SupplierManagement/Index";
				}
				toastr.success(message, "提示");
			} else {
				
			}
	    })
	}
	
	function saveRemoveLink1(){
		var orgOrganCompanyRefIdRemove1 = $("#orgOrganCompanyRefIdRemove1").val();
		var orgOrganCompanyRefId = $(".select_val").attr("data-value");
		if(orgOrganCompanyRefId != null && orgOrganCompanyRefId != ''){
			$.post("SupplierManagement/RemoveLink1", {"id": orgOrganCompanyRefIdRemove1,"orgOrganCompanyRefId":orgOrganCompanyRefId}, function (data) {
				if (data.ResultSign == "Successful") {
					$(".popup_box,.link_remove1").show();
					$(".link_remove2").siblings().hide();
					var message = data.MessageKey == null ? data
							: data.MessageKey;
					toastr.options.onHidden = function() {
						window.location.href = base+"User/Logout";
					}
					toastr.success(message, "提示");
				} else {
					
				}
		    })
		}else{
			toastr.error("请选择子账号", "提示");
			return;
		}
	}
