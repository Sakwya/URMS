package top.sakwya.urms.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;


public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/employee?useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("sakwya")
                            .outputDir("E:\\Desktop\\temp");
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("top.sakwya.urms")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\Desktop\\temp\\mapper\\"));
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()
                            .enableRestStyle();
                    builder.addInclude("user").addInclude("role").addInclude("menu")
                            .addInclude("user_role").addInclude("role_menu");
                }).execute();
    }
}
