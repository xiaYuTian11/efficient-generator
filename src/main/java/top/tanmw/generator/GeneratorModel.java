package top.tanmw.generator;

import cn.hutool.core.util.StrUtil;
import top.tanmw.generator.model.CodePathModel;

import java.util.*;

/**
 * 代码生成器模型
 *
 * @author TMW
 * @since 2022/3/1 16:07
 */
public class GeneratorModel {
    /**
     * 数据库 url
     */
    private String url;
    /**
     * 驱动
     */
    private String driver;
    /**
     * 用户
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 模版路径
     */
    private String templatePath;
    /**
     * 作者
     */
    private String author;
    /**
     * 查询表sql
     */
    private String showTablesSql;
    /**
     * 表注释sql
     */
    private String showTablesCommentSql;
    /**
     * 基础路径
     */
    private String basePath;
    /**
     * 工程名称
     */
    private String projectName;
    /**
     *
     */
    private String packageName;
    /**
     * 是否覆盖
     */
    private boolean replace;
    /**
     * 模式，single工程，multi 多模块
     */
    private String pattern;
    private List<Integer> fileType;
    private Set<String> includeSet;
    private Set<String> includeSetComment;
    private Set<String> excludeSet;
    private Set<String> excludePrefix;
    private Set<String> replacePrefixSet;

    private CodePathModel codePathModel;

    private Set<String> includePrefix;
    private String tableLogic;
    private String tableFieldInsert;
    private String tableFieldUpdate;
    private String tableFieldInsertUpdate;
    private Boolean crud;

    public String getTableFieldInsertUpdate() {
        return tableFieldInsertUpdate;
    }

    public void setTableFieldInsertUpdate(String tableFieldInsertUpdate) {
        this.tableFieldInsertUpdate = tableFieldInsertUpdate;
    }

    public String getTableLogic() {
        // if (StrUtil.isBlank(tableLogic)) {
        //     return "is_delete";
        // }
        return tableLogic;
    }

    public void setTableLogic(String tableLogic) {
        this.tableLogic = tableLogic;
    }

    public String getTableFieldInsert() {
        return tableFieldInsert;
    }

    public void setTableFieldInsert(String tableFieldInsert) {
        this.tableFieldInsert = tableFieldInsert;
    }

    public String getTableFieldUpdate() {
        return tableFieldUpdate;
    }

    public void setTableFieldUpdate(String tableFieldUpdate) {
        this.tableFieldUpdate = tableFieldUpdate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Integer> getFileType() {
        return fileType;
    }

    public void setFileType(List<Integer> fileType) {
        this.fileType = fileType;
    }

    public Set<String> getExcludePrefix() {
        return excludePrefix;
    }

    public void setExcludePrefix(String excludePrefix) {
        if (StrUtil.isNotBlank(excludePrefix)) {
            this.excludePrefix = new HashSet<>(Arrays.asList(excludePrefix.split(",")));
        } else {
            this.excludePrefix = new HashSet<>();
        }
    }

    public Set<String> getReplacePrefix() {
        return replacePrefixSet;
    }

    public void setReplacePrefix(String replacePrefixSet) {
        if (StrUtil.isNotBlank(replacePrefixSet)) {
            this.replacePrefixSet = new HashSet<>(Arrays.asList(replacePrefixSet.split(",")));
        } else {
            this.replacePrefixSet = new HashSet<>();
        }
    }

    public boolean isReplace() {
        return replace;
    }

    public void setReplace(boolean replace) {
        this.replace = replace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getShowTablesSql() {
        return showTablesSql;
    }

    public void setShowTablesSql(String showTablesSql) {
        this.showTablesSql = showTablesSql;
    }

    public String getShowTablesCommentSql() {
        return showTablesCommentSql;
    }

    public void setShowTablesCommentSql(String showTablesCommentSql) {
        this.showTablesCommentSql = showTablesCommentSql;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Set<String> getIncludeSet() {
        return includeSet;
    }

    public void setIncludeSet(Set<String> includeSet) {
        this.includeSet = includeSet;
    }

    public void setIncludeSet(String includeSet) {
        if (StrUtil.isNotBlank(includeSet)) {
            this.includeSet = new HashSet<>(Arrays.asList(includeSet.split(",")));
        } else {
            this.includeSet = new HashSet<>();
        }
    }

    public Set<String> getIncludeSetComment() {
        return includeSetComment;
    }

    public void setIncludeSetComment(String includeSetComment) {
        if (StrUtil.isNotBlank(includeSetComment)) {
            this.includeSetComment = new HashSet<>(Arrays.asList(includeSetComment.split(",")));
        } else {
            this.includeSetComment = new HashSet<>();
        }
    }

    public Set<String> getExcludeSet() {
        return excludeSet;
    }

    public void setExcludeSet(Set<String> excludeSet) {
        this.excludeSet = excludeSet;
    }

    public void setExcludeSet(String excludeSet) {
        if (StrUtil.isNotBlank(excludeSet)) {
            this.excludeSet = new HashSet<>(Arrays.asList(excludeSet.split(",")));
        } else {
            this.excludeSet = new HashSet<>();
        }
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public CodePathModel getCodePathModel() {
        if (Objects.isNull(codePathModel)) {
            codePathModel = CodePathModel.builder().build();
        }
        return codePathModel;
    }

    public void setCodePathModel(CodePathModel codePathModel) {
        this.codePathModel = codePathModel;
    }

    public Set<String> getIncludePrefix() {
        return includePrefix;
    }

    public void setIncludePrefix(String includePrefix) {
        if (StrUtil.isNotBlank(includePrefix)) {
            this.includePrefix = new HashSet<>(Arrays.asList(includePrefix.split(",")));
        } else {
            this.includePrefix = new HashSet<>();
        }
    }

    public Boolean isCrud() {
        if (Objects.isNull(crud)) {
            return true;
        }
        return crud;
    }

    public void setCrud(String crud) {
        if (StrUtil.isBlank(crud)) {
            this.crud = true;
        } else {
            this.crud = StrUtil.equalsAnyIgnoreCase(crud, "true");
        }
    }
}
