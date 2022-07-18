package top.tanmw.generator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static top.tanmw.generator.ProjectPattern.MULTI;

/**
 * @author TMW
 * @since 2022/2/28 15:15
 */
public class Generator {

    public static void main(String[] args) throws Exception {
        final String url = Generator.class.getClassLoader().getResource("generator.txt").getPath().toString();
        run(url);
    }

    public static void run(String url) throws Exception {
        final Properties properties = getProperties(url);
        GeneratorModel model = new GeneratorModel();
        model.setUrl(properties.getProperty("url"));
        model.setDriver(properties.getProperty("driver"));
        model.setUser(properties.getProperty("user"));
        model.setPassword(properties.getProperty("password"));
        model.setDbName(properties.getProperty("dbName"));
        model.setShowTablesSql(properties.getProperty("showTablesSql"));
        model.setShowTablesCommentSql(properties.getProperty("showTablesCommentSql"));
        model.setBasePath(properties.getProperty("basePath"));
        if (StrUtil.isBlank(model.getBasePath())) {
            String sp1 = System.getProperty("user.dir");
            model.setBasePath(sp1);
        }
        model.setDelete(properties.getProperty("isDelete"));
        model.setDeleteLevel(properties.getProperty("deleteLevel"));

        model.setModuleName(properties.getProperty("moduleName"));
        if (StrUtil.isBlank(model.getModuleName())) {
            String basePath = model.getBasePath();
            File file = new File(basePath);
            String name = file.getName();
            model.setModuleName(name);
        }
        model.setPackageName(properties.getProperty("packageName"));
        if (StrUtil.isBlank(model.getPackageName())) {
            model.setPackageName(model.getModuleName());
        }
        model.setPattern(properties.getProperty("pattern"));
        if (StrUtil.isBlank(model.getPattern())) {
            model.setPattern(MULTI.getDesc());
        }

        model.setExcludePrefix(properties.getProperty("excludePrefix"));
        model.setIncludeSet(properties.getProperty("includeSet"));
        model.setExcludeSet(properties.getProperty("excludeSet"));
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
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        codeGenerateUtils.init(model);
        codeGenerateUtils.generate();
    }

    public static Properties getProperties(String url) throws Exception {
        Properties properties = new Properties();
        File file = new File(url);
        InputStream in = new FileInputStream(file);
        properties.load(in);
        return properties;
    }

}
