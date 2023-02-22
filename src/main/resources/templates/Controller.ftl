package ${package_name};

import com.zenith.leader.common.result.Result;
import com.zenith.leader.common.log.Log;
import com.zenith.leader.common.log.OptTypeEnum;
import com.zenith.leader.common.auth.Permission;
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
@RequestMapping("/${lower_table_name}")
@Validated
@Api(tags = "${table_describe}")
@Permission
public class ${table_name}Controller {

    @Autowired
    private ${table_name}Service ${lower_table_name}Service;

    /**
    * 新增
    */
    @Log(optType = OptTypeEnum.INSERT, desc = "${table_describe}")
    @PostMapping("/save")
    @ApiOperation(value = "保存", response = Result.class)
    public Result save(@Validated @RequestBody ${table_name}DTO dto) {
        ${table_name} entity = ${lower_table_name}Service.save(dto);
        return Result.ok(entity);
    }

    /**
    * 详情
    */
    @Log(optType = OptTypeEnum.QUERY, desc = "${table_describe}")
    @GetMapping("/find")
    @ApiOperation(value = "详情", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识",dataTypeClass = String.class, required = true)
    })
    public Result find(@NotBlank(message = "id 不能为空") @RequestParam(name="id") String id) {
        ${table_name}VO entity = ${lower_table_name}Service.findById(id);
        return Result.ok(entity);
    }

    /**
    * 修改
    */
    @Log(optType = OptTypeEnum.UPDATE, desc = "${table_describe}")
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = Result.class)
    public Result update(@Validated @RequestBody ${table_name}DTO dto) {
        boolean flag = ${lower_table_name}Service.update(dto);
        return flag ? Result.ok() : Result.fail();
    }

    /**
    * 删除
    */
    @Log(optType = OptTypeEnum.DELETE, desc = "${table_describe}")
    @GetMapping("/delete")
    @ApiOperation(value = "删除", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "数据唯一标识",dataTypeClass = String.class, required = true)
    })
    public Result delete(@NotBlank(message = "id 不能为空") @RequestParam(name="id") String id) {
        boolean flag = ${lower_table_name}Service.delete(id);
        return flag ? Result.ok() : Result.fail();
    }

    /**
    * 列表
    */
    @Log(optType = OptTypeEnum.PAGE, desc = "${table_describe}")
    @PostMapping("/list")
    @ApiOperation(value = "列表", response = Result.class)
    public Result list(@Validated @RequestBody ${table_name}ListDTO dto) {
        return Result.ok(${lower_table_name}Service.list(dto));
    }
}
