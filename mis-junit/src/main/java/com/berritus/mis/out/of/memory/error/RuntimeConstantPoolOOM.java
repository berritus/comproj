package com.berritus.mis.out.of.memory.error;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/12
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            System.out.println(i);
            list.add(String.valueOf(i++).intern());
        }
    }
}
