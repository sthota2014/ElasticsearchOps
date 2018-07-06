object ElasticsearchAliasOps {

val esSite="http://localhost:9200"
val credentials = "-uelastic:changeme"

def removeAlias(aliasName:String, aliasStem: String) = {
  import scala.sys.process._
  import scala.util.matching.Regex

  val name = new Regex("(\\w+?)(?=\\d+)")
  //val aliasStem=(name findFirstIn aliasName).getOrElse("NA")
  val cmd1 =Seq("curl", "-H", "Content-Type: application/x-ndjson" ,credentials, "-k" , "-X","GET", esSite + "/_cat/aliases/"+ aliasName)
  val r = cmd1.!!
  val ar = r.split("\n").flatMap(x => x.split(" ")).filter(_.contains(aliasStem))
  //val rmAl = "{\"actions\":[" +(for{ i <- 0 until ar.length ; if(i%2 == 0) } yield "{ \"remove\": {\"alias\": " +"\""+ ar(i) + "\","+ "\"index\":"+ "\""+ ar(i+1) + "\"}}").mkString(",") + "]}"
  val rmAl = "{\"actions\":[" +(for{ i <- 0 until ar.length } yield "{ \"remove\": {\"alias\": " +"\""+ aliasName + "\","+ "\"index\":"+ "\""+ ar(i) + "\"}}").mkString(",") + "]}"
  val cmd2 =Seq("curl", "-H", "Content-Type: application/json", "--data-binary", rmAl, credentials, "-k" , "-X","POST",  esSite + "/_aliases")
  cmd2.!
}

def createAlias(n:Int, idxSubstr:String, aliasStr:String) = {
  import java.time._
  import scala.sys.process._
  val file="/tmp/crAlias"
  def lastNDays(n:Int) = { ((0 to n-1).map(x => Instant.now().minus(Duration.ofDays(x)).toString.substring(0,10).split("-").mkString)).toList }
  //val addIt = lastNDays(n).map(x => idxSubstr + x).map(x => { " {\"add\": { \"index\": " + "\""+x +"\",\"alias\": \""+idxSubstr+n.toString+"\" }}"}).mkString(",")
  val addIt = lastNDays(n).map(x => idxSubstr + x).map(x => { " {\"add\": { \"index\": " + "\""+x +"\",\"alias\": \""+aliasStr+"\" }}"}).mkString(",")
  val lastday2remove = idxSubstr+ Instant.now().minus(Duration.ofDays(n)).toString.substring(0,10).split("-").mkString
  //val rmvIt = { " {\"remove\": { \"index\": " + "\""+ lastday2remove+"\",\"alias\": \""+idxSubstr+n.toString+"\" }}"}
  val rmvIt = { " {\"remove\": { \"index\": " + "\""+ lastday2remove+"\",\"alias\": \""+aliasStr+"\" }}"}
  val totalStr = "{\"actions\":[" + addIt +","+ rmvIt +"]}'"
  //println(totalStr)
  val cmd2 =Seq("curl", "-H", "Content-Type: application/json", "--data-binary", totalStr, credentials, "-k" , "-X","POST",  esSite + "/_aliases")
  cmd2.!
}

def getAliases = {
  import scala.sys.process._
  val cmd1 =Seq("curl", "-H", "Content-Type: application/json" ,credentials, "-k" , "-X","GET", esSite + "/_cat/aliases/" )
  cmd1.!!
  }

def createIndexes(idxSubstr:String, n:Int, defFile: String) = {
import java.time._
import scala.sys.process._
(1 to n) map ( x=> {
                val dtStr = LocalDate.now.plusDays(x).toString.replaceAll("-", "");
                var idxName = idxSubstr + dtStr
                println(idxName)
                val cmd =Seq("curl", "-H", "Content-Type: application/json", "--data-binary", "@"+defFile, "-uelastic:changeme", "-k" , "-X","PUT",  esSite+"/"+idxName)
          //val cmd =Seq("curl", "-H", "Content-Type: application/json", "--data-binary", "@"+defFile, "-uelastic:changeme", "-k" , "-X","PUT",  "https://255.255.255.255:9200/"+idxName)
          //val cmd =Seq("curl", "-H", "Content-Type: application/json", "--data-binary", "@/tmp/createIndex/cr.txt", "-uelastic:changeme", "-k" , "-X","PUT",  "https://255.255.255.255:9200/"+idxName)
                cmd.! })
}

def main(args:Array[String]):Unit = {
  args(0) match {
    case "createAlias" => createAlias(args(1).toInt, args(2),args(3))
    case "getAliases" => println(getAliases)
    case "removeAlias" => removeAlias(args(1), args(2))
    case "createIndexes" => createIndexes(args(1), args(2).toInt, args(3))
    }
  }
}
