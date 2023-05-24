package top.tanmw.generator.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author TMW
 * @since 2023/5/23 17:32
 */
@Data
@Builder
public class CodePathModel {

    private String basePath;
    private String controllerPath;
    private String controllerPackageName;
    private String apiPath;
    private String apiPackageName;
    private String servicePath;
    private String servicePackageName;
    private String daoPath;
    private String daoPackageName;
    private String modelEntityPath;
    private String modelEntityPackageName;
    private String modelConverterPath;
    private String modelConverterPackageName;
    private String modelDtoPath;
    private String modelDtoPackageName;
    private String modelVoPath;
    private String modelVoPackageName;
    private String mapperPath;

}
