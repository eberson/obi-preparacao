package br.com.esoit.obi.preparacao;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColchaoTest {
    
    private static final String FILE_COLCHAO_PASSA_1 = "colchao-1.txt";
    private static final String FILE_COLCHAO_PASSA_2 = "colchao-3.txt";
    private static final String FILE_COLCHAO_NAO_PASSA = "colchao-2.txt";
    
    private static File fileColchaoPassa1;
    private static File fileColchaoPassa2;
    private static File fileColchaoNaoPassa;
    
    @BeforeClass
    public static void init(){
        fileColchaoPassa1 = loadFile(FILE_COLCHAO_PASSA_1);
        fileColchaoPassa2 = loadFile(FILE_COLCHAO_PASSA_2);
        fileColchaoNaoPassa = loadFile(FILE_COLCHAO_NAO_PASSA);
    }
    
    private static File loadFile(String file){
        URL url = ColchaoTest.class.getResource(file);
        
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    
    @Test
    public void testColchaoPassaExemplo1(){
        ProblemaColchao colchao = new ProblemaColchao(fileColchaoPassa1);
        Assert.assertEquals("S", colchao.resolve());
    }

    @Test
    public void testColchaoPassaExemplo2(){
        ProblemaColchao colchao = new ProblemaColchao(fileColchaoPassa2);
        Assert.assertEquals("S", colchao.resolve());
    }

    @Test
    public void testColchaoNaoPassaExemplo(){
        ProblemaColchao colchao = new ProblemaColchao(fileColchaoNaoPassa);
        Assert.assertEquals("N", colchao.resolve());
    }
}
