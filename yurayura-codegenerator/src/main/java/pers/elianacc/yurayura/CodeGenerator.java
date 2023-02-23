package pers.elianacc.yurayura;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Mybatis-Plus代码生成器(新)
 *
 * @author ELiaNaCc
 * @since 2019-10-21
 */
public class CodeGenerator {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(tip);
        return scanner.next();
    }

    public static void main(String[] args) {

        String generatorDb = scanner("请输入需要生成的库名");

        String generatorDbUrl = "jdbc:mysql://127.0.0.1:3306/" + generatorDb + "?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";

        AtomicReference<String> generatorTable = new AtomicReference<>();
        AtomicReference<String> tablePrefixName = new AtomicReference<>();
        AtomicReference<String> modulePackagePath = new AtomicReference<>();

        FastAutoGenerator.create(generatorDbUrl, "root", "123456")
                // 全局配置(GlobalConfig)
                .globalConfig(builder -> builder
                        .author("ELiaNaCc")
                        .outputDir("D://mp_generator")
                        .fileOverride()
                        .enableSwagger()
                )
                // 包配置(PackageConfig)
                .packageConfig((scanner, builder) -> {
                    generatorTable.set(scanner.apply("请输入需要生成的表名"));
                    tablePrefixName.set(generatorTable.get().substring(0, generatorTable.get().indexOf("_")));
                    if (!generatorTable.get().matches("^[a-zA-Z]([a-zA-Z0-9_]+)?$")) {
                        System.out.println("表名输入异常！");
                        System.exit(0);
                    }
                    if (generatorTable.get().contains("_sys_")) {
                        modulePackagePath.set("sys." + generatorTable.get().substring(generatorTable.get().indexOf("_") + 5));
                    } else {
                        modulePackagePath.set(generatorTable.get().substring(generatorTable.get().indexOf("_") + 1));
                    }
                    modulePackagePath.set(modulePackagePath.get().contains("_") ? modulePackagePath.get().substring(0, modulePackagePath.get().indexOf("_")) : modulePackagePath.get());
                    builder.parent("pers.elianacc")
                            .moduleName(tablePrefixName.get())
                            .entity("entity." + modulePackagePath.get())
                            .xml("dao.mapper")
                            .mapper("dao")
                            .serviceImpl("service.impl")
                            .service("service")
                            .controller("controller");
                })
                // 模板配置(TemplateConfig)
                .templateConfig(builder -> builder
                        .entity("templates/entity.java.vm")
                        .mapperXml("templates/mapper.xml.vm")
                        .mapper("templates/mapper.java.vm")
                        .serviceImpl("templates/serviceImpl.java.vm")
                        .service("templates/service.java.vm")
                        .controller("templates/controller.java.vm")
                )
                // 注入配置(InjectionConfig)
                .injectionConfig(builder -> {
                    Map<String, Object> parmMap = new HashMap<>();
                    parmMap.put("sysModulePath", generatorTable.get().contains("_sys_") ? "/sys/" : "/");
                    parmMap.put("modulePackagePath", modulePackagePath.get());
                    parmMap.put("packageParentPath", "pers.elianacc." + tablePrefixName.get());
                    builder.customMap(parmMap);
                })
                // 策略配置(StrategyConfig)
                .strategyConfig(builder -> {
                    String tablePrefix = generatorTable.get().contains("_sys_") ? tablePrefixName.get() + "_sys_" : tablePrefixName.get() + "_";
                    String filePrefix = generatorTable.get().contains("_sys_") ? "Sys" : "";
                    builder.addInclude(generatorTable.get())
                            .addTablePrefix(tablePrefix)
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .formatFileName(filePrefix + "%s")
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatXmlFileName(filePrefix + "%sMapper")
                            .formatMapperFileName(filePrefix + "%sMapper")
                            .serviceBuilder()
                            .formatServiceImplFileName(filePrefix + "%sServiceImpl")
                            .formatServiceFileName("I" + filePrefix + "%sService")
                            .controllerBuilder()
                            .enableRestStyle()
                            .formatFileName(filePrefix + "%sController");
                })
                .execute();
    }

}
