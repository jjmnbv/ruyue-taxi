package com.szyciov.util;

import java.util.List;

/**
 * Excel模板接口规范
 * Created by lzw on 2017/8/15.
 */
public interface ExcelTemplate {
    /**
     * 获取字段名称
     * @return
     */
    List<String> getCloumnList();

    /**
     * 获取列名列表
     * @return
     */
    List<String> getTitleList();

    /**
     * 获取例子列表
     * @return
     */
    List<String> getExampleList();

    /**
     * 验证标题的正确性
     * @param titles
     * @return
     */
    boolean verifyTitle(String[] titles);
}
