package com.berritus.mis.dubbo.api;

import com.berritus.mis.bean.mybatis.SysFiles;

public interface QrySysService {
    SysFiles qrySysFiles(Integer fileId);
    SysFiles qrySysFiles2(SysFiles bean);
    SysFiles qrySysFiles3(Integer fileId);
}
