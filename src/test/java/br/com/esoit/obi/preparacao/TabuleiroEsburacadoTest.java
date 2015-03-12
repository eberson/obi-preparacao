package br.com.esoit.obi.preparacao;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TabuleiroEsburacadoTest {
    
    private static final String FILE_TABULEIRO_1 = "tabuleiro-esburacado-1.txt";
    private static final String FILE_TABULEIRO_2 = "tabuleiro-esburacado-2.txt";
    
    private static File fileTabuleiro1;
    private static File fileTabuleiro2;
    
    @BeforeClass
    public static void init(){
        fileTabuleiro1 = loadFile(FILE_TABULEIRO_1);
        fileTabuleiro2 = loadFile(FILE_TABULEIRO_2);
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
    public void testTabuleiro1(){
        TabuleiroEsburacado tabuleiro = new TabuleiroEsburacado(fileTabuleiro1);
        Assert.assertEquals(4, tabuleiro.resolve());
    }

    @Test
    public void testTabuleiro2(){
        TabuleiroEsburacado tabuleiro = new TabuleiroEsburacado(fileTabuleiro2);
        Assert.assertEquals(3, tabuleiro.resolve());
    }
}
