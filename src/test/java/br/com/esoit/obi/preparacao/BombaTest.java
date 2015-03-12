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
public class BombaTest {
    
    private static File fileTemSolucao;
    private static File fileSemSolucao;
    
    @BeforeClass
    public static void init(){
        fileSemSolucao = loadFileSemSolucao();
        fileTemSolucao = loadFileTemSolucao();
    }
    
    private static File loadFileTemSolucao(){
        return loadFile("bomba-1.txt");
    }

    private static File loadFileSemSolucao(){
        return loadFile("bomba-2.txt");
    }

    private static File loadFile(String file){
        URL url = BombaTest.class.getResource(file);
        
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    
    @Test
    public void testBombaTemSolucao(){
        Bomba bomba = new Bomba(fileTemSolucao);
        Assert.assertEquals("8", bomba.resolve());
    }

    @Test
    public void testBombaSemSolucao(){
        Bomba bomba = new Bomba(fileSemSolucao);
        Assert.assertEquals("*", bomba.resolve());
    }

}
