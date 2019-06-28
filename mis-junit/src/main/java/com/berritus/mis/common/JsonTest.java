package com.berritus.mis.common;

import com.alibaba.fastjson.JSON;
import com.berritus.mis.bean.school.TbStudent;
import org.junit.Test;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: 2019/6/28
 */
public class JsonTest {

    /**
     * @Description: json字符串转成对象
     * @Date: 2019/6/28
     * @Author: Qin Guihe
     *
     */
    @Test
    public void jsonStrToObject() {
        TbStudent tbStudent = new TbStudent();
        tbStudent.setStuName("hello world");
        tbStudent.setAge(21);
        tbStudent.setId(100001);

        String jsonStr = JSON.toJSONString(tbStudent);

        TbStudent tbStudent2 = JSON.parseObject(jsonStr, TbStudent.class);

        System.out.println(tbStudent2.getStuName());
    }

    /**
     * @Description: 对象转成json字符串
     * @Date: 2019/6/28
     * @Author: Qin Guihe
     *
     */
    @Test
    public void objectToJsonStr() {
        TbStudent tbStudent = new TbStudent();
        tbStudent.setStuName("hello world");
        tbStudent.setAge(21);
        tbStudent.setId(100001);

        String jsonStr = JSON.toJSONString(tbStudent);

        System.out.println(jsonStr);
    }
}
