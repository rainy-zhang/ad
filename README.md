# 基于SpringCould实现的广告系统

ad-data-dump将数据导出到*.data文件中

ad-search启动后，通过IndexFileLoader.java将文件中的数据加载到索引对象中定义的Map中

ad-search启动后，通过BinlogRunner.java开启对MySQL Binlog的监听，用于将增量数据添加到内存中

"# ad" 
