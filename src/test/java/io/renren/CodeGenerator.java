package io.renren;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("C:\\Users\\Administrator\\Documents\\WeChat Files\\wxid_4r2wer1y4ojd22\\FileStorage\\File\\2021-02\\renren\\renren-fast" + "/src/main/java");//设置出入目录
        gc.setAuthor("java");//设置作者名
        gc.setOpen(false);//设置代码生成完是否打开目录
        gc.setFileOverride(false);//设置生成的代码是否覆盖之前的代码
        gc.setServiceName("%sService");//设置Service的名字
        gc.setIdType(IdType.ASSIGN_ID);//设置主键生成策略
        gc.setDateType(DateType.ONLY_DATE);//设置日期类型
        gc.setSwagger2(true); //开启swagger2模式
        mpg.setGlobalConfig(gc);//保存全局设置

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://101.201.150.92:3306/renren_fast?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("101399jjy..");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);//保存数据源设置

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("department");//设置包的模块名
        pc.setParent("io.renren.modules");//设置包名
        //上面两个都是设置包名的，包名组成为。setParent/setModuleName
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("design_center","financial_center","intelligence_center","quality_testing_center","strategy_center","technology_center");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}