package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.coupon.dto.OrgOrganInfoDTO;

public interface OrgOrganMapper {

	List<OrgOrganInfoDTO> listOrganByLecompanyId(String companyid);
}