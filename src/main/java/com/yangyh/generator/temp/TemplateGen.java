package com.yangyh.generator.temp;

import com.yangyh.generator.table.TableMetaInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成实体类、Mapper接口、Mybatis配置文件
 */
public class TemplateGen {

    public void batchGen(TableMetaInfo metaInfo) {
        try {
            Configuration conf = new PropertiesConfiguration("conf/gen-conf.properties");
            String targetDir = conf.getString("targetDir") + File.separator + "src" + File.separator + "main";
            String srcBaseDir = targetDir + File.separator + "java";
            String basePackage = conf.getString("basePackage");
            String str = File.separator;
            String packageDir = "";
            if (str.equals("\\")) {
                packageDir = basePackage.replaceAll("\\.", "\\" + File.separator);
            } else {
                packageDir = basePackage.replaceAll("\\.", File.separator);
            }

            // 实体类
            String entityDir = srcBaseDir + File.separator + packageDir + File.separator + "entity";
            File entieyDirFile = new File(entityDir);
            if (!entieyDirFile.exists()) {
                entieyDirFile.mkdirs();
            }
            genCode(metaInfo, "entity.ftl", entityDir + File.separator + metaInfo.getClassName() + "Entity.java");

            // mapper接口
            String classMapperDir = srcBaseDir + File.separator + packageDir + File.separator + "mapper";
            File classMapperDirFile = new File(classMapperDir);
            if (!classMapperDirFile.exists()) {
                classMapperDirFile.mkdirs();
            }
            genCode(metaInfo, "class-mapper.ftl", classMapperDir + File.separator + metaInfo.getClassName() + "Mapper.java");

            // mybatis配置文件(xml)
            String resourceBaseDir = targetDir + File.separator + "resources" + File.separator + "sql-mapper";
            File resourceBaseDirFile = new File(resourceBaseDir);
            if (!resourceBaseDirFile.exists()) {
                resourceBaseDirFile.mkdirs();
            }
            genCode(metaInfo, "mybatis-mapper.ftl", resourceBaseDir + File.separator + "mapper-"
                    + metaInfo.getClassName().toLowerCase() + ".xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void genCode(TableMetaInfo metaInfo, String freemarkerName, String targetFile) {
        try {
            Template template = TemplateCfgManager.getConfiguration().getTemplate(freemarkerName);
            FileOutputStream fos = new FileOutputStream(new File(targetFile));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("metaInfo", metaInfo);
            template.process(map, new OutputStreamWriter(fos, "utf-8"));
            fos.flush();
            fos.close();
            System.out.println("文件[" + targetFile + "]成功生成！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
