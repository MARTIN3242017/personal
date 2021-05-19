package com.samh.personal.controller;

import com.samh.personal.common.exception.ResultVO;
import com.samh.personal.service.PersonalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description xxx
 * @Author WANKAI
 * @Date 2021/4/13 18:50
 */

@Slf4j
@Api(tags = "xxx")
@RestController
@RequestMapping("/persona")
public class PersonalController {
    @Resource
    private PersonalService personalService;

    @ApiOperation(httpMethod = "POST", value = "xxx", response = Object.class)
    @PostMapping(value = "/add")
    public ResultVO queryOneWidget(@RequestBody Object dto) {
        return ResultVO.success();
    }

    @ApiOperation(httpMethod = "GET", value = "xxx", response = Object.class)
    @GetMapping(value = "/one")
    public ResultVO calculationAndDraft(@RequestParam String id) {
        return ResultVO.success();
    }

}
