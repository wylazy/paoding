/**
 * 
 */
package net.paoding.analysis.ext;

import java.io.Reader;
import java.util.Map;

import net.paoding.analysis.analyzer.PaodingTokenizer;
import net.paoding.analysis.analyzer.TokenCollector;
import net.paoding.analysis.analyzer.impl.MaxWordLengthTokenCollector;
import net.paoding.analysis.analyzer.impl.MostWordsTokenCollector;
import net.paoding.analysis.knife.PaodingMaker;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

/**
 * <p>
 * PaodingAnalyzer 分词器的Solr适配器工厂方法实现
 * net.paoding.analysis.ext.PaodingAnalyzerSolrFactory
 * </p>
 * 
 * 
 * 
 * 
 * 
 * @author ZhenQin
 *
 */
public class PaodingAnalyzerSolrFactory extends TokenizerFactory {
  
  /**  
   * 最多切分 默认模式  
   */    
  public static final String MOST_WORDS_MODE = "most-words";    
  
  /**  
   * 按最大切分  
   */    
  public static final String MAX_WORD_LENGTH_MODE = "max-word-length";    
  
  private String mode = null;    

  /**
   * 
   */
  public PaodingAnalyzerSolrFactory() {
    
  }
    
    public void setMode(String mode) {    
        if (mode == null || MOST_WORDS_MODE.equalsIgnoreCase(mode)    
                || "default".equalsIgnoreCase(mode)) {    
            this.mode = MOST_WORDS_MODE;    
        } else if (MAX_WORD_LENGTH_MODE.equalsIgnoreCase(mode)) {    
            this.mode = MAX_WORD_LENGTH_MODE;    
        } else {    
            throw new IllegalArgumentException("不合法的分析器Mode参数设置:" + mode);    
        }    
    }    
    
    @Override    
    public void init(Map<String, String> args) {    
        super.init(args);    
        String mode = args.get("mode");
        if (mode != null) {
          setMode(mode);    
        }
    } 
    
       
    
    private TokenCollector createTokenCollector() {    
      if (MAX_WORD_LENGTH_MODE.equals(mode)) {
        return new MaxWordLengthTokenCollector(); 
      }
      return new MostWordsTokenCollector();  
    }     

    
  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  /* (non-Javadoc)
   * @see org.apache.solr.analysis.TokenizerFactory#create(java.io.Reader)
   */
  public Tokenizer create(Reader input) {
    return new PaodingTokenizer(input, PaodingMaker.make(),    
                createTokenCollector()); 
  }

}
