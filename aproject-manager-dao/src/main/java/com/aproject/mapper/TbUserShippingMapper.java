package com.aproject.mapper;

import com.aproject.pojo.TbUserShipping;
import com.aproject.pojo.TbUserShippingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbUserShippingMapper {
    int countByExample(TbUserShippingExample example);

    int deleteByExample(TbUserShippingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbUserShipping record);

    int insertSelective(TbUserShipping record);

    List<TbUserShipping> selectByExample(TbUserShippingExample example);

    TbUserShipping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUserShipping record, @Param("example") TbUserShippingExample example);

    int updateByExample(@Param("record") TbUserShipping record, @Param("example") TbUserShippingExample example);

    int updateByPrimaryKeySelective(TbUserShipping record);

    int updateByPrimaryKey(TbUserShipping record);
}