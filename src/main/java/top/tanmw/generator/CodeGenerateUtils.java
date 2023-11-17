package top.tanmw.generator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.Template;
import top.tanmw.generator.db.DbFactory;
import top.tanmw.generator.db.DbQuery;
import top.tanmw.generator.model.CodePathModel;
import top.tanmw.generator.util.PackageUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static top.tanmw.generator.PathConstants.*;
import static top.tanmw.generator.ProjectPattern.*;

/**
 * 代码生成器启动类，Generate.java暂时未作使用
 *
 * @author TMW
 * @date 2021/2/25 17:45
 */
public class CodeGenerateUtils {

    private final List<ColumnClass> columnClassList = new ArrayList<>();
    private String url;
    private String driver;
    private String user;
    private String password;
    private String basePath;
    private String projectName;
    private String author;
    private String packageName;
    // 优先
    private Set<String> includeSet;
    private Set<String> includeSetComment;
    private Map<String, String> includeMapName;
    private Set<String> excludeSet;
    private Set<String> excludePrefix;
    private String basePackageName;
    private String basePackagePath;
    private String baseControllerPath;
    private String baseApiPath;
    private String baseServicePath;
    private String baseDaoPath;
    private String baseModelPath;
    private String baseMapperPath;
    private DbQuery dbQuery;
    private Boolean isReplace;
    private ProjectPattern projectPattern;
    /**
     * 表名
     */
    private String tableName;
    private List<Integer> fileType;
    /**
     * 主键名称
     */
    private String primaryKeyColumnName;
    /**
     * 主键字段
     */
    private String primaryKeyFieldName;
    private String primaryKeyFieldMethodName;
    /**
     * 表对应的类名，表名转驼峰首字母大写
     */
    private String changeTableName;
    /**
     * 模块描述
     */
    private String tableDescribe;
    /**
     * 前缀
     */
    private String includePrefix;
    /**
     * 是否自定义路径
     */
    private boolean isCustomPath;
    /**
     * 模型路径
     */
    private CodePathModel codePathModel;

    public void initTableMapName() {
        includeMapName = new HashMap<>();
        if (CollUtil.isNotEmpty(includeSet) && CollUtil.isNotEmpty(includeSetComment) && includeSet.size() == includeSetComment.size()) {
            AtomicInteger count = new AtomicInteger(0);
            includeSet.forEach(table -> includeMapName.put(table, includeSetComment.toArray(new String[]{})[count.getAndIncrement()]));
        }
    }

