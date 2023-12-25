1. 原型图

# 实现功能：

拟定投票主题，设计投票内容，发起投票，统计投票，多人投票，未投票人统计



# 数据库表

1. 用户表（users）：
   1. 主键
   2. 用户编号
   3. 用户名
2. 投票表（polls）：
   1. 主键
   2. 标题
   3. 作者名 -- 通过users关联获取
   4. 所选内容选项编号 --内容表关联
   5. 时间b
3. 投票记录表（vote_records）：
   1. 主键
   2. 投票表主键
   3. 用户编号--users主键
   4. 投票选项编号 --投票表关联
4. 投票人员表（voters）：
   1. 主键
   2. 用户编号 --user表关联
   3. 投票编号 -- 投票关联
   4. 是否投票
5. 所有内容表(content)：
   1. 主键
   2. 内容名称







主页面：

1. 已经创建好的投票表展示

点击发起投票--添加功能

1. 发起人--下拉列表--查询用户表
2. 投票人员 -- 查询用户表
3. 定义单选内容 -- 查询内容表

发布 -- 提交 -- 投票表 投票记录表 投票人员表

开始投票

1. 学号和姓名 -下拉框
2. 投票 -- 查询投票表展示进行投票
3. 确定--投票人员表的修改

投票结果

1. 投票表的部分内容展示
2. 统计条的展示 -- 查询投票表所选选内容选项编号  统计查询投票人员表的投票情况
3. 展示投票人员情况
   1. 已投 -- 查询投票人员表的已投的数量，和查询已投人员的名字
   2. 未投 -- 查询投票人员表的未投的数量，和查询未投人员的名字





----

1.首先一个用户发起投票
他可以选择哪些用户可以进行投票
选择发起的标题，和所要投票的哪些选项
发布结束后
2.开始投票
输入学号和姓名
对单选框进行选择
投票结束
3.投票结果
显示选项的选择情况
显示已投的数量和人名
未投的数量和人名


polls表根据标题查询返回主键和选项id展示出在进行投票的时候
投完进行记录

```sql
-- SELECT v.vote_records_id,
-- 	p.polls_id,p.polls_title,p.polls_time,
-- 	p.content_id,
-- 	u.users_id,u.users_uid,u.users_uname
-- FROM vote_records v join polls p on v.polls_id = p.polls_id
-- 								join users u on v.users_id = u.users_id;


-- select v.voters_id,v.voters_flag,
-- p.polls_id,p.polls_title,p.polls_time,p.content_id,
-- u.users_id,u.users_uid,u.users_uname from voters v 
-- 					join users u on v.users_id = u.users_id
-- 					join polls p on v.polls_id = p.polls_id;

select count(*)
from voters group by voters_flag;

select count(*)
from voters where voters_flag=0;

select count(*)
from voters where voters_flag=1;

select u.users_uname from users u  
                    join voters v on v.users_id = u.users_id 
                    where v.voters_flag = 1
```

