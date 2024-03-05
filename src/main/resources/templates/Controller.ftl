package ${package_name};

import com.efficient.common.result.Result;
import com.efficient.common.permission.Permission;
import com.efficient.logs.annotation.Log;
import com.efficient.logs.constant.LogEnum;
import ${api_package_name}.${table_name}Service;
import ${dto_package_name}.${table_name}DTO;
import ${dto_package_name}.${table_name}ListDTO;
import ${entity_package_name}.${table_name};
import ${vo_package_name}.${table_name}VO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
* <p>
* ${table_describe} controller 层
* </p>
*
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("${suffixPath}/${lower_table_name}")
@Validated
@Api(tags = "${table_describe}")
@Permission
public class ${table_name}Controller {

    @Autowired
    private ${table_name}Service ${lower_table_name}Service;

<#if crud = true >


    /**
    * 新增
    */
    @Log(logOpt = LogEnum.SAVE, module = "${table_describe}")
    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public Result<${table_name}> save(@Validated @RequestBody ${table_name}DTO dto) {
        return ${lower_table_name}Service.save(dto);
    }

    /**
    * 详情
    */
    @Log(logOpt = LogEnum.QUERY, module = "${table_describe}")
    @GetMapping("/find")
    @ApiOperation(value = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<${table_name}VO> find(@NotBlank(message = "id 不能为空") @RequestParam(name="id") String id) {
        return ${lower_table_name}Service.findById(id);
    }

    /**
    * 修改
    */
    @Log(logOpt = LogEnum.UPDATE, module = "${table_describe}")
    @PostMapping("/update")
    @ApiOperation(value = "修改")
    public Result<Boolean> update(@Validated @RequestBody ${table_name}DTO dto) {
        return ${lower_table_name}Service.update(dto);
    }

    /**
    * 删除
    */
    @Log(logOpt = LogEnum.DELETE, module = "${table_describe}")
    @GetMapping("/delete")
    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识", required = true)
    })
    public Result<Boolean> delete(@NotBlank(message = "id 不能为空") @RequestParam(name="id") String id) {
        return ${lower_table_name}Service.delete(id);
    }

    /**
    * 列表
    */
    @Log(logOpt = LogEnum.PAGE, module = "${table_describe}")
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = Result.class)
    public Result list(@Validated @RequestBody ${table_name}ListDTO dto) {
        return Result.ok(${lower_table_name}Service.list(dto));
    }

</#if>
}
