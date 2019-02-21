package com.berritus.mis.query;

import com.alibaba.dubbo.config.annotation.Service;
import com.berritus.mis.bean.mybatis.SysFiles;
import com.berritus.mis.core.bean.task.TbSysTask;
import com.berritus.mis.core.cache.MisCache;
import com.berritus.mis.core.dao.task.TbSysTaskMapper;
import com.berritus.mis.dao.SysFilesMapper;
import com.berritus.mis.dubbo.api.QrySysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = QrySysService.class, timeout = 10000)
public class QrySysServiceImpl implements QrySysService {
    @Autowired
    private SysFilesMapper sysFilesMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TbSysTaskMapper sysTaskMapper;

    @Override
    public SysFiles qrySysFiles(Integer fileId) {
        SysFiles sysFiles = (SysFiles)redisTemplate.opsForValue().get(fileId + "");
        if(sysFiles != null){
            return sysFiles;
        }

        synchronized(this){
            sysFiles = (SysFiles)redisTemplate.opsForValue().get(fileId + "");
            if(sysFiles != null){
                return sysFiles;
            }

            sysFiles = sysFilesMapper.selectByPrimaryKey(fileId);
            if(sysFiles != null){
                redisTemplate.opsForValue().set(fileId + "", sysFiles);
                //redisTemplate.opsForValue().set("1000", "hello world");
                //ValueOperations<String, Object> operations = redisTemplate.opsForValue();
                //operations.set(fileId + "", sysFiles);

            }
            return sysFiles;
        }
    }

    //@MisCache(key=NORMAL_KEY + " + '{seqId:' + #args[0] + ',cityId:' + #args[1] + '}'")
    @Override
    @MisCache(key = "'fileId' + #args[0]", index = 0)
    public SysFiles qrySysFiles2(SysFiles bean) {
        SysFiles sysFiles = sysFilesMapper.selectByPrimaryKey(bean.getFileId());
        return sysFiles;
    }

    @Override
    @MisCache(key = "'fileId:' + #args[0]", index = 3)
    public SysFiles qrySysFiles3(Integer fileId) {
        SysFiles sysFiles = sysFilesMapper.selectByPrimaryKey(fileId);

        TbSysTask bean = new TbSysTask();
        bean.setState("S0A");
        sysTaskMapper.selectSysTasks(bean);
        return sysFiles;
    }
}
