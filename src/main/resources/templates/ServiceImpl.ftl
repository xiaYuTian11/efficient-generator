package ${package_name};

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

    @Override
    public ${table_name} save(${table_name}DTO dto) {
        ${table_name} entity = ${lower_table_name}Converter.dto2Entity(dto);
        boolean flag = this.save(entity);
        return entity;
    }

    @Override
    public ${table_name}VO findById(String id) {
        ${table_name} entity = this.getById(id);
        return ${lower_table_name}Converter.entity2Vo(entity);
    }

    @Override
    public Boolean update(${table_name}DTO dto) {
        return this.updateById(${lower_table_name}Converter.dto2Entity(dto));
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public Page<${table_name}> list(${table_name}ListDTO dto) {
        final Page<${table_name}> page = ${lower_table_name}Mapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), new QueryWrapper<>());
        return page;
    }

    /**
    * 导出
    */
    @Override
    public BaseExportVO export(${table_name}ListDTO dto) {
    BaseExportVO result = new BaseExportVO();
        // 查询数据
        dto.setPageNum(1);
        dto.setPageSize(Integer.MAX_VALUE);
        Page<${table_name}> page = this.list(dto);
        List<${table_name}> list = page.getRecords();
        // 添加序号
        AtomicInteger no = new AtomicInteger(1);
        List<${table_name}VO> dataList = list.stream().map(e -> {
            ${table_name}VO vo = ${lower_table_name}Converter.entity2Vo(e);
            // vo.setNo(no.getAndIncrement());
            return vo;
        }).collect(Collectors.toList());
        // 导出文件
        final Date currentDate = DateUtil.date();
        final String fileName = "替换文件名称.xlsx";
        final String targetFilePath = "替换生成目录" + "/" + fileName;
        final String templatePath = "替换模版文件夹目录" + "/替换模版名称.xlsx";
        EasyExcel.write(targetFilePath).withTemplate(templatePath).sheet().doFill(dataList);
        result.setUrl("导出文件相对目录" + "/" + fileName);
        result.setExportDate(currentDate);
        result.setName("替换导出文件名称.xlsx");
        return result;
    }
}
