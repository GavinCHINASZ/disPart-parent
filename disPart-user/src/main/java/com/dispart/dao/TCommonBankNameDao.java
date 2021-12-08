package com.dispart.dao;

import com.dispart.vo.user.TCommonBankNameVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 银行常用列表
 * TCommonBankNameDao 表数据库访问层
 *
 * @author 黄贵川
 * @since 2021-08-18
 */
@Mapper
public interface TCommonBankNameDao {
    /**
     * 常用银行信息查询
     *
     * @author 黄贵川
     * @date 2021/08/18
     * @return TCommonBankNameVo 银行常用列表
     */
    List<TCommonBankNameVo> selectTCommonBankNameVoList();

    /**
     * 查询总条数
     *
     * @author 黄贵川
     * @date 2021/08/18
     * @return int
     */
    int selectCount();
}