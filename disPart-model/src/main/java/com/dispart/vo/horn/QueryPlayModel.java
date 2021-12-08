package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/3/1 14:27
 * @ Description:
 */
public class QueryPlayModel extends BaseModel {
    /**操作类型： bind*/
    private String action;
    /**播报交易流水号*/
    private String queryTranceId;
    /**全局唯一的参考ID*/
    private String queryReferenceId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getQueryTranceId() {
        return queryTranceId;
    }

    public void setQueryTranceId(String queryTranceId) {
        this.queryTranceId = queryTranceId;
    }

    public String getQueryReferenceId() {
        return queryReferenceId;
    }

    public void setQueryReferenceId(String queryReferenceId) {
        this.queryReferenceId = queryReferenceId;
    }
}
