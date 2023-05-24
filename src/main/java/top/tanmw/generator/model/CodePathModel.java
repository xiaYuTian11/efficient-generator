package top.tanmw.generator.model;

import lombok.Builder;
import lombok.Data;

/**
 * 自定义code路径配置类
 *
 * @author TMW
 * @since 2023/5/23 17:32
 */
@Data
@Builder
public class CodePathModel {

    /**
     * 工程基础文件路径，默认取配置文件里面的路径，此处不需要配置
     */
    private String basePath;
    /**
     * controller 文件路径
     */
    private String controllerPath;
    /**
     * controller 包名，自动生成，无需配置
     */
    private String controllerPackageName;
    /**
     * api 文件路径
     */
    private String apiPath;
    /**
     * api 包名，自动生成，无需配置
     */
    private String apiPackageName;
    /**
     * service 文件路径
     */
    private String servicePath;
    /**
     * service 包名，自动生成，无需配置
     */
    private String servicePackageName;
    /**
     * dao  文件路径
     */
    private String daoPath;
    /**
     * dao 包名，自动生成，无需配置
     */
    private String daoPackageName;
    /**
     * Entity 文件路径
     */
    private String modelEntityPath;
    /**
     * Entity 包名，自动生成，无需配置
     */
    private String modelEntityPackageName;
    /**
     * Converter 文件路径
     */
    private String modelConverterPath;
    /**
     * Converter 包名，自动生成，无需配置
     */
    private String modelConverterPackageName;
    /**
     * Dto 文件路径
     */
    private String modelDtoPath;
    /**
     * Dto 包名，自动生成，无需配置
     */
    private String modelDtoPackageName;
    /**
     * Vo 文件路径
     */
    private String modelVoPath;
    /**
     * Vo 包名，自动生成，无需配置
     */
    private String modelVoPackageName;
    /**
     * mapper 文件路径
     */
    private String mapperPath;

}
