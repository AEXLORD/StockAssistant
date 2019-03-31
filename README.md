# StockAssistant
  该项目目的是为了帮助长线投资者能够在长期不看盘的情况下还能掌握股票数据（当前价格、pb、成交量）而写的，希望大神们能够一起交流讨论
  
  用户可以将自己关注的股票代码、每股净资产、接收邮箱、时间间隔等信息输入软件。软件会周期性的检查该股票信息，出现pb（平均市净率：price/ book value）低于或高于某一值时进行风险提示，或者当日成交额大于某一值时提示风险预警。
  
  目前测试用例为 601398 中国工商银行 的股票，设定的pb风险值分别为 pb>1.2、pb<0.9、pb<0.8、成交额大于50亿

# 1.运行环境
1.jdk 1.80 以上

2.lib -> javax.mail.jar

得益于Java的跨平台性，无论在Windows还是在Linux上都能很好运行（无界面，后台运行）

# 2.前期配置
src -> account.properties 中首先要配置一个'发送邮箱'的信息
>    myEmailAccount=  邮箱账号

>    myEmailPassword= 邮箱密码

>    myEmailSMTPHost= 邮箱服务器地址

之后使用'eclipse'或其他ide将项目打包成可执行程序，在Launch configuration中选择'Running.java'

# 3.运行配置
>    please inpute stock number :

输入股票代码
>    please inpute asset value :

输入该股票每股市净率
>    please inpute your email :

输入接受信息的邮箱
>    please inpute the time space :

输入检测间隔时间

# End
一个小工具，也没有多复杂，如果有喜欢的同学方便的话点个star

也欢迎感兴趣的同学交流讨论

邮箱：609924504Zyz@gmail.com
