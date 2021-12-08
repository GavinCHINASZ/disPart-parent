package com.dispart.controller;

import com.dispart.model.base.CarBlackList;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CarBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class CarBlackListController {
    @Autowired
    CarBlackListService carBlackListService;
    @PostMapping("DISP20210358")
    public Result inserCarBlackList(@RequestBody Request<CarBlackList> carBlackList){
        return carBlackListService.inserCarBlackList(carBlackList.getBody(),carBlackList.getHead().getOperator());
    }
    @PostMapping("DISP20210359")
    Result deteCarBlackList(@RequestBody Request<CarBlackList> carBlackList){
        return carBlackListService.deteCarBlackList(carBlackList.getBody());
    };
    @PostMapping("DISP20210360")
    public Result updateCarBlackList(@RequestHeader String userNo,@RequestBody Request<CarBlackList> carBlackList){
        carBlackList.getBody().setModifyId(userNo);
        return carBlackListService.updateCarBlackList(carBlackList.getBody(), carBlackList.getHead().getOperator());
    }
    @PostMapping("DISP20210362")
    public Result selectCarBlackList(@RequestBody Request<CarBlackList> carBlackList){
        return carBlackListService.selectCarBlackList(carBlackList.getBody());

    }
}
