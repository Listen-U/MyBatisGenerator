package com.listen.generator.method.plugins;

import java.util.List;

import com.listen.generator.method.tools.BaseGenTool;
import com.listen.generator.method.tools.MethodGeneratorTool;
import com.listen.generator.method.tools.SqlMapperGeneratorTool;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class BatchDeletePlugin extends PluginAdapter {

    private final static String BATCH_DELETE = "deleteBatch";

    private final static String PARAMETER_NAME = "ids";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (BaseGenTool.isMybatisMode(introspectedTable)) {
            MethodGeneratorTool.defaultBatchDeleteMethodGen(interfaze,introspectedTable,context);
        }
        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTargetRuntime().equals(IntrospectedTable.TargetRuntime.MYBATIS3)){
            addSqlMapper(document, introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }


    /**
     * 批量删除的xml方法生成
     * @param document
     * @param introspectedTable
     */
    private void addSqlMapper(Document document, IntrospectedTable introspectedTable){
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        String key = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        String baseSql = String.format("delete from %s where %s in (",tableName,key);

        FullyQualifiedJavaType paramType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();

        XmlElement deleteElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.DELETE, BATCH_DELETE,paramType);

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator(PARAMETER_NAME,"item","index",null);

        deleteElement.addElement(new TextElement(baseSql));

        foreachElement.addAttribute(new Attribute("separator", ","));

        foreachElement.addElement(new TextElement("#{item}"));

        deleteElement.addElement(foreachElement);

        deleteElement.addElement(new TextElement(")"));

        document.getRootElement().addElement(deleteElement);
    }
}
