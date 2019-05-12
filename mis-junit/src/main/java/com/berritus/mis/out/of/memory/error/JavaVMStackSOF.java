package com.berritus.mis.out.of.memory.error;

/**
 * @Description: VM Args: -Xss128k
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/12
 */
public class JavaVMStackSOF {
    private int stackLenth = -1;

    public void stackLeak() {
        stackLenth++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e){
            System.out.println("stack lenth " + oom.stackLenth);
            throw e;
        }
    }
}
