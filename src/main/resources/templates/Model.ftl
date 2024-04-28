package ${package_name};

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* ${table_describe} 实体类
* </p>
*
* @author ${author}
* @date ${date}
*/
@Data
@TableName("${table_name_small}")
@ApiModel("${table_describe}")
public class ${table_name} implements Serializable {

    private static final long serialVersionUID = ${serialVersionUID};

<#if model_column??>
<#list model_column as model>
    /**
    *${model.columnComment!}
    */
    @ApiModelProperty(value = "${model.columnComment!}")
    <#if (model.primaryKey = true)>
    @TableId(value = "${model.columnName}")
    <#else>
        <#if tableLogic?has_content && model.columnName == tableLogic>
    @TableLogic
    @TableField("${model.columnName}")
        <#elseif tableFieldInsertList?has_content && tableFieldInsertList?seq_contains(model.columnName)>
    @TableField(value = "${model.columnName}", fill = FieldFill.INSERT)
        <#elseif tableFieldUpdateList?has_content && tableFieldUpdateList?seq_contains(model.columnName)>
    @TableField(value = "${model.columnName}", fill = FieldFill.UPDATE)
        <#elseif tableFieldInsertUpdateList?has_content && tableFieldInsertUpdateList?seq_contains(model.columnName)>
    @TableField(value = "${model.columnName}", fill = FieldFill.INSERT_UPDATE)
        <#else>
    @TableField("${model.columnName}")
        </#if>
    </#if>
    <#if (model.columnType = 'varchar' || model.columnType = 'text' || model.columnType = 'varchar2'
    || model.columnType = 'clob' || model.columnType = 'char' || model.columnType = 'bpchar'
    || model.columnType = 'longvarchar')>
    private String ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'timestamp' ||  model.columnType = 'date'  ||  model.columnType = 'datetime'>
    private Date ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'smallint' || model.columnType = 'int'  || model.columnType = 'int2'
    || model.columnType = 'int4' || model.columnType = 'integer' || model.columnType = 'bit'>
    private Integer ${model.changeColumnName?uncap_first};
    </#if>
    <#if (model.columnType = 'bigint' || model.columnType = 'int8' || model.columnType = 'bigserial')>
    private Long ${model.changeColumnName?uncap_first};
    </#if>
    <#if (model.columnType = 'binary' || model.columnType = 'blob' ||model.columnType = 'longblob')>
    private byte[] ${model.changeColumnName?uncap_first};
    </#if>
    <#if model.columnType = 'numeric' ||  model.columnType = 'decimal' ||  model.columnType = 'number'>
    private java.math.BigDecimal ${model.changeColumnName?uncap_first};
    </#if>
</#list>
</#if>
}
