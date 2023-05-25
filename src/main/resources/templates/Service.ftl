package ${package_name};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${dto_package_name}.${table_name}DTO;
import ${dto_package_name}.${table_name}ListDTO;
import ${entity_package_name}.${table_name};
import ${vo_package_name}.${table_name}VO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* <p>
* ${table_describe} 服务Api
* </p>
*
* @author ${author}
* @date ${date}
*/
public interface ${table_name}Service extends IService<${table_name}> {
    /***
    * 新增
    */
    ${table_name} save(${table_name}DTO dto);

    /**
    * 详情
    */
    ${table_name}VO findById(String id);

    /**
    * 修改
    */
    Boolean update(${table_name}DTO dto);

    /**
    * 删除
    */
    Boolean delete(String id);

    /**
    * 列表查询
    */
    Page<${table_name}> list(${table_name}ListDTO dto);

    /**
    * 导出
    */
    Object export(${table_name}ListDTO dto);
}
