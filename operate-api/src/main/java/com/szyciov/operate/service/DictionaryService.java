package com.szyciov.operate.service;

import com.szyciov.entity.Dictionary;
import com.szyciov.operate.dao.DictionaryDao;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("dictionaryService")
public class DictionaryService {

	@Autowired
	private DictionaryDao dao;

	public List<Dictionary> getDictionaryByType(String str) {
		return dao.getDictionaryByType(str);
	}
	
	public List<Dictionary> getDictionaryByTypeText(String str,String text) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("queryType", str);
		map.put("text", text);
		return dao.getDictionaryByTypeText(map);
	}

	public PageBean getDictionaryByQuery(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Dictionary> list = getDictionaryListByQuery(queryParam);
		int iTotalRecords = getDictionaryListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<Dictionary> getDictionaryListByQuery(QueryParam queryParam) {
		return dao.getDictionaryListByQuery(queryParam);
	}

	public int getDictionaryListCountByQuery(QueryParam queryParam) {
		return dao.getDictionaryListCountByQuery(queryParam);
	}

	public void deleteDictionary(String id) {
		dao.deleteDictionary(id);
	}

	public Dictionary getById(String id) {
		return dao.getById(id);
	}

	public Map<String, String> createDictionary(Dictionary dictionary) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "字典创建成功");
		//添加前检查数据是否已存在
		int count = checkIsExist(dictionary);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "字典已存在，请勿重复添加");
		}else{
			String uuid = UUID.randomUUID().toString();//.replace("-", "");
			dictionary.setId(uuid);
			dao.createDictionary(dictionary);
		}
		return ret;
	}
	
	/**
	 * 添加前检查数据是否已存在
	 * @author luxiangda
	 * @date 2016年4月26日
	 * @param dictionary
	 * @return
	 */
	public int checkIsExist(Dictionary dictionary){
//		QueryParam param = new QueryParam();
//		param.setKey(dictionary.getType());
//		param.setiSortColName(dictionary.getValue());
//		param.setiDisplayStart(dictionary.getId());
		return dao.getDictionaryListCountByEqualQuery(dictionary);
	}

	public Map<String, String> updateDictionary(Dictionary dictionary) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "字典修改成功");
		if(checkIsExist(dictionary)>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "字典已存在，请勿重复添加");
		}else{
			dao.updateDictionary(dictionary);
		}
		return ret;
	}
	
	public Dictionary getDictionaryText(Map<String, String> map){
		return dao.getDictionaryText(map);
	};
}