    public Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }

    public void init(GeneratorModel generatorModel) {
        url = generatorModel.getUrl();
        driver = generatorModel.getDriver();
        user = generatorModel.getUser();
        password = generatorModel.getPassword();

        basePath = generatorModel.getBasePath();
        includePrefix = generatorModel.getIncludePrefix();
        projectName = generatorModel.getProjectName();
        packageName = generatorModel.getPackageName();
        includeSet = generatorModel.getIncludeSet();
        includeSetComment = generatorModel.getIncludeSetComment();
        excludeSet = generatorModel.getExcludeSet();
        excludePrefix = generatorModel.getExcludePrefix();
        isReplace = generatorModel.isReplace();
        fileType = generatorModel.getFileType();
        author = generatorModel.getAuthor();
        if (StrUtil.isBlank(author)) {
            author = AUTHOR;
        }
        // basePackageName = PROJECT_PREFIX + packageName;
        basePackageName = packageName;
        basePackagePath = basePackageName.replaceAll("\\.", "/");
        projectPattern = ProjectPattern.getPattern(generatorModel.getPattern());
        if (Objects.equals(SINGLE, projectPattern)) {
            // 单工程
            baseControllerPath = JAVA_PREFIX + basePackagePath;
            baseApiPath = JAVA_PREFIX + basePackagePath;
            baseServicePath = JAVA_PREFIX + basePackagePath;
            baseDaoPath = JAVA_PREFIX + basePackagePath;
            baseModelPath = JAVA_PREFIX + basePackagePath;
            baseMapperPath = RESOURCES_PREFIX;
        } else if (Objects.equals(MULTI, projectPattern)) {
            // 多模块
            baseControllerPath = projectName + RAIL + WEB + JAVA_PREFIX + basePackagePath;
            baseApiPath = projectName + RAIL + API + JAVA_PREFIX + basePackagePath;
            baseServicePath = projectName + RAIL + SERVICE + JAVA_PREFIX + basePackagePath;
            baseDaoPath = projectName + RAIL + DAO + JAVA_PREFIX + basePackagePath;
            baseMapperPath = projectName + RAIL + DAO + RESOURCES_PREFIX;
            baseModelPath = projectName + RAIL + MODEL + JAVA_PREFIX + basePackagePath;
        } else if (Objects.equals(CUSTOM, projectPattern)) {
            // 自定义
            isCustomPath = true;
            codePathModel = generatorModel.getCodePathModel();
            codePathModel.setControllerPackageName(PackageUtil.pathToPackage(codePathModel.getControllerPath()));
            codePathModel.setApiPackageName(PackageUtil.pathToPackage(codePathModel.getApiPath()));
            codePathModel.setServicePackageName(PackageUtil.pathToPackage(codePathModel.getServicePath()));
            codePathModel.setDaoPackageName(PackageUtil.pathToPackage(codePathModel.getDaoPath()));
            codePathModel.setModelEntityPackageName(PackageUtil.pathToPackage(codePathModel.getModelEntityPath()));
            codePathModel.setModelConverterPackageName(PackageUtil.pathToPackage(codePathModel.getModelConverterPath()));
            codePathModel.setModelDtoPackageName(PackageUtil.pathToPackage(codePathModel.getModelDtoPath()));
            codePathModel.setModelVoPackageName(PackageUtil.pathToPackage(codePathModel.getModelVoPath()));
        }

        dbQuery = DbFactory.getDbQuery(generatorModel.getUrl());

    }

    public void generate() throws Exception {
        Connection connection = null;
        try {
            connection = getConnection();
            Set<String> tables = findTables(connection);
            Map<String, String> tableCommentMap = findTableComment(connection);
            // final Set<String> lowerCaseSet = includeSet.stream().map(String::toLowerCase).collect(Collectors.toSet());
            final Set<String> lowerCaseSet = includeSet;
            if (lowerCaseSet.size() > 0) {
                tables = tables.stream().filter(lowerCaseSet::contains).collect(Collectors.toSet());
            }
            if (excludeSet.size() > 0) {
                tables = tables.stream().filter(tableNameStr -> !excludeSet.contains(tableNameStr)).collect(Collectors.toSet());
            }

            if (StrUtil.isNotBlank(includePrefix)) {
                tables = tables.stream().filter(tableNameStr -> StrUtil.startWithIgnoreCase(tableNameStr, includePrefix)).collect(Collectors.toSet());
            }

            if (tables.size() < 1) {
                throw new RuntimeException("未发现可生成表");
            }
            for (String tableNameStr : tables) {
                primaryKeyFieldName = null;
                primaryKeyFieldMethodName = null;
                tableName = tableNameStr;
                tableDescribe = tableCommentMap.get(tableNameStr) == null ? tableName : tableCommentMap.get(tableNameStr);
                if (StrUtil.isNotBlank(includeMapName.get(tableName)) && (StrUtil.isBlank(tableDescribe) || StrUtil.equals(tableDescribe, tableName))) {
                    tableDescribe = includeMapName.get(tableName);
                }
                String noPrefixName = tableNameStr.toLowerCase();
                if (CollUtil.isNotEmpty(excludePrefix)) {
                    for (String prefix : excludePrefix) {
                        if (noPrefixName.toLowerCase().startsWith(prefix.toLowerCase())) {
                            noPrefixName = noPrefixName.replaceFirst(prefix.toLowerCase(), "");
                            break;
                        }
                    }
                }
                changeTableName = replaceUnderLineAndUpperCase(noPrefixName);
                columnClassList.clear();
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSet resultSet = dbQuery.getResultSet(databaseMetaData, tableName);
                final ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
                while (primaryKeys.next()) {
                    primaryKeyColumnName = primaryKeys.getString("COLUMN_NAME");
                    primaryKeyFieldName = replaceUnderLineAndUpperCase(primaryKeyColumnName);
                }
                try {
                    //生成Model文件
                    if (fileType.contains(1)) {
                        generateModelFile(resultSet);
                    }
                    //生成DTO文件
                    if (fileType.contains(2)) {
                        generateDTOFile(resultSet);
                    }
                    //生成ListDTO文件
                    if (fileType.contains(3)) {
                        generateListDTOFile(resultSet);
                    }
                    //生成VO文件
                    if (fileType.contains(4)) {
                        generateVOFile(resultSet);
                    }
                    // 生成Converter文件
                    if (fileType.contains(5)) {
                        generateConverterFile(resultSet);
                    }
                    //生成Mapper文件
                    if (fileType.contains(6)) {
                        generateMapperFile(resultSet);
                    }
                    //生成Dao文件
                    if (fileType.contains(7)) {
                        generateDaoFile(resultSet);
                    }
                    //生成Repository文件
                    // generateRepositoryFile(resultSet);
                    //生成服务层接口文件
                    if (fileType.contains(8)) {
                        generateServiceInterfaceFile(resultSet);
                    }
                    //生成服务实现层文件
                    if (fileType.contains(9)) {
                        generateServiceImplFile(resultSet);
                    }
                    //生成Controller层文件
                    if (fileType.contains(10)) {
                        generateControllerFile(resultSet);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Set<String> findTables(Connection connection) throws SQLException {
        Set<String> set = new HashSet<>();
        PreparedStatement ps = connection.prepareStatement(dbQuery.getShowTablesSql());
        final ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            final String tableName = resultSet.getString(1);
            set.add(tableName);
        }
        if (set.size() <= 0) {
            throw new RuntimeException("未在指定数据库中发现可用表！");
        }
        return set;
    }

    public Map<String, String> findTableComment(Connection connection) throws SQLException {
        Map<String, String> map = new HashMap<>(16);
        PreparedStatement ps = connection.prepareStatement(dbQuery.getShowTablesCommentSql());
        final ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String tableName = resultSet.getString(1);
            String tableComment = resultSet.getString(2);
            map.put(tableName, tableComment);
        }
        return map;
    }

    private String getCreatePath(String baseFilePath, String filePath, String suffix) {
        final String path = basePath + File.separator + baseFilePath + File.separator + filePath + File.separator + changeTableName + suffix;
        return path.replace("\\", "/");
    }

    private void generateModelFile(ResultSet resultSet) throws Exception {
        final String suffix = ".java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getModelEntityPath(), "", suffix);
        } else {
            path = getCreatePath(baseModelPath, MODEL + SLASH + ENTITY, suffix);
        }

        final String templateName = "Model.ftl";
        File mapperFile = new File(path);
        this.checkFilePath(mapperFile);
        if (isCustomPath) {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, codePathModel.getModelEntityPackageName());
        } else {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, MODEL + SLASH + ENTITY);
        }
        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + ".java 完成 >>>>>>>>>>>");
    }

    private void generateModelAndDTOAndVoFile(ResultSet resultSet, String templateName, File createFile, String packageName) throws Exception {
        if (columnClassList.size() < 1) {
            ColumnClass columnClass = null;
            while (resultSet.next()) {
                //id字段略过
                // if (StrUtil.equalsAny(templateName, "Model.ftl")) {
                //     if (StrUtil.equalsAny(resultSet.getString("COLUMN_NAME"), "id", "is_delete")) {
                //         continue;
                //     }
                // }
                columnClass = new ColumnClass();
                //获取字段名称
                String columnName = resultSet.getString("COLUMN_NAME");
                columnClass.setColumnName(columnName);
                //获取字段类型
                columnClass.setColumnType(resultSet.getString("TYPE_NAME").toLowerCase());
                //转换字段名称，如 sys_name 变成 SysName
                if (StrUtil.isUpperCase(columnName)) {
                    // 全部大写转换成消息
                    columnName = columnName.toLowerCase();
                }
                // 替换特殊字符
                columnName = columnName.replaceAll("#", "");
                columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(columnName));
                // 字段在数据库的注释
                String remarks = resultSet.getString("REMARKS");
                if (StrUtil.isNotBlank(remarks) && (remarks.contains("\r") || remarks.contains("\n"))) {
                    remarks = remarks.replace("\r", " ").replace("\n", " ");
                }
                columnClass.setColumnComment(remarks);
                final boolean equals = StrUtil.equals(columnClass.getColumnName(), primaryKeyColumnName);
                if (equals) {
                    primaryKeyFieldMethodName = StrUtil.upperFirst(columnClass.getChangeColumnName());
                }
                columnClass.setPrimaryKey(equals);
                columnClassList.add(columnClass);
            }
        }
        List<ColumnClass> relColumnClassList;
        if (StrUtil.equalsAny(templateName, "Model.ftl")) {
            // relColumnClassList = columnClassList.stream().filter(entity -> !StrUtil.equalsAny(entity.getColumnName(), "is_delete"))
            //         .collect(Collectors.toList());
            relColumnClassList = new ArrayList<>(columnClassList);
        } else {
            relColumnClassList = new ArrayList<>(columnClassList);
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("model_column", relColumnClassList);
        generateFileByTemplate(templateName, packageName, createFile, dataMap);
    }

    private void generateListDTOFile(ResultSet resultSet) throws Exception {
        final String suffix = "ListDTO.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getModelDtoPath(), "", suffix);
        } else {
            path = getCreatePath(baseModelPath, MODEL + SLASH + DTO, suffix);
        }

        final String templateName = "ListDTO.ftl";
        File mapperFile = new File(path);
        this.checkFilePath(mapperFile);
        if (isCustomPath) {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, codePathModel.getModelDtoPackageName());
        } else {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, MODEL + SLASH + DTO);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "ListDTO.java 完成 >>>>>>>>>>>");
    }

    private void generateVOFile(ResultSet resultSet) throws Exception {
        final String suffix = "VO.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getModelVoPath(), "", suffix);
        } else {
            path = getCreatePath(baseModelPath, MODEL + SLASH + VO, suffix);
        }

        final String templateName = "VO.ftl";
        File mapperFile = new File(path);
        this.checkFilePath(mapperFile);
        if (isCustomPath) {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, codePathModel.getModelVoPackageName());
        } else {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, MODEL + SLASH + VO);
        }
        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "VO.java 完成 >>>>>>>>>>>");
    }

    private void generateDTOFile(ResultSet resultSet) throws Exception {
        final String suffix = "DTO.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getModelDtoPath(), "", suffix);
        } else {
            path = getCreatePath(baseModelPath, MODEL + SLASH + DTO, suffix);
        }

        final String templateName = "DTO.ftl";
        File mapperFile = new File(path);
        this.checkFilePath(mapperFile);
        if (isCustomPath) {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, codePathModel.getModelDtoPackageName());
        } else {
            generateModelAndDTOAndVoFile(resultSet, templateName, mapperFile, MODEL + SLASH + DTO);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "DTO.java 完成 >>>>>>>>>>>");
    }

    private void generateConverterFile(ResultSet resultSet) throws Exception {
        final String suffix = "Converter.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getModelConverterPath(), "", suffix);
        } else {
            path = getCreatePath(baseModelPath, MODEL + SLASH + CONVERTER, suffix);
        }

        final String templateName = "Converter.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();
        if (isCustomPath) {
            generateFileByTemplate(templateName, codePathModel.getModelConverterPackageName(), mapperFile, dataMap);
        } else {
            generateFileByTemplate(templateName, MODEL + SLASH + CONVERTER, mapperFile, dataMap);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "Converter.java 完成 >>>>>>>>>>>");
    }

    private void generateControllerFile(ResultSet resultSet) throws Exception {
        final String suffix = "Controller.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getControllerPath(), "", suffix);
        } else {
            path = getCreatePath(baseControllerPath, CONTROLLER, suffix);
        }

        final String templateName = "Controller.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();
        if (isCustomPath) {
            generateFileByTemplate(templateName, codePathModel.getControllerPackageName(), mapperFile, dataMap);
        } else {
            generateFileByTemplate(templateName, CONTROLLER, mapperFile, dataMap);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "Controller.java 完成 >>>>>>>>>>>");
    }

    private void generateServiceImplFile(ResultSet resultSet) throws Exception {
        final String suffix = "ServiceImpl.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getServicePath(), "", suffix);
        } else {
            path = getCreatePath(baseServicePath, SERVICE, suffix);
        }

        final String templateName = "ServiceImpl.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();

        if (isCustomPath) {
            generateFileByTemplate(templateName, codePathModel.getServicePackageName(), mapperFile, dataMap);
        } else {
            generateFileByTemplate(templateName, SERVICE, mapperFile, dataMap);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "ServiceImpl.java 完成 >>>>>>>>>>>");
    }

    private void generateServiceInterfaceFile(ResultSet resultSet) throws Exception {
        final String suffix = "Service.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getApiPath(), "", suffix);
        } else {
            path = getCreatePath(baseApiPath, API, suffix);
        }

        final String templateName = "Service.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();
        if (isCustomPath) {
            generateFileByTemplate(templateName, codePathModel.getApiPackageName(), mapperFile, dataMap);
        } else {
            generateFileByTemplate(templateName, API, mapperFile, dataMap);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "Service.java 完成 >>>>>>>>>>>");
    }

    private void generateRepositoryFile(ResultSet resultSet) throws Exception {
        final String suffix = "Repository.java";
        final String path = basePath + changeTableName + suffix;
        final String templateName = "Repository.ftl";
        File mapperFile = new File(path);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
    }

    private void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap) throws Exception {
        this.generateFileByTemplate(templateName, null, file, dataMap);
    }

    private void generateFileByTemplate(final String templateName, String packagePath, File file, Map<String, Object> dataMap) throws Exception {
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("serialVersionUID", getSerialVersionUID());
        // 小写
        dataMap.put("table_name_small", tableName);
        // 首字母大写驼峰
        dataMap.put("table_name", changeTableName);
        // 首字母小写驼峰
        dataMap.put("lower_table_name", StrUtil.lowerFirst(changeTableName));
        dataMap.put("author", author);
        if(StrUtil.isNotBlank(tableDescribe)){
            tableDescribe = tableDescribe.replaceAll("\\n", "").replaceAll("\\r","");
        }
        dataMap.put("table_describe", tableDescribe);
        dataMap.put("date", DateUtil.formatDateTime(new Date()));
        dataMap.put("primary_key_field", primaryKeyFieldName);
        dataMap.put("primary_key_field_method", primaryKeyFieldMethodName);

        if (isCustomPath) {
            dataMap.put("dto_package_name", codePathModel.getModelDtoPackageName());
            dataMap.put("vo_package_name", codePathModel.getModelVoPackageName());
            dataMap.put("entity_package_name", codePathModel.getModelEntityPackageName());
            dataMap.put("package_name", packagePath);
            dataMap.put("api_package_name", codePathModel.getApiPackageName());
            dataMap.put("service_package_name", codePathModel.getServicePackageName());
            dataMap.put("converter_package_name", codePathModel.getModelConverterPackageName());
            dataMap.put("dao_package_name", codePathModel.getDaoPackageName());
        } else {
            dataMap.put("dto_package_name", getSuffixPackageName(MODEL + SLASH + DTO));
            dataMap.put("vo_package_name", getSuffixPackageName(MODEL + SLASH + VO));
            dataMap.put("entity_package_name", getSuffixPackageName(MODEL + SLASH + ENTITY));
            dataMap.put("package_name", getSuffixPackageName(packagePath));
            dataMap.put("api_package_name", getSuffixPackageName(API));
            dataMap.put("service_package_name", getSuffixPackageName(SERVICE));
            dataMap.put("converter_package_name", getSuffixPackageName(MODEL + SLASH + CONVERTER));
            dataMap.put("dao_package_name", getSuffixPackageName(DAO));
        }

        // dataMap.put("table_annotation", tableAnnotation);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
    }

    /**
     * 生成serialVersionUID
     */
    protected String getSerialVersionUID() {
        return String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits())) + "L";
    }

    private String getSuffixPackageName(String packagePath) {
        if (StrUtil.isBlank(packagePath)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (packagePath.contains(SLASH)) {
            final String[] split = packagePath.split(SLASH);
            for (String str : split) {
                sb.append(str).append(DOT);
            }
            return basePackageName + DOT + sb.substring(0, sb.length() - 1);
        }
        return basePackageName + DOT + packagePath;
    }

    /**
     * Mapper.java
     */
    private void generateDaoFile(ResultSet resultSet) throws Exception {
        final String suffix = "Mapper.java";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getDaoPath(), "", suffix);
        } else {
            path = getCreatePath(baseDaoPath, DAO, suffix);
        }
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();
        if (isCustomPath) {
            generateFileByTemplate(templateName, codePathModel.getDaoPackageName(), mapperFile, dataMap);
        } else {
            generateFileByTemplate(templateName, DAO, mapperFile, dataMap);
        }

        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "Mapper.java 完成 >>>>>>>>>>>");
    }

    /**
     * Mapper.xml
     */
    private void generateMapperFile(ResultSet resultSet) throws Exception {
        final String suffix = "Mapper.xml";
        String path;
        if (isCustomPath) {
            path = getCreatePath(codePathModel.getMapperPath(), "", suffix);
        } else {
            path = getCreatePath(baseMapperPath, MAPPER, suffix);
        }

        final String templateName = "Mapper.xml.ftl";
        File mapperFile = new File(path);
        checkFilePath(mapperFile);
        Map<String, Object> dataMap = new HashMap<>();
        generateFileByTemplate(templateName, mapperFile, dataMap);
        System.out.println("<<<<<<<<<<<< 生成 " + changeTableName + "Mapper.xml 完成 >>>>>>>>>>>");
    }

    private void checkFilePath(File file) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        if (!isReplace && file.exists()) {
            throw new RuntimeException(String.format("路径下文件已存在，如需替换请修改配置！\r\n %s", file.getAbsolutePath()));
        }
    }

    public String replaceUnderLineAndUpperCase(String str) {
        return StrUtil.upperFirst(StrUtil.toCamelCase(str));
    }
}
