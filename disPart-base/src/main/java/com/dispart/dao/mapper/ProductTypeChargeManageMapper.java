package com.dispart.dao.mapper;

import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.model.base.OrgIdAndDepId;
import com.dispart.model.base.ProductTypeChargeManage;
import com.dispart.vo.basevo.OrganizationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductTypeChargeManageMapper {
    int insertProductTypeCharge(ProductTypeChargeManage proTypeChgeManage);
    int updateProductTypeChargeByVarietyNo(@Param("varietyNo") String varietyNo, @Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> findProductTypeChargeByVarietyNoOrProductNm(@Param("varietyNo") String varietyNo, @Param("productNm") String productNm);
    int deleteProductTypeChargeByVarietyNo(@Param("varietyNo") String varietyNo);
    List<ProductTypeChargeManage> findProductTypeChargeByProductNm(@Param("varietyNo") String varietyNo,@Param("prdctNm") String prdctNm);
    List<ProductTypeChargeManage> selectAll(@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> selectAllTest(@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> selectParentId(@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> selectAllTestByLevel(@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> selectPreVno(@Param("varietyNo") String varietyNo);
    List<ProductTypeChargeManage> selectVnoByNm(@Param("prdctNm") String prdctNm);
    List<ProductTypeChargeManage> selectbynm(@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
   /* List<DISP20210019DepFindByParamInDto> selectDep(@Param("depId") String depId);
    List<OrganizationVo> selectOrgId(@Param("orgId") String orgId);*/
    List<OrgIdAndDepId> selectPreOrgIdAndDepId(@Param("preproductNmNo") String preproductNmNo);
    Integer updateSubDepIdAndOrgId(@Param("varietyNo") String varietyNo,@Param("proTypeChgeManage") ProductTypeChargeManage proTypeChgeManage);
    List<ProductTypeChargeManage> selectVnoByPId(@Param("varietyNo") String varietyNo);
}
