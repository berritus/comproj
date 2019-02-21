package com.berritus.mis.dao.school;

import com.berritus.mis.bean.school.TbStudent;
import com.berritus.mis.core.dao.MisDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbStudentMapper extends MisDao<TbStudent, Integer> {

}