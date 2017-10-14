package com.itheima.test;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
/**
 * 搜索索引库的步骤：
 * 		1.创建分词器对象
 * 		2.创建一个搜索解析器对象QueryParser解析器对象(1.默认查询的域名；2.搜索的分析器对象)
 * 		3.创建查询的条件对象Query
 * 		//参数说明：1.存放的是查询 条件；2.可以自定义查询语法
 * 		//当参数中只有查询的关键字的时候，那么久根据默认查询域来搜索
 * 		//当参数中自定义查询域名的时候，那么就根据自定义的查询域来搜索，会覆盖默认查询域
 * 		4.创建一个索引库地址的流
 * 		5.创建读取索引库对象(根据这个索引库地址流)[文档，索引]
 * 		6.创建一个搜索的对象IndexSearch(根据读取流对象)[文档，索引]
 * 		7.根据条件对象执行查询，查询的是索引库中索引：返回结果集
 * 		//参数说明：1.查询的条件对象；2.查询返回的记录数
 * 		8.根据结果集获取坐标文档ID
 * 		9.遍历数组
 * 		10.根据每一个坐标获取文档ID
 * 		11.在IndexSearch中根据文档ID查询文档数据
 * 		12.打印文档数据
 * 		13.释放资源
 * @author yado
 *
 */
public class SearchLuceneTest {

	@Test
	public void test() throws Exception{
		
		//1.创建分词器对象
//		StandardAnalyzer analyzer = new StandardAnalyzer();
		//创建中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		//2.创建搜索查询对象QueryParser
		QueryParser queryParser = new QueryParser("name", analyzer);
		//3.创建条件查询对象Query
		Query query = queryParser.parse("编程");
		//4.创建索引库地址流对象
		FSDirectory directory = FSDirectory.open(new File("D:\\myData\\temp\\IndexLucene"));
		//5.根据这个流对象创建读取索引库对象
		IndexReader reader = DirectoryReader.open(directory);
		//6.根据读取流对象创建索引库搜索对象IndexSearch
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		//7.执行条件查询，查询的结果是索引库中的索引，返回结果集
		TopDocs docs = indexSearcher.search(query, 3);
		
		//查询总记录数
		System.out.println("总记录数==="+docs.totalHits);
		
		//8.根据结果集获取坐标文档ID，数组形式
		ScoreDoc[] scoreDocs = docs.scoreDocs;
		//9.遍历数组
		for (ScoreDoc scoreDoc : scoreDocs) {
			//10.根据每一个坐标获取文档ID
			int docId = scoreDoc.doc;
			//11.在IndexSearch中根据文档ID查询文档数据
			Document doc = indexSearcher.doc(docId);
			//12.打印文档数据
			System.out.println("id=="+doc.get("id")); 
			System.out.println("name=="+doc.get("name")); 
			System.out.println("price=="+doc.get("price")); 
			System.out.println("pic=="+doc.get("pic")); 
			System.out.println("desc=="+doc.get("desc")); 
		}
		//13.释放资源
		reader.close();
	}
	
}
