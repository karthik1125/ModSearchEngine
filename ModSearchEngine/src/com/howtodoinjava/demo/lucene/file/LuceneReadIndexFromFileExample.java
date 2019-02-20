package com.howtodoinjava.demo.lucene.file;

//=================================      read the query, process it, and retrieve results          ============================

import java.util.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;



import org.tartarus.snowball.ext.PorterStemmer;

public class LuceneReadIndexFromFileExample
{
    //directory contains the lucene indexes
    private static final String INDEX_DIR = "fadditional";
	private static Scanner sc;
  
    public static void main(String[] args) throws Exception
    {
        //Create lucene searcher. It search over a single IndexReader.
        IndexSearcher searcher = createSearcher();
        String query1="";
//        -------------------------------------------------------------------------------------------------------
//        --------------------------------------------------------------------------------------------------------
       
        
        
        String indexDir = "G:\\sem5\\IR\\Assignment-02\\LuceneDemo_developed\\indexedFiles";
	    Directory index = FSDirectory.open(Paths.get(indexDir));

	    IndexReader reader = DirectoryReader.open(index);

	    Fields fields = MultiFields.getFields(reader);
	    Vector<String> indices = new Vector<String>();
	    for (String field : fields)
	    {
	     //   System.out.println(field);
	        if(field.equals("contents"))
	        {
	        	Terms terms = fields.terms(field);
		        TermsEnum termsEnum = terms.iterator();

		        //Term term = new Term(field);
		        //int count = 0;
		        while (termsEnum.next() != null) {
		            indices.add(termsEnum.term().utf8ToString());
		            //count++;
		        }
		       // System.out.println(indices);
		        Vector<Vector<String>> v = new Vector<Vector<String>>(27);
		        
				for (int i = 0; i < 27; i++)
		        {
					Vector<String> indices_1 = new Vector<String>();
					v.add(i,indices_1);
					v.get(i).add("0");
		        }
				for (int i = 0; i < indices.size(); i++)
		        {
					String s1 =indices.get(i);
					char c = s1.charAt(0) ;
					if ((int) c > 97)
					{
						Character.toLowerCase(c);
						int ind = ((int) c ) - 96 ;
						//v<ind>.add(ind, s1);
						v.get(ind).add(s1);
						v.get(ind).set(0,Integer.toString(Math.max(s1.length(),Integer.parseInt(v.get(ind).get(0)))));
						
					}
					else
					{
						v.get(0).add(s1);
						v.get(0).set(0,Integer.toString(Math.max(s1.length(),Integer.parseInt(v.get(0).get(0)))));

					}
		        }
				//System.out.println(v);
				
				
//=================================     process query           ============================
				
				
				sc = new Scanner(System.in);
				System.out.println("-------->  Enter Query: ");
				String name1 = new String(sc.nextLine());
				
				String[] splited = name1.split("\\s+");
				
				//String name1="flutepricviolin";
				name1 =name1.toLowerCase();
				char c1;
				String  c2,name = "";
//				name = name1.replaceAll(" ","");
				Set<String> mainans = new HashSet<String>();
				
				
				for (int z = 0; z < splited.length;z++)
				{
					
					int j=0,ind1,jnew;
					String max_len,str;
					
					Vector<String> ans = new Vector<String>();
					
					Vector<Integer> e = new Vector<Integer>();
					Vector<String> estr01 = new Vector<String>();
					Vector<String> estr02 = new Vector<String>();
					int dummy=0,pu=9999;
					name = splited[z];
					//System.out.println(name);
					while( j < name.length())
		            {
						c1 =name.charAt(j);
						c2=Character.toString(c1) ;
						
						if ((int) c1 > 97)
						{
							//Character.toLowerCase(c1);
							ind1 = ((int) c1 ) - 96 ;
						}
						else
						{
							ind1 = 0;
						}
						//
							//v<ind>.add(ind, s1);
						max_len = v.get(ind1).get(0);
						int max_l=Integer.parseInt(max_len),e1;
											
						if(max_l > name.length()-dummy)
						{
							max_l = name.length()-dummy;
						}
						
	//					System.out.printf("dummy-%d j-%d max_l-%d  %n",dummy,j,max_l);
						
						String estr1="",estr2 ="";
						jnew=j ;
						while(max_l > 0)
						{
							e1=999999;
							for (int j1 = 1; j1 < v.get(ind1).size(); j1++)
				            {
								str =  v.get(ind1).get(j1);
								int enew = edit.editDistDP( c2 , str, c2.length(), str.length());
	//							System.out.printf("c2-%s str-%s enew - %d %n", c2,str,enew);
	//							if(enew==0)
	//								ans.add(str);
								if( enew < e1)
								{
									e1 = enew;
									estr1 = str;
									estr2 = c2;
								}
				            }
							e.add(e1);
							estr01.add(estr1);
							estr02.add(estr2);
							jnew = jnew+1;
							max_l--; 
							if (max_l != 0)
							{
								c2 = c2 + Character.toString(name.charAt(jnew)) ;
							}
							
						}
						
						double[] ratio = new double[e.size()];
						for (int j1 = 0; j1 < e.size(); j1++)
			            {
	//						
							ratio[j1] =  (double) e.get(j1) / estr01.get(j1).length();	
			            }
	//					Object obj = Collections.min(ratio);
	//					System.out.println(obj);
						double  mini=999.00;
						for (int j1 = 0; j1 < e.size(); j1++)
			            {
							if(ratio[j1]<mini)
							{
								mini = ratio[j1];
							}
			            }
						for (int j1 = 0; j1 < e.size(); j1++)
			            {	
							
							if(ratio[j1] == mini)
							{
	//							System.out.println(estr.get(j1));
								//query1=query1 + estr01.get(j1)+ " ";
								ans.add(estr01.get(j1));
								if(estr02.get(j1).length() < pu)
							    {
									pu = estr02.get(j1).length();
							    }
							}
			            }
						e.clear();
						estr01.clear();
						estr02.clear();
						
						j = j + pu ;
						dummy = dummy + pu;
						pu = 9999;
						//System.out.println(ans);
						for(int i = 0; i<ans.size(); i++)
						{
							mainans.add(ans.get(i));
						}
						ans.clear();
						//System.out.println(mainans);
						//System.out.println(ans);
						//Set<String> words = new HashSet<String>(ans);
	//					System.out.printf("------------------------  dummy-%d j-%d max_l-%d  %n",dummy,j,max_l);
		            }
					
				}
				String sterm;
				Set<String> mainans1 = new HashSet<String>();
				
				for(String s: mainans) {
					PorterStemmer stemmer = new PorterStemmer();
					stemmer.setCurrent(s); //set string you need to stem
					stemmer.stem();  //stem the word
					sterm = stemmer.getCurrent();//get the stemmed word
					mainans1.add(sterm);
				}
				
							
				for(Iterator<String> k = mainans1.iterator(); k.hasNext();) {
					query1=query1 + k.next()+ " ";
				}
				System.out.println("\n-------->  Showing results for  ::  "+ query1);
				
				
	        }
                
	    }
	    
//=================================     query sent for result retrieval           ============================

	    
	        //Search indexed contents using search term
	        TopDocs foundDocs = searchInContent(query1, searcher);
	          
	        //Total found documents
	        System.out.println("\n-------->  Total Results :: " + foundDocs.totalHits);
	         
	        //Let's print out the path of files which have searched term
	        for (ScoreDoc sd : foundDocs.scoreDocs)
	        {
	            Document d = searcher.doc(sd.doc);
	            String file = "";
	            char c;
	            int g =d.get("path").length()-1 ;
	            while( g>=0) 
	            {
	            	c= d.get("path").charAt(g);
	            	if (c== '\\' )
	            		{
	            		break;
	            		}
	            	file = file + Character.toString(c); 
	            	g=g-1;
	        	}
//	            System.out.println(file);
	            StringBuilder input1 = new StringBuilder();
	            input1.append(file);
	            input1=input1.reverse();
//	            System.out.println(d.get("path"));
	            System.out.println("Path : inputFiles\\" + input1 + ", Score : " + sd.score);

	        }
        
    }
     
    private static TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception
    {
        //Create search query
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);
         
        //search the index
        TopDocs hits = searcher.search(query, 10);
        return hits;
    }
 
    private static IndexSearcher createSearcher() throws IOException
    {
        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
         
        //It is an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
         
        //Index searcher
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}












