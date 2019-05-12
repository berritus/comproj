package com.berritus.mis.out.of.memory.error;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: VM Args: -Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
 * 将堆的最小值-Xms参数与最大值-Xmx参数设置为一样即可避免堆自动扩展
 * 通过参数-XX:+HeapDumpOnOutOfMemoryError可以让虚拟机在出现内存溢出异常时Dump出当前的内存
 * 堆转储快照以便事后分析
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/12
 */
public class HeapOOM {
    static class OOMObject {
        private String str;
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while(true){
            list.add(new OOMObject());
        }
    }
}

