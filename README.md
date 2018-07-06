# ElasticsearchOps

#This helps to create indexes in Elastisearch given a definition file for the index creation with settings and mappings.

#Also helps to create alias, see existing aliases, remove an alias. 

#To create indexes for 3 days. The file cr.txt contains index definition lined-up in one-line.
/opt/mapr/spark/spark-2.2.1/bin/spark-submit  --class ElasticsearchAliasOps /tmp/elasticsearchaliasops_2.10-1.0.jar #createIndexes "fw" 3 "/tmp/createIndex/cr.txt"
