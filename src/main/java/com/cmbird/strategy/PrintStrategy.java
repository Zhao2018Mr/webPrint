package com.cmbird.strategy;

import com.cmbird.print.websocket.domain.RequestVo;
import com.cmbird.utils.AjaxResult;

import javax.print.PrintException;

/**
 * @author zyj
 * @date 2021年05月28日 13:13
 */
public interface PrintStrategy {

    /**
     * 打印
     * @author zyj
     * @date 2021/5/28 13:15
     * @param requestVo
     * @return java.lang.String
     */
    AjaxResult print(String data) throws Exception;
}
