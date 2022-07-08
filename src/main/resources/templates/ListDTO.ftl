package ${package_name};

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* <p>
* ${table_describe} 列表查询DTO
* </p>
*
* @author ${author}
* @date ${date}
*/
@Data
@ApiModel("${table_describe} 列表查询-${table_name}ListDTO")
public class ${table_name}ListDTO {
    private static final long serialVersionUID = ${serialVersionUID};
    @NotNull(message = "pageNum 不能为空")
    private Integer pageNum;
    @NotNull(message = "pageSize 不能为空")
    private Integer pageSize;
}
