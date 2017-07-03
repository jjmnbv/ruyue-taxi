//配置账户
function allocation(allocation){
    $.post("OpInformationSet/GetOpPlatformInfo",function (status) {
        if (allocation == "alipay" && null != status && null != status.driveralipaystatus && status.driveralipaystatus != '') {
            var comfirmData={
                tittle:"提示",
                context:"<h4>确定修改支付宝收款账户配置？</h4><br><font color='red'>收款账户涉及乘客、司机及机构客户的充值及收款，修改后，款项将进入新配置账户</font>",
                contextAlign:"left",
                button_l:"确定",
                button_r:"取消",
                click: "toAllocation('" + allocation + "')",
                htmltex:"<input type='hidden' placeholder='添加的html'>"
            };
            ZconfirmLeft(comfirmData);
        } else if(allocation == "wechat" && null != status && null != status.driverwechatstatus && status.driverwechatstatus != '') {
            var comfirmData={
                tittle:"提示",
                context:"<h4>确定修改微信收款账户配置？</h4><br><font color='red'>收款账户涉及乘客、司机及机构客户的充值及收款，修改后，款项将进入新配置账户</font>",
                contextAlign:"left",
                button_l:"确定",
                button_r:"取消",
                click: "toAllocation('" + allocation + "')",
                htmltex:"<input type='hidden' placeholder='添加的html'>"
            };
            ZconfirmLeft(comfirmData);
        } else {
            toAllocation(allocation);
        }
    });
}
function toAllocation(allocation) {
    window.location.href = base+"OpInformationSet/AllocationIndex/driver/"+allocation;
}

//支付宝开通账户
function openAlipay(){
    $.post("OpInformationSet/GetOpPlatformInfo",function (status) {
        if (null != status && null != status.driveralipaystatus && status.driveralipaystatus != '') {
            var comfirmData={
                tittle:"提示",
                context:"确定开通支付宝收款功能？",
                button_l:"确定",
                button_r:"取消",
                click: "openAlipays('1')",
                htmltex:"<input type='hidden' placeholder='添加的html'> "
            };
            ZconfirmLeft(comfirmData);
        } else {
            Zalert("提示","请先配置支付宝收款账户");
        }
    });
}
function openAlipays(obj){
    var data = {driveralipaystatus: obj}
    $.ajax({
        type : 'POST',
        dataType : 'json',
        url : "OpInformationSet/EditOpPlatformInfo",
        data : JSON.stringify(data),
        contentType : 'application/json; charset=utf-8',
        async : false,
        success : function(result) {
            var resultSign = result.ResultSign;
            if(resultSign == "Successful") {
                toastr.options.onHidden = function() {
                    window.location.href = base+"OpInformationSet/driver/Index";
                }
                toastr.success("启用成功","提示");
            } else {
                toastr.error("启用失败","提示");
            }
        }
    });
}
//支付宝禁用账户
function closeAlipay(){
    $.post("OpInformationSet/GetOpPlatformInfo", function (status) {
        if (null != status && null != status.driverwechatstatus && status.driverwechatstatus != '') {
            if(status.driverwechatstatus == '0'){
                Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
            }else{
                var comfirmData={
                    tittle:"提示",
                    context:"<h4>确定关闭支付宝收款功能？</h4><br><font color='red'>关闭后，乘客端、司机端、机构端将不能使用支付宝进行支付或充值</font>",
                    contextAlign:"left",
                    button_l:"确定",
                    button_r:"取消",
                    click: "closeAlipays('0')",
                    htmltex:"<input type='hidden' placeholder='添加的html'> "
                };
                ZconfirmLeft(comfirmData);
            }
        } else {
            Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
        }
    });
}
function closeAlipays(obj){
    var data = {driveralipaystatus: obj}
    $.ajax({
        type : 'POST',
        dataType : 'json',
        url : "OpInformationSet/EditOpPlatformInfo",
        data : JSON.stringify(data),
        contentType : 'application/json; charset=utf-8',
        async : false,
        success : function(result) {
            var resultSign = result.ResultSign;
            if(resultSign == "Successful") {
                toastr.options.onHidden = function() {
                    window.location.href = base+"OpInformationSet/driver/Index";
                }
                toastr.success("禁用成功","提示");
            } else {
                toastr.error("禁用失败","提示");
            }
        }
    });
}
//微信开通账户
function openWechat(){
    $.post("OpInformationSet/GetOpPlatformInfo", function (status) {
        if (null != status && null != status.driverwechatstatus && status.driverwechatstatus != '') {
            var comfirmData={
                tittle:"提示",
                context:"确定开通微信收款功能？",
                button_l:"确定",
                button_r:"取消",
                click: "openWechats('1')",
                htmltex:"<input type='hidden' placeholder='添加的html'> "
            };
            ZconfirmLeft(comfirmData);
        } else {
            Zalert("提示","请先配置微信收款账户");
        }
    });
}
function openWechats(obj){
    var data = {
        driverwechatstatus : obj
    }
    $.ajax({
        type : 'POST',
        dataType : 'json',
        url : "OpInformationSet/EditOpPlatformInfo",
        data : JSON.stringify(data),
        contentType : 'application/json; charset=utf-8',
        async : false,
        success : function(result) {
            var resultSign = result.ResultSign;
            if(resultSign == "Successful") {
                toastr.options.onHidden = function() {
                    window.location.href = base+"OpInformationSet/driver/Index";
                }
                toastr.success("启用成功","提示");
            } else {
                toastr.error("启用失败","提示");
            }
        }
    });
}
//微信禁用账户
function closeWechat(){
    $.post("OpInformationSet/GetOpPlatformInfo", function (status) {
        if (null != status && null != status.driveralipaystatus && status.driveralipaystatus != '') {
            if(status.driveralipaystatus == '0'){
                Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
            }else{
                var comfirmData={
                    tittle:"提示",
                    context:"<h4>确定关闭微信收款功能？</h4><br><font color='red'>关闭后，乘客端、司机端、机构端将不能使用微信进行支付或充值</font>",
                    contextAlign:"left",
                    button_l:"确定",
                    button_r:"取消",
                    click: "closeWechats('0')",
                    htmltex:"<input type='hidden' placeholder='添加的html'> "
                };
                ZconfirmLeft(comfirmData);
            }
        } else {

            Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
        }
    });
}
function closeWechats(obj){
    var data = {driverwechatstatus: obj}
    $.ajax({
        type : 'POST',
        dataType : 'json',
        url : "OpInformationSet/EditOpPlatformInfo",
        data : JSON.stringify(data),
        contentType : 'application/json; charset=utf-8',
        async : false,
        success : function(result) {
            var resultSign = result.ResultSign;
            if(resultSign == "Successful") {
                toastr.options.onHidden = function() {
                    window.location.href = base+"OpInformationSet/driver/Index";
                }
                toastr.success("禁用成功","提示");
            } else {
                toastr.error("禁用失败","提示");
            }
        }
    });
}
