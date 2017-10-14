package com.itheima.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.itheima.dao.BookDao;
import com.itheima.dao.BookDaoImpl;
import com.itheima.pojo.Book;
	/**
	 * 写入索引库步骤：
	 * 		//1.采集数据
	 *		//2.创建文档对象
	 *		//3.创建域对象
	 *		//参数说明：1.指定域名；2.域值；3.是否存储
	 *		//4.把数据放到域中，把域放到文档中
	 *		//5.把文档放到集合中
	 *		//6.创建分词器对象
	 *		//7.创建指定索引库地址的流对象
	 *		//8.创建索引库的配置文件
	 *		//参数说明：1、指定Lucene的版本对象；2、指定分词器对象
	 *		//9.创建写入索引库的对象(1、指定索引库地址的流；2、指定索引库配置对象)
	 *		//10.写入索引库
	 *		//11.释放资源
	 * @author yado
	 *
	 */
public class CreateIndexLuceneTest {

	@Test
	public void test() throws Exception{
		
		//1.采集数据
		BookDao dao = new BookDaoImpl();
		List<Book> bookList = dao.queryBookList();
		//创建文档集合
		List<Document> docList = new ArrayList<>();
		for (Book book : bookList) {
			//2.创建文档对象
			Document document = new Document();
			//3.创建域对象
			Field idField = new TextField("id", book.getId()+"", Store.YES);
			Field nameField = new TextField("name", book.getName(), Store.YES);
			Field priceField = new TextField("price", book.getPrice()+"", Store.YES);
			Field picField = new TextField("pic", book.getPic(), Store.YES);
			Field descField = new TextField("desc", book.getDesc(), Store.YES);
			//4.把信息放到域中，把域放到文档中
			document.add(idField);
			document.add(nameField);
			document.add(priceField);
			document.add(picField);
			document.add(descField);
			//5.把文档放到集合中
			docList.add(document);
		}
		
		//6.创建分词器
//		StandardAnalyzer analyzer = new StandardAnalyzer();
		//创建中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		//7.创建指定索引库地址的流对象
		FSDirectory directory = FSDirectory.open(new File("D:\\myData\\temp\\IndexLucene"));
		//8.创建索引库地址的配置文件
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		//9.创建写入索引库的对象
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		for (Document document : docList) {
			//10.写入索引库
			indexWriter.addDocument(document);
		}
		//11.关闭资源
		indexWriter.close();
	}
	
}
