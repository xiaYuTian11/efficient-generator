package ${package_name};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${entity_package_name}.${table_name};
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
* ${table_describe} 持久层
* </p>
*
* @author ${author}
* @date ${date}
*/
@Mapper
public interface ${table_name}Mapper extends BaseMapper<${table_name}> {


}
