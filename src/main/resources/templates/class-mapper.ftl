package ${basePackage!}.mapper;

import ${basePackage!}.entity.${metaInfo.className!}Entity;

import java.util.List;
import java.util.Map;

/**
  * @description:
  * @author: ${author!}
  * @create: ${.now}
  */
public interface ${metaInfo.className!}Mapper {

    List<${metaInfo.className!}Entity> queryByMap(Map<String, Object> map);

    void save(${metaInfo.className!}Entity entity);

    void update(${metaInfo.className!}Entity entity);

}