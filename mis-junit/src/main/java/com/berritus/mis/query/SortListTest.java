package com.berritus.mis.query;

import cn.hutool.core.math.MathUtil;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @Copyright: mis520
 * @Author: Qin Guihe
 * @Date: Create in 2019/5/29
 */
public class SortListTest {

    class SortBean {
        private int sort;
        private String beanName;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }
    }

    @Test
    public void test1() {
        List<SortBean> listSortBeans = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int num = random.nextInt(1000);

            SortBean sortBean = new SortBean();
            sortBean.setSort(num);
            listSortBeans.add(sortBean);
        }

        Collections.sort(listSortBeans, new Comparator<SortBean>() {
            @Override
            public int compare(SortBean o1, SortBean o2) {
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                } else if (o1.getSort() < o2.getSort()) {
                    return -1;
                }

                return 0;
            }
        });

        for (SortBean sortBean : listSortBeans) {
            System.out.println(sortBean.getSort());
        }
    }
}
