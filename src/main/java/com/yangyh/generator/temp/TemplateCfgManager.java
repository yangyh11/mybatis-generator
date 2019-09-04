package com.yangyh.generator.temp;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModelException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: yangyh
 * @create: 2019-05-15 17:20
 */
public class TemplateCfgManager {

    public static Configuration configuration;

    public static Configuration getConfiguration() {
        if (configuration != null) {
            return configuration;
        }
        configuration = new Configuration();
        try {
            org.apache.commons.configuration.Configuration conf = new PropertiesConfiguration("conf/gen-conf.properties");
            configuration.setDirectoryForTemplateLoading(new File(conf.getString("templateDir")));
            configuration.setSharedVariable("basePackage", conf.getString("basePackage", ""));
            configuration.setSharedVariable("targetDir", conf.getString("targetDir", System.getProperty("user.home") + File.separator+ "output"));
            configuration.setSharedVariable("author", System.getProperty("user.name"));
            configuration.setObjectWrapper(new DefaultObjectWrapper());
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }

        return configuration;
    }
}
