# ElasticsearchOps

>This helps to create indexes in Elastisearch given a definition file for the index creation with settings and mappings.
>Also helps to create alias, see existing aliases, remove an alias. 

#-------------------------------------------------------------------------------------------------

#To create indexes for 3 days. The file cr.txt contains index definition lined-up in one-line.

#-------------------------------------------------------------------------------------------------

bin/spark-submit --class ElasticsearchAliasOps /tmp/elasticsearchops_2.11-1.0.jar createIndexes "fw" 3 "/tmp/createIndex/cr.txt"
fw20180707
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   125  100    48  100    77    981   1573 --:--:-- --:--:-- --:--:--  1571
{"acknowledged":true,"shards_acknowledged":true}fw20180708
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   125  100    48  100    77   1113   1785 --:--:-- --:--:-- --:--:--  1833
{"acknowledged":true,"shards_acknowledged":true}fw20180709
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   125  100    48  100    77   1117   1792 --:--:-- --:--:-- --:--:--  1833

#-------------------------------------------------------------------------------------------------

#To create an alias starting from today until 7 days back

#-------------------------------------------------------------------------------------------------

bin/spark-submit --class ElasticsearchAliasOps /tmp/elasticsearchops_2.11-1.0.jar createAlias 7 fw WeeklyFirewall

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   534  100    21  100   513    881  21522 --:--:-- --:--:-- --:--:-- 22304
{"acknowledged":true}

#-------------------------------------------------------------------------------------------------

#To check existing aliases in Elasticsearch

#-------------------------------------------------------------------------------------------------

bin/spark-submit --class ElasticsearchAliasOps /tmp/elasticsearchops_2.11-1.0.jar getAliases

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   224  100   224    0     0  35572      0 --:--:-- --:--:-- --:--:-- 37333
WeeklyFirewall fw20180706 - - -
WeeklyFirewall fw20180703 - - -
WeeklyFirewall fw20180702 - - -
WeeklyFirewall fw20180705 - - -
WeeklyFirewall fw20180630 - - -
WeeklyFirewall fw20180701 - - -
WeeklyFirewall fw20180704 - - -

#-------------------------------------------------------------------------------------------------

#To remove an alias 

#-------------------------------------------------------------------------------------------------

bin/spark-submit --class ElasticsearchAliasOps /tmp/elasticsearchops_2.11-1.0.jar removeAlias WeeklyFirewall fw

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   224  100   224    0     0  38030      0 --:--:-- --:--:-- --:--:-- 44800
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   468  100    21  100   447   1122  23893 --:--:-- --:--:-- --:--:-- 24833
{"acknowledged":true}

