package com.szyciov.op.dto;

import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;

import java.util.List;

/**
 * Created by lzw on 2017/8/16.
 */
public class OpPlatformBusinesslicenseDto extends OpPlatformBusinesslicense {

    private List<String> operationareas;

    public List<String> getOperationareas() {
        return operationareas;
    }

    public void setOperationareas(List<String> operationareas) {
        this.operationareas = operationareas;
    }
}
