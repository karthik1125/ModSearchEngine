package com.howtodoinjava.demo.lucene.file;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class readindex {
	public static void main(String[] args) throws IOException {

	    String indexDir = "G:\\sem5\\IR\\Assignment-02\\LuceneDemo\\indexedFiles";
	    Directory index = FSDirectory.open(Paths.get(indexDir));

	    IndexReader reader = DirectoryReader.open(index);

	    Fields fields = MultiFields.getFields(reader);

	    for (String field : fields) {
	        System.out.println(field);
	        Terms terms = fields.terms(field);
	        TermsEnum termsEnum = terms.iterator();

	        //Term term = new Term(field);
	        int count = 0;
	        while (termsEnum.next() != null) {
	            System.out.println(termsEnum.term().utf8ToString());
	            count++;
	        }
	        System.out.println(count);
	    }
}
}
