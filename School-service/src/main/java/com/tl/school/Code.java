package com.tl.school;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.Arrays;

public class Code {
    public static void main(String[] args) {
        //需要构建一个代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //配置策略
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
//        String property = System.getProperty("E:\\IDEA\\SchoolShop\\School-service\\");//获取用户生成的目录
        gc.setOutputDir("E:/IDEA/SchoolShop/School-service/src/main/java");
        gc.setAuthor("tl");
        gc.setFileOverride(true);//是否覆盖原来生成的
        gc.setServiceName("%sService");//去Service的I前缀
        gc.setIdType(IdType.ASSIGN_ID);//entity的Id生成策略
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        //设置数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:p6spy:mysql:///SchoolShop?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDriverName("com.p6spy.engine.spy.P6SpyDriver");
        mpg.setDataSource(dsc);

        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.tl");
        pc.setModuleName("school");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("user","boy");//设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);//设置包的命名规则，下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);//自动生成lombok
        strategy.setLogicDeleteFieldName("deleted");//设置字段逻辑删除
        TableFill create = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        strategy.setTableFillList(Arrays.asList(create,updateTime));//设置字段自动填充
        strategy.setVersionFieldName("version");//设置乐观锁配置
        mpg.setStrategy(strategy);

        //执行
        mpg.execute();
    }
}

