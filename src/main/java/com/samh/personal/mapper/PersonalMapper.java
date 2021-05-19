package com.samh.personal.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author WANKAI
 * @Email 908780582@qq.com
 * @Date 2021/4/13 18:50
 */
@Mapper
@Repository
public interface PersonalMapper {
    String one(@Param("id") String id);
}
