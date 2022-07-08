package top.tanmw.generator;

import cn.hutool.core.util.StrUtil;

/**
 * @author TMW
 * @since 2022/3/2 15:39
 */
public enum ProjectPattern {
    SINGLE("single"),
    MULTI("multi");
    private String desc;

    ProjectPattern(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ProjectPattern getPattern(String pattern) {
        final ProjectPattern[] values = ProjectPattern.values();
        for (ProjectPattern value : values) {
            final String desc = value.getDesc();
            if (StrUtil.equals(desc, pattern)) {
                return value;
            }
        }
        return null;
    }

}
