package com.gmm.ocr.controller;

import com.gmm.ocr.common.base.AjaxResult;
import com.gmm.ocr.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 * 
 * @author leon
 */
public class BaseController
{

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message)
    {
        return AjaxResult.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 分页参数封装
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public Map startPages(Integer pageSize, Integer pageNumber) {
        int startRow = (pageSize * (pageNumber - 1));
        int endRow = pageSize;

        Map map = new HashMap();
        map.put("startRow",startRow);
        map.put("endRow",endRow);

        return map;
    }

    /**
     * 分页返回数据
     * @param list
     * @param total
     * @param pageSize
     * @param pageNumber
     * @return
     */
    public Map getDataMap(List<?> list,int total,int pageSize,int pageNumber){
        Map resultMap = new HashMap();
        resultMap.put("list",list);
        resultMap.put("total",total);
        resultMap.put("pageSize",pageSize);
        resultMap.put("pageNumber",pageNumber);
        resultMap.put("hasNextPage",pageSize * pageNumber >= total ? false : true);

        return resultMap;
    }

}
