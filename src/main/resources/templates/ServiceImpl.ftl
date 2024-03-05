package ${package_name};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.efficient.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${api_package_name}.${table_name}Service;
import ${converter_package_name}.${table_name}Converter;
import ${dao_package_name}.${table_name}Mapper;
import ${dto_package_name}.${table_name}DTO;
import ${dto_package_name}.${table_name}ListDTO;
import ${entity_package_name}.${table_name};
import ${vo_package_name}.${table_name}VO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* <p>
* ${table_describe} 服务实现类
* </p>
*
* @author ${author}
* @date ${date}
*/
@Service
public class ${table_name}ServiceImpl extends ServiceImpl<${table_name}Mapper, ${table_name}> implements ${table_name}Service {

    @Autowired
    private ${table_name}Converter ${lower_table_name}Converter;
    @Autowired
    private ${table_name}Mapper ${lower_table_name}Mapper;
<#if crud = true >
    @Override
    public Result<${table_name}> save(${table_name}DTO dto) {
        ${table_name} entity = ${lower_table_name}Converter.dto2Entity(dto);
        boolean flag = this.save(entity);
        return Result.ok(entity);
    }

    @Override
    public Result<${table_name}VO> findById(String id) {
        ${table_name} entity = this.getById(id);
        ${table_name}VO vo = ${lower_table_name}Converter.entity2Vo(entity);
        return Result.ok(vo);
    }

    @Override
    public Result<Boolean> update(${table_name}DTO dto) {
        boolean flag = this.updateById(${lower_table_name}Converter.dto2Entity(dto));
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Result<Boolean> delete(String id) {
        boolean flag = this.removeById(id);
        return flag ? Result.ok() : Result.fail();
    }

    @Override
    public Page<${table_name}> list(${table_name}ListDTO dto) {
        LambdaQueryWrapper<${table_name}> queryWrapper = new LambdaQueryWrapper<>(${table_name}.class);
        final Page<${table_name}> page = ${lower_table_name}Mapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), queryWrapper);
        return page;
    }
</#if>
}
