package com.listen.generator.method.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * @author winfo064
 * @className MyCommentGenerator1
 * @date 2019/2/27
 **/
public class MyCommentGenerator1 extends DefaultCommentGenerator {

    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;

    public MyCommentGenerator1() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        sb.append("\n");
        sb.append("	* 列名:" + introspectedColumn.getActualColumnName() + " 类型:" + introspectedColumn.getJdbcTypeName()
                + "(" + introspectedColumn.getLength() + ")" + " 允许空:" + introspectedColumn.isNullable() + " 缺省值:"
                + introspectedColumn.getDefaultValue());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/**");
        sb.append("\n");
        sb.append("	* ");
        sb.append("\n");
        sb.append("	* @author zhangqin" + "\n");
        if (!suppressDate) {

            sb.append("	* @date " + currentDateStr + "\n");

        }

        List<Parameter> parameters = method.getParameters();

        for (Parameter parameter : parameters) {

            sb.append("	* @param " + parameter.getName() + "\n");

        }

        sb.append("	* @return " + method.getReturnType());
        sb.append("\n" + "	*/");
        method.addJavaDocLine(sb.toString());
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {

    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {

        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/**");
        sb.append("\n");
        sb.append("* ");
        sb.append("\n");
        sb.append("* @author Listen" + "\n");
        if (!suppressDate) {

            sb.append("* @date " + currentDateStr + "\n");

        }
        sb.append("* 数据表" + introspectedTable.getFullyQualifiedTableNameAtRuntime() + "映射bean，由Mybaits自动生成工具生成");
        sb.append("\n" + "*/");
        topLevelClass.addJavaDocLine(sb.toString());

    }

}
