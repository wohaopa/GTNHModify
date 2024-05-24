# GTNH Modify （万宁GTNH）
GTNH整合包的修改Mod，致力于修改合成表。

[English]((https://github.com/wohaopa/GTNHModify/blob/master/README.md))
中文

## 配置
在`GTNHmodify.cfg`文件中配置此Mod。这是已有的几种模式：
1. `None`: 什么都不做
2. `OneTick`: 所有的配方都是 1 Tick _[推荐]_
3. `Tenths`: 所有的配方时间都是原来的十分之一 _[Recommended]_
4. `Output64`: 所有配方的输入都是1 (如果少于1则什么都不做); 所有的输出都是64 (如果多于64则什么都不做); 1 tick; 1 EU (如果需要EU)
5. `Energyless`: 所有配方的能量都是 0 EU

有些模式**没有涵盖**完整的配方，请继续关注!

**因为本人学业原因，无法继续开发此Mod，欢迎各位有兴趣的开发者贡献代码！**

## 计划表
1. Minecraft
- [x] 熔炉时间
2. GregTech
- [x] GT 配方
- [x] 单方块泵
- [x] 工业熔炉
- [x] 各种钻井
- [ ] 原木拟生
- [ ] 温室
- [ ] 屠宰场
3. Thaumcraft
- [x] 炼金炉（只是烧物品的时间，蒸馏未做）
- [ ] 蒸馏器蒸馏时间
- [ ] 注魔
- [ ] 充能节点速度
4. Ender IO
- [ ] 灵魂绑定器

## 贡献方法
### Mod修改配方的流程
1. 使用Mixin/asm对各模组进行修改，放入处理钩子。（主要是只对各种固定的时间）
2. 使用Handler在服务端（或内置服务端）启动阶段对配方就行修改。
### Mod结构
1. `strategies`包存放各种策略。所有策略均继承抽象类`Strategy`并在其中进行注册。
2. `handler`包存放用于修改各Mod的类。需要加有@IHandler注解，注解中传入执行handler的方法的名称。handler中全部为静态方法，用于向策略转发来着Mixin传来的回调和启动服务器时需要修改的配方。handler在Handlers类中注册，通过反射的形式执行。
3. `mixins`包存放各种mixin，其中late下以Mod名区分子包。late下的mixin需要在LateMixinLoader中注册。其余修改原版类的Mixin需在MixinPlugin中注册。
4. `ModHelper`类用于存放各种Mod存在的信息，如果Mod不存在时不应注册相关的Mixin以及Handler
### Mod开发计划
1. 组合式策略（各种配方细节修改的自由组合）
2. 指令重载客户端配方（目前只需要服务端安装本Mod，但NEI显示不正确，客户端安装后也需要进入单机才能正确显示配方）
2. 支持脚本语言修改配方（CraftTweaker！但是可以拿到已知配方）
3. 导出配方表（dumper分支完成部分功能）
4. 导出图标（dumper已经完成此功能）

