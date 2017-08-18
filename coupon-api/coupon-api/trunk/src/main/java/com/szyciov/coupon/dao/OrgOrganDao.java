package com.szyciov.coupon.dao;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.OrgOrganInfoDTO;
import com.szyciov.coupon.mapper.OrgOrganMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrgOrganDao {

	@Resource
	private OrgOrganMapper organMapper;

	/**
	 * 获取机构端ID
	 * @param companyid
	 * @return
	 */
	public List<OrgOrganInfoDTO> listOrganByLecompanyId(String companyid){

		return organMapper.listOrganByLecompanyId(companyid);
	}


}