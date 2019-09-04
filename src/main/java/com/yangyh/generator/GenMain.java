package com.yangyh.generator;

import com.yangyh.generator.table.TableMetaInfo;
import com.yangyh.generator.temp.TemplateGen;

/**
 * @description:
 * @author: yangyh
 * @create: 2019-05-15 17:16
 */
public class GenMain {

    public static void main(String[] args) {
        TableMetaInfo metaInfo2 = new TableMetaInfo("hs_asset", "holder_stib_temp");
        TemplateGen templateGen = new TemplateGen();
        templateGen.batchGen(metaInfo2);
    }
}
