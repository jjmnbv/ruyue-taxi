$.fn.dataTableExt.oApi.fnSearch = function (oSettings, oData, language) {
    var oSettings = this.fnSettings();
    oSettings._iDisplayStart = 0;//重新从第0条查询
    if (oSettings.aoServerParams.length > 0) {
        oSettings.aoServerParams.pop(oSettings.aoServerParams[0]);
    }
    oSettings.aoServerParams.push({
        "fn": function (aoData) {
        	//搜索回调参数支持函数
        	if(oData!=null&&typeof oData === "function"){
      			oData(aoData);
      		}else{
      			for (var i = 0, iLen = oData.length ; i < iLen ; i++) {
              aoData.push(oData[i]);
      			}
      		}
        }
    });
    
    if (!language) {
    	language = "没有查询到相关信息";
    }
    oSettings.oLanguage.sEmptyTable = language;

    this.fnDraw();
};
