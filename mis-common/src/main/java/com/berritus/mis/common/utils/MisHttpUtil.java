package com.berritus.mis.common.utils;

import com.berritus.mis.bean.common.ResultVO;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/7/14
 */
public class MisHttpUtil {

    public static ResultVO getResultVO(boolean successful, Integer rspCode, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setSuccessful(successful);
        resultVO.setRspCode(rspCode);
        resultVO.setMsg(msg);

        return resultVO;
    }
}
