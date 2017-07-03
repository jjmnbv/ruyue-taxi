$(document).ready(function() {
	var modelAPI="OrgUsecarrules/GetLeVehiclemodels?id=";
	//首次加载页面请求初始数据
	for(q=1;q<=3;q++){
		var id=q-1;
		$.ajax({
			type: 'get',
			url: 'OrgUsecarrules/GetLeLeasescompany?rulesType='+q,
			async : false,
			dataType: 'json',
			success: function (data) {
				int_data = data;
			}, error: function () {
				alert("服务器报错，请重新刷新页面");
			}
		});
		if(id==0){
			window.data_0=int_data;
		}
		if(id==1){
			window.data_1=int_data;
		}
		if(id==2){
			window.data_2=int_data;
		}

		//请求车类型并且逐个插入选项卡
		$.ajax({
			type: 'get',
			url: modelAPI + int_data[0].id + "&&rulesType="+q,
			async : false,
			dataType: 'json',
			success: function (modeldata) {
			
				var ml = modeldata.length;
				var lei = "";
				for (i = 0; i < ml; i++) {
					if (i == 0) {
						lei += '<div class="lei on" id="' + modeldata[i].id + '">' + modeldata[i].name + '</div>';
					} else if (i % 3 == 0) {
						lei += '<div class="lei" style="margin-left: 0px;" id="' + modeldata[i].id + '">' + modeldata[i].name + '</div>';
					} else {
						lei += '<div class="lei" id="' + modeldata[i].id + '">' + modeldata[i].name + '</div>';
					}
				}
				var tex = '<div class="right_box_m"><div class="right_box_s"><div class="select_box"></div>' + lei + '</div><span class="xinzeng">添加</span></div>';
				$("#"+id).append(tex);
			}
		});
		select(1, id);
	}
		//重复执行函数，用于添加select选项
		function select(sl,id){
			var data=eval("window.data_"+id);
			var dl=data.length;
			for (j=0;j<sl;j++){
				var li='<input class="select_val" readonly data-value="'+data[j].id+'" value="'+data[j].shortname+'"><ul class="select_content">';
				for (i=sl;i<dl;i++){
					li+='<li data-value="'+data[i].id+'">'+data[i].shortname+'</li>';
				}
				li+='</ul>';
				$(".right_box:eq("+id+")").find(" .select_box:eq("+j+")").html(li);
			}
		}

		//用户重新选择按钮
		$(".select_content>li").live("click", function(){
			var tindex=$(this).parents(".right_box_m").index();
			var n=$(this).parents(".right_box").children(".right_box_m").length;
			var sli=$(this).index()+n;
			var id=$(this).parents(".right_box").attr("id");
			var rulesType=id+1;
			var thisid=$(this).attr("data-value");
			$(this).parents(".right_box_m").find(".lei").remove();
			var nthis=$(this);
			$.ajax({
				type : 'get',
				url : modelAPI+thisid+"&&rulesType="+rulesType,
				dataType : 'json',
				success : function(modeldata){
					var ml=modeldata.length;
					var lei="";
					for (i=0;i<ml;i++){
						if(i==0){
							lei+='<div class="lei on" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
						}else if(i% 3 == 0){
							lei+='<div class="lei" style="margin-left: 0px;" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
						}else{
							lei+='<div class="lei" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
						}
					}
					nthis.parents(".right_box_s").append(lei);

					if(id==0){
						data_0.push(data_0[tindex]);
						data_0[tindex]=data_0[sli];
						data_0.splice(sli,1);
						window.data_0=data_0;
					}
					if(id==1){
						data_1.push(data_1[tindex]);
						data_1[tindex]=data_1[sli];
						data_1.splice(sli,1);
						window.data_1=data_1;
					}
					if(id==2){
						data_2.push(data_2[tindex]);
						data_2[tindex]=data_2[sli];
						data_2.splice(sli,1);
						window.data_2=data_2;
					}
					select(n,id);
				}
			});
		});

		//新增按钮操作
		$(".right_box .xinzeng").live('click', function(){
			var id=$(this).parents(".right_box").attr("id");
			var rulesType=id+1;
			var n=$(this).parents(".right_box").children(".right_box_m").length;
			var data=eval("window.data_"+id);
			var dl=data.length;
			if(n<dl){
				xthis = $(this).parents(".right_box");
				$.ajax({
					type : 'get',
					url : modelAPI+data[n].id+"&&rulesType="+rulesType,
					dataType : 'json',
					success : function(modeldata){
						var ml=modeldata.length;
						var lei="";
						for (i=0;i<ml;i++){
							if(i==0){
								lei+='<div class="lei on" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
							}else if(i% 3 == 0){
								lei+='<div class="lei" style="margin-left: 0px;" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
							}else{
								lei+='<div class="lei" id="'+modeldata[i].id+'">'+modeldata[i].name+'</div>';
							}
						}
						var tex='<div class="right_box_m"><div class="right_box_s"><div class="select_box"></div>'+lei+'</div><span class="xinzeng">添加</span> <span class="font-red">删除</span></div>';
						xthis.append(tex);
						select(n+1,id);
					}
				});
				if($(this).next().attr("class")!=="font-red"){
					$(this).before('<span class="font-red">删除</span>');
				}
				$(this).remove();
			}
		});

		// 删除按钮操作
		$(".font-red").live("click", function(){
			var n=$(this).parents(".right_box").children(".right_box_m").length;
			var tindex=$(this).parent(".right_box_m").index();
			if(n>1){
				var id=$(this).parents(".right_box").attr("id");
				if(id==0){
					data_0.push(data_0[tindex]);
					data_0.splice(tindex,1);
					window.data_0=data_0;
				}
				if(id==1){
					data_1.push(data_1[tindex]);
					data_1.splice(tindex,1);
					window.data_1=data_1;
				}
				if(id==2){
					data_2.push(data_2[tindex]);
					data_2.splice(tindex,1);
					window.data_2=data_2;
				}
				if(n==2&&tindex==0){
					$(this).parents(".right_box").find(".font-red:eq(1)").remove();
				}
				if(tindex===n-1){
					var m=n-2;
					$(this).parents(".right_box").find(".font-red:eq("+m+")").before('<span class="xinzeng">添加</span>');
					if(n==2){
						$(this).parents(".right_box").find(".font-red:eq("+m+")").remove();
					}
				}
				$(this).parent(".right_box_m").remove();
				select(n-1,id);
			}
		});
	});


	//是否checked,是选中的话显示选择框
	$(".right>input").click(function(){
		var n=$(this).index()/3;
		if($(this).is(':checked')){
			$(".right_box:eq("+n+")").show();
		}else{
			$(".right_box:eq("+n+")").hide();
		}
	});

	//点击返回、取消两个按钮之后的操作
	$(".btn_grey,.btn_green").click(function(){
		Zconfirm("确定放弃新增规则？",function(){
			$(window.parent.document).find(".pop_index").hide();
			location.href = base+"OrgUsecarrules/Index";
		});
	});

	//车类型选择
	$(".right_box_s .lei").live('click', function(){
		$(this).toggleClass("on");
	});



	//跪着得出的最后数组组合
	$(".btn_red").click(function(){
		var arrbox = {};
		for (i=0;i<3;i++){
			id=i+1;
			if($(".company:eq("+i+")").is(':checked')){
				var n=$(".right_box:eq("+i+")").children(".right_box_m").length;
				var lbox=[];
				for(j=0;j<n;j++){
					var obj=$(".right_box:eq("+i+")").children(".right_box_m:eq("+j+")");
					var m=obj.find(".on").length;
					var value=obj.find(".select_val").attr("data-value");
					var modelbox=obj.find(".on:eq(0)").attr("id");
					for(h=1;h<m;h++){
						var  modelId=obj.find(".on:eq("+h+")").attr("id");
						modelbox+=","+modelId;
					}

					lbox.push({leasesCompanyId: value, modelId: modelbox});
				}
				arrbox[id] = lbox;
			}
		}
		if(arrbox == '' || arrbox == null){

		}
		console.info(arrbox);
		if($("#ruleName").val() != ''){
			$.ajax({
				type : 'post',
				url : 'OrgUsecarrules/Add?ruleName='+$("#ruleName").val(),
				data : JSON.stringify(arrbox),
				dataType : 'json',
				contentType: "application/json; charset=utf-8",
				success : function(status){
					if (status.ResultSign == "Successful") {
						var message = status.MessageKey == null ? status
							: status.MessageKey;
						toastr.options.onHidden = function() {
							window.location.href = base+"OrgUsecarrules/Index";
						}
						toastr.success(message, "提示");
					} else {
						var message = status.MessageKey == null ? status
							: status.MessageKey;
						toastr.error(message, "提示");
					}
				}
			});
		}else{
			toastr.error("规则名称不能空", "提示");
		}
	});


