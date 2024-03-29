package top.tanmw.generator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import top.tanmw.generator.model.CodePathModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static top.tanmw.generator.ProjectPattern.MULTI;

/**
 * @author TMW
 * @since 2022/2/28 15:15
 */
public class Generator {

    // public static void main(String[] args) throws Exception {
    //     String url = Generator.class.getClassLoader().getResource("generator.properties").getPath().toString();
    //
    //     GeneratorModel model = init(url);
    //     CodePathModel build = CodePathModel.builder()
    //             .controllerPath("tanmw-web/src/main/java/top/tanmw/web/controller")
    //             .apiPath("tanmw-api/src/main/java/top/tanmw/front/api")
    //             .servicePath("tanmw-station/src/main/java/top/tanmw/front/station/service")
    //             .daoPath("tanmw-dao/src/main/java/top/tanmw/dao/mapper")
    //             .modelEntityPath("tanmw-model/src/main/java/top/tanmw/model/bean")
    //             .modelConverterPath("tanmw-model/src/main/java/top/tanmw/model/converter")
    //             .modelDtoPath("tanmw-model/src/main/java/top/tanmw/model/dto")
    //             .modelVoPath("tanmw-model/src/main/java/top/tanmw/model/vo")
    //             .mapperPath("tanmw-dao/src/main/java/top/tanmw/dao/mapper/xml")
    //             .build();
    //     model.setCodePathModel(build);
    //     generate(model);
    // }

    public static GeneratorModel init(String url) throws Exception {
        final Properties properties = getProperties(url);
        GeneratorModel model = new GeneratorModel();
        model.setUrl(properties.getProperty("url"));
        model.setDriver(properties.getProperty("driver"));
        model.setUser(properties.getProperty("user"));
        model.setPassword(properties.getProperty("password"));
        model.setDbName(properties.getProperty("dbName"));
        model.setTemplatePath(properties.getProperty("templatePath"));
        model.setAuthor(properties.getProperty("author"));
        model.setShowTablesSql(properties.getProperty("showTablesSql"));
        model.setShowTablesCommentSql(properties.getProperty("showTablesCommentSql"));
        model.setBasePath(properties.getProperty("basePath"));
        model.setSuffixPath(properties.getProperty("suffixPath"));
        if (StrUtil.isBlank(model.getBasePath())) {
            String sp1 = System.getProperty("user.dir");
            model.setBasePath(sp1);
        }
        model.setProjectName(properties.getProperty("projectName"));
        if (StrUtil.isBlank(model.getProjectName())) {
            model.setProjectName("zenith");
        }
        model.setPackageName(properties.getProperty("packageName"));
        if (StrUtil.isBlank(model.getPackageName())) {
            model.setPackageName(model.getProjectName());
        }
        model.setPattern(properties.getProperty("pattern"));
        if (StrUtil.isBlank(model.getPattern())) {
            model.setPattern(MULTI.getDesc());
        }
        model.setExcludePrefix(properties.getProperty("excludePrefix"));
        model.setReplacePrefix(properties.getProperty("replacePrefix"));
        model.setIncludePrefix(properties.getProperty("includePrefix"));
        model.setIncludeSet(properties.getProperty("includeSet"));
        model.setIncludeSetComment(properties.getProperty("includeSetComment"));
        model.setExcludeSet(properties.getProperty("excludeSet"));
        model.setTableLogic(properties.getProperty("tableLogic"));
        model.setCrud(properties.getProperty("crud"));
        model.setTableFieldInsert(properties.getProperty("tableFieldInsert"));
        model.setTableFieldUpdate(properties.getProperty("tableFieldUpdate"));
        model.setTableFieldInsertUpdate(properties.getProperty("tableFieldInsertUpdate"));
        model.setReplace(false);
        if (StrUtil.isNotBlank(properties.getProperty("replace"))) {
            model.setReplace(Boolean.parseBoolean(properties.getProperty("replace")));
        }
        String fileType = properties.getProperty("fileType");
        List<Integer> list = new ArrayList<>();
        if (StrUtil.isBlank(fileType)) {
            for (int i = 0; i < 20; i++) {
                list.add(i);
            }
        } else {
            final String[] split = fileType.split(",");
            for (String str : split) {
                if (!str.contains("-") && NumberUtil.isNumber(str)) {
                    list.add(Integer.parseInt(str));
                } else {
                    final String[] split1 = str.split("-");
                    if (split1.length == 2 && NumberUtil.isNumber(split1[0]) && NumberUtil.isNumber(split1[1])) {
                        for (int y = Integer.parseInt(split1[0]); y <= Integer.parseInt(split1[1]); y++) {
                            list.add(y);
                        }
                    }
                }
            }
        }
        model.setFileType(list);
        return model;
    }

    public static Properties getProperties(String url) throws Exception {
        Properties properties = new Properties();
        File file = new File(url);
        InputStreamReader in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        properties.load(in);
        return properties;
    }

    public static void generate(GeneratorModel model) throws Exception {
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        codeGenerateUtils.init(model);
        codeGenerateUtils.initTableMapName();
        FreeMarkerTemplateUtils.init(model.getTemplatePath());
        codeGenerateUtils.generate();
    }

}
