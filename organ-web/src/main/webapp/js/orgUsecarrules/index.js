	function query(){
		var queryCompany = $("#queryCompany").val();
		var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？% \\\\ _ \\- + ’ 《》\"]");     
        if(pattern.test(queryCompany)){  
        	toastr.success("不能输入特殊字符", "提示");
        	return;
	    }
		window.location.href=base+"OrgUsecarrules/Index?queryCompany="+queryCompany+"&search=search";
	}
	function edit(ruleName){
		window.location.href=base+"OrgUsecarrules/AddIndex?ruleName="+ruleName;
	}
	function empty(){
		$("#queryCompany").val("");
		window.location.href=base+"OrgUsecarrules/Index?queryCompany="+"";
	}
	function del(id) {
		var comfirmData = {
			tittle : "提示",
			context : "规则删除，将不能恢复，确定删除？",
			button_l : "否",
			button_r : "是",
			htmltex : "<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData,function(){deletePost(id)});
	}
	
	function deletePost(id) {
		var data = {
			ruleName : id
		};
		$.post("OrgUsecarrules/DeleteOrgUsecarrules", data, function(status) {
			if (status.ResultSign == "Successful") {
				$(".pop_box").hide();
				$(window.parent.document).find(".pop_index").hide();
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = base+"OrgUsecarrules/Index";
				}
				toastr.success(message, "提示");
			} else {
				
			}
		});
	}
