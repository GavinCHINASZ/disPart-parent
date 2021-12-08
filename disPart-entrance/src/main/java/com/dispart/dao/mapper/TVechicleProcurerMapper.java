package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0229addInDto;
import com.dispart.dto.entrance.D_0230addInDto;
import com.dispart.dto.entrance.D_0231upInDto;
import com.dispart.dto.entrance.D_0234findInDto;
import com.dispart.dto.entrance.D_0235UpInDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.dto.entrance.D_0503FindDto;
import com.dispart.dto.entrance.QuryEntranceCheckParamOutDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckInDto;
import com.dispart.model.EmployeeInfo;
import com.dispart.vo.commons.TRoleMenuInfo;
import com.dispart.vo.entrance.D_0503OutVO;
import com.dispart.vo.entrance.TProductTypeInfo;
import com.dispart.vo.entrance.TVechicleProcurer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//车辆进出场管理表
@Mapper
public interface TVechicleProcurerMapper {
    /**
     * 添加大车进场记录
     *
     * @param record the record
     * @return insert count
     */
    int insert(D_0229addInDto record);


    /**
     * 无人工进场
     *
     * @param tVechicleProcurer
     * @return
     */
    int insertByNoBody(TVechicleProcurer tVechicleProcurer);
    /**
     * 添加空车进场记录
     *
     * @param record
     * @return
     */
    int insertMin(D_0230addInDto record);


    //获取序列号
    Integer findNextval(@Param("seqName") String seqName);

    //收款后变更记录状态
    int upVeStByPrimaryKey(D_0231upInDto record);

    //查询进场记录
    TVechicleProcurer findInVe(D_0234findInDto record);

    //变更进场记录
    int updateByPrimaryKey(D_0235UpInDto record);

    /**
     * select by primary key
     *
     * @param inId primary key
     * @return object by primary key
     */
    TVechicleProcurer selectByPrimaryKey(String inId);

    /**
     * 根扰条件查询进出场信息
     *
     * @param record
     * @return
     */
    List<QuryEntranceCheckParamOutDto> selectWhere(QuryEntranceVeCheckInDto record);

    /**
     * 根扰inId查询进出场信息
     *
     * @param record
     * @return
     */
    List<QuryEntranceCheckParamOutDto> selectWhereByInId(QuryEntranceVeCheckInDto record);

    /**
     * 根扰条件查询进出场信息总箱条数
     *
     * @param record
     * @return
     */
    int selectWhereCount(QuryEntranceVeCheckInDto record);

    /**
     * 根扰条件inId查询进出场信息总条数
     *
     * @param record
     * @return
     */
    int selectByInIdCount(QuryEntranceVeCheckInDto record);
    /**
     * 更新进声场信息表
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TVechicleProcurer record);

    /**
     * 查询业务人员办理进出场信息根据数量
     */
    int selectInOutCount(QuryEntranceVeCheckInDto record);

    /**
     * 查询业务人员办理进出场信息根据数量
     *
     * @param record
     * @return
     */
    List<QuryEntranceCheckParamOutDto> selectInOutWhere(QuryEntranceVeCheckInDto record);

    /**
     * 查询业务人员办理进出场信息根据数量根据进出场id
     *
     * @param record
     * @return
     */
    List<QuryEntranceCheckParamOutDto> selectInOutByInid(QuryEntranceVeCheckInDto record);
    /**
     * 根据员工id或时间条件查询
     *
     * @param record
     * @return
     */
    int selcetInOutOrValueCount(QuryEntranceVeCheckInDto record);

    /**
     * 根据员工id或时间条件查询
     *
     * @param record
     * @return
     */
    List<QuryEntranceCheckParamOutDto> selcetInOutOrValueWhere(QuryEntranceVeCheckInDto record);

    /**
     * 根据出场口编号条件查询
     *
     * @param outNum
     * @return
     */
    TVechicleProcurer selectByOutNum(String outNum);

    /**
     * 补贴申请--货车/空车 没有补贴申请过人进出场
     *
     * @param body D_0297FindDto
     * @return List<D_0297FindDto>
     * @author 黄贵川
     * @date 2021/08/24
     */
    List<D_0297FindDto> findEntranceMessage(D_0297FindDto body);

    /**
     * 补贴申请--货车/空车 没有补贴申请过人进出场
     *
     * @param body D_0297FindDto
     * @return Integer
     * @author 黄贵川
     * @date 2021/08/24
     */
    Integer findEntranceMessageCount(D_0297FindDto body);

    /**
     * update 更新进出场信息，状态为未核验
     * @param record
     * @return Integer
     * @author zhongfei
     * @date 2021/08/24
     */
    int updateByPrimaryKeySelectiveCheck(TVechicleProcurer record);

    /**
     * 根据车牌号条件查询
     *
     * @param vehicleNum
     * @return
     */
    TVechicleProcurer findInVeByVehicleNum(@Param("vehicleNum") String vehicleNum);

    /**
     * 查询TRoleMenuInfo
     *
     * @author 黄贵川
     * @date 2021-10-21
     * @param body D_0297FindDto
     * @return TRoleMenuInfo
     */
    TRoleMenuInfo findRoleMenuInfo(D_0297FindDto body);

    /**
     * 查询员工
     *
     * @author 黄贵川
     * @date 2021-10-21
     * @param body D_0297FindDto
     * @return EmployeeInfo
     */
    EmployeeInfo findEmployeeInfo(D_0297FindDto body);

    /**
     * 查询品类
     *
     * @return TProductTypeInfo
     * @author 黄贵川
     * @date 2021/20/25
     */
    List<TProductTypeInfo> findProductTypeInfo();

    /**
     * 进出场管理-进出场停车费查询
     * @param dto
     * @return
     */
    List<D_0503OutVO> getParkingInfo(D_0503FindDto dto);

    /**
     * 进出场管理-进出场停车费查询数量
     * @param dto
     * @return
     */
    Integer getParkingInfoCount(D_0503FindDto dto);

    String[] findVechicleProcurerInId(D_0297FindDto body);


}
