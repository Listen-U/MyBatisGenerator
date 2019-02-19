package com.listen.generator.method.tools;

import org.mybatis.generator.api.IntrospectedTable;

public class BaseGenTool {

    /**
     * 判断是不是Mybatis3运行生成的.
     *
     * @param introspectedTable the introspected table
     * @return the boolean
     * @author HuWeihui
     * @since hui_project v1
     */
    public static boolean isMybatisMode(IntrospectedTable introspectedTable){
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)) {
            return true;
        }
        return false;
    }

}
