package com.hanhan.store.generated.mapper;

import com.hanhan.store.model.po.AppSetting;
import com.hanhan.store.model.po.AppSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppSettingMapper {
    long countByExample(AppSettingExample example);

    int deleteByExample(AppSettingExample example);

    int deleteByPrimaryKey(String id);

    /**
     * use {@link insertSelective} instead.
     */
    @Deprecated
    int insert(AppSetting record);

    int insertSelective(AppSetting record);

    List<AppSetting> selectByExample(AppSettingExample example);

    AppSetting selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AppSetting record, @Param("example") AppSettingExample example);

    int updateByExample(@Param("record") AppSetting record, @Param("example") AppSettingExample example);

    int updateByPrimaryKeySelective(AppSetting record);

    /**
     * use {@link updateByPrimaryKeySelective} instead.
     */
    @Deprecated
    int updateByPrimaryKey(AppSetting record);
}