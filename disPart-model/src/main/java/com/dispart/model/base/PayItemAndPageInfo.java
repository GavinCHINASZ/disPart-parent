package com.dispart.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class PayItemAndPageInfo {
   public List<PayItemManage> payItemManage;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   Integer tolPageNum;
}
