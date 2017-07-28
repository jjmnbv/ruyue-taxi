var validator;
var v_id;
$(function () {
    v_id = $("#id").val();
    var frmmodal = $("#frmmodal");
    var erroralert = $('.alert-danger', frmmodal);
    var successalert = $('.alert-success', frmmodal);
    validator = frmmodal.validate(
        {
            submitHandler: function (form) {
                successalert.show();
                erroralert.hide();
                save();
            }
        });
    $("#btnCancel").click(function () {
        location.href = "AreaFenceSet/Index";
    })
    onSetCity();

    if (v_id != '0') {

        initForm(v_id);
    }

});
function onSetCity() {
    _loading.show();
    //设置运行城市树
    var setting = {
        async: {
            enable: true,
            url: "AreaFenceSet/GetCity",
            autoParam: ["id", "tag"],
            otherParam: {"afsId": v_id}
        },
        view: {
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
        callback: {
            onAsyncSuccess: zTreeOnAsyncSuccess,
            beforeClick: beforeClick,
            onCheck: onCheck
        }
    };
    //绑定城市列表
    $.ajax({
        type: 'POST',
        data: {afsId: v_id, pId: "tag"},
        url: "AreaFenceSet/GetCity",
        dataType: 'json',
        cache: false,
        error: function () {
            alert('系统连接失败，请稍后再试！')
        },
        success: function (data) {
            var zTree = $.fn.zTree.init($("#cityList"), setting, data);
            var nodes = zTree.getCheckedNodes(true);
            v = "";
            if (nodes != null && nodes != "undefined" && nodes != "" && nodes.length > 0) {
                var j = 0;
                for (var i = 0; i < nodes.length; i++) {
                    v += nodes[i].name + ",";
                }
            }
            if (v.length > 0) v = v.substring(0, v.length - 1);
            $("#citySel").val(v);
            // cityObj.attr("innerText", v);
        },
        complete: function (xhr, ts) {
            _loading.hide();
        }
    });
}
//异步加载成功回调函数
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    var zTree = $.fn.zTree.getZTreeObj("cityList");
    //获得当前节点的子节点
    var nodes = treeNode.children;
    //遍历每一个子节点更新其选中状态
    for (var i = 0; i < nodes.length; i++) {
        nodes[i].checked = treeNode.checked;
        //更新节点
        zTree.updateNode(nodes[i]);
    }
}

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("cityList");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onCheck(e, treeId, treeNode) {

    var zTree = $.fn.zTree.getZTreeObj("cityList"),
        nodes = zTree.getCheckedNodes(true),
        v = "";
    //展开当前节点 触发异步请求子节点的动作
    zTree.expandNode(treeNode, true, true, true);
    for (var i = 0, l = nodes.length; i < l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0) v = v.substring(0, v.length - 1);
    $("#citySel").val(v);
    // cityObj.attr("value", v);
}


//初始化栅栏信息界面，如果id>0代表修改，否则代码<img src="~/Content/assets/img/icon/add.png" />新增

function initForm(id) {
    $.ajax({
        url: "AreaFenceSet/GetById",
        cache: false,
        data: {id: id},
        success: function (json) {
            setForm(json);
        },
        error: function (xhr, status, error) {
            // showerror(xhr.responseText, "AreaFenceSet/Index");
            return;
        }
    });
}

function setForm(entity) {
    $("#txtName").val(entity.name);
}
//保存
function save() {

    var zTree = $.fn.zTree.getZTreeObj("cityList");
    var nodes = zTree.getCheckedNodes(true);
    var treenodes = "";//存放已选择节点ID
    if (nodes != null && nodes != "undefined" && nodes != "" && nodes.length > 0) {
        var j = 0;
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].isParent == false || (nodes[i].isParent == true && (nodes[i].check_Child_State == -1 || nodes[i].check_Child_State == 0))) {
                treenodes += nodes[i].id + "|";
                j++;
            }
        }
    } else {
        toastr.warning("至少选择一个城市", "错误提示");
        return;
    }
    _loading.show();
    //组合数据
    var data = {
        id: v_id,
        name: $("#txtName").val(),
        cityId: treenodes
    }

    $.ajax({
        type: 'POST',
        cache: false,
        dataType: 'json',
        url: v_id != '0' ? 'AreaFenceSet/Update' : 'AreaFenceSet/Add',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (data) {
            if (data.status == 0) {
                setTimeout(function () {
                    location.href = basePath + "AreaFenceSet/Index";
                }, 500);
                return;

            } else {
                var message = data.msg == null ? data
                    : data.msg;
                toastr.error(message, "提示");
            }
        }
    });
}