# generator

代码生成器

## 框架

代码生成器所用框架springboot+mybatis-plus+自定义[topmons](https://github.top/xiaYuTian11/topmons)模块

## 配置说明

```text
url=jdbc:postgresql://127.0.0.1:5432/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true
driver=org.postgresql.Driver
user=postgres
password=postgres

author=TMW
## 是否删除以生成文件（用于配置错误生成的错误文件删除操作）,默认false
isDelete=
## 删除层级，默认生成文件，如top.tanmw.demo.SysUserController 默认只删除 SysUserController.java，如果值为2，则删除demo包下所有文件
deleteLevel=
## 注意文件路径采用反斜杠,默认当前工程路径
basePath=
## 注意文件路径采用反斜杠,将在每个文件路径后面在拼接上这个路径 register/train
suffixPath=
## 包名称,多模块使用
projectName=demo
## 模式，single工程，multi 多模块，默认多模块
packageName=com.efficient.system
## 模式，single工程，multi 多模块，默认多模块
pattern=single
## 包含表名，多个 英文逗号分隔
includeSet=efficient_sys_user,efficient_sys_org,efficient_sys_org_user,efficient_sys_system,efficient_sys_menu,efficient_sys_role,efficient_sys_role_menu,efficient_sys_user_manage,efficient_sys_user_group,efficient_sys_user_group_relation,efficient_sys_org_role,efficient_sys_system_user,efficient_sys_dict,efficient_sys_config
includeSetComment=
## 包含前缀
includePrefix=
## 替换前缀
replacePrefix=
## 排除集合
excludeSet=
## 去掉指定前缀，如sys,sys_等,多个逗号分割
excludePrefix=efficient_
## 是否替换覆盖现有文件，默认不会覆盖
replace=true
## 生成文件,1-model,2-dto,3-listDto,4-VO,5-converter,6-mapper,7-dao,8-service,9-serviceimpl,10-controller
## 1-4,生成表示包含中间连续的类型，英文逗号包括分隔
fileType=
## 是否自动生成增删改查方法，模式true
crud=true
## 使用外包模版,模版放在resources/templates不用配置，会默认读取外包模版
templatePath=
## mybatis-plus 逻辑删除字段
tableLogic=
## mybatis-plus 新增时自动插入字段
tableFieldInsert=
## mybatis-plus 修改时自动插入字段
tableFieldUpdate=
## mybatis-plus 新增修改同时变更字段
tableFieldInsertUpdate=
```
## 依赖
```xml
<repositories>
    <repository>
        <id>maven-public</id>
        <name>maven-public</name>
        <url>https://repo.maven.apache.org/maven2/</url>
        <layout>default</layout>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
</repositories>
<dependencies>
    <dependencies>
        <dependency>
            <groupId>top.tanmw</groupId>
            <artifactId>efficient-generator</artifactId>
            <version>1.3.19</version>
        </dependency>
        <!--    自定义数据库依赖   -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>
    </dependencies>
</dependencies>
```

## 使用

```java
// 将项目打包后生成的jar引入需要工程

/**
 * 代码生成器
 *
 * @author TMW
 * @since 2022/7/8 10:11
 */
public class GeneratorTest {
    @Test
    public void generator() throws Exception {
        // 传入配置文件地址
        String path = GeneratorTest.class.getResource("/generator.properties").getPath().toString();
        GeneratorModel model = init(url);
        
        // 需要完全自定义路径加入下面代码，否则不需要
        CodePathModel build = CodePathModel.builder()
                .controllerPath("tanmw-web/src/main/java/top/tanmw/web/controller")
                .apiPath("tanmw-api/src/main/java/top/tanmw/front/api")
                .servicePath("tanmw-station/src/main/java/top/tanmw/front/station/service")
                .daoPath("tanmw-dao/src/main/java/top/tanmw/dao/mapper")
                .modelEntityPath("tanmw-model/src/main/java/top/tanmw/model/bean")
                .modelConverterPath("tanmw-model/src/main/java/top/tanmw/model/converter")
                .modelDtoPath("tanmw-model/src/main/java/top/tanmw/model/dto")
                .modelVoPath("tanmw-model/src/main/java/top/tanmw/model/vo")
                .mapperPath("tanmw-dao/src/main/java/top/tanmw/dao/mapper/xml")
                .build();
        model.setCodePathModel(build);
        // 自定义路径代码到此结束
        
        generate(model);
    }
}
```
