package com.hanhan.store.generated.mapper;

import com.hanhan.store.model.po.AppSettingClass;
import com.hanhan.store.model.po.AppSettingClassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppSettingClassMapper {
    long countByExample(AppSettingClassExample example);

    int deleteByExample(AppSettingClassExample example);

    int deleteByPrimaryKey(String id);

    /**
     * use {@link insertSelective} instead.
     */
    @Deprecated
    int insert(AppSettingClass record);

    int insertSelective(AppSettingClass record);

    List<AppSettingClass> selectByExampleWithBLOBs(AppSettingClassExample example);

    List<AppSettingClass> selectByExample(AppSettingClassExample example);

    AppSettingClass selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppSettingClass record, @Param("example") AppSettingClassExample example);

    int updateByExampleWithBLOBs(@Param("record") AppSettingClass record, @Param("example") AppSettingClassExample example);

    int updateByExample(@Param("record") AppSettingClass record, @Param("example") AppSettingClassExample example);

    int updateByPrimaryKeySelective(AppSettingClass record);

    int updateByPrimaryKeyWithBLOBs(AppSettingClass record);

    /**
     * use {@link updateByPrimaryKeySelective} instead.
     */
    @Deprecated
    int updateByPrimaryKey(AppSettingClass record);
}