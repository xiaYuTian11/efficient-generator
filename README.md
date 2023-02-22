# generator

代码生成器

## 框架

代码生成器所用框架springboot+mybatis-plus+自定义[commons](https://github.com/xiaYuTian11/commons)模块

## 配置说明

```text
url=jdbc:postgresql://127.0.0.1:5432/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true
driver=org.postgresql.Driver
user=postgres
password=postgres

## 代码生成的基础路径，不写默认是当前工程路径
basePath=C:\\Users\\tmw\\Desktop\\test
## 包文件路径 com.generator.demo
packageName=com.generator.demo
# single-单模块，multi-多模块
pattern=single
# 多模块时，需要设置统一的模块名称，如generator-web,generator-dao,则此处填写generator，自动拼接模块名称
projectName=generator
## 包含表名，多个 英文逗号分隔
includeSet=sys_log
## 表注释，不填默认读取数据库备注
includeSetComment=系统日志表
## 不包含表名，多个 英文逗号分隔
excludeSet=
## 去掉指定前缀，如sys,sys_等,多个逗号分割
excludePrefix=
## 是否替换覆盖现有文件，默认不会覆盖
replace=true
## 默认全部生成,生成文件,1-model,2-dto,3-listDto,4-VO,5-converter,6-mapper,7-dao,8-service,9-serviceimpl,10-controller
## 1-4,生成表示包含中间连续的类型，英文逗号包括分隔
fileType=
## 使用外包模版,模版放在resources/templates不用配置，会默认读取外包模版
templatePath=
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
        Generator.run(path);
    }
}
```
