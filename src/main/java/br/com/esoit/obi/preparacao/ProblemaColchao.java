package br.com.esoit.obi.preparacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ProblemaColchao {
    
    private Colchao colchao;
    private Porta porta;
    
    public ProblemaColchao(File file){
        try {
            init(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void init(File file) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(file));

        try{
            String line;
            int linha = 0;
            
            while((line = reader.readLine()) != null){
                String[] info = line.split(" ");
                linha++;
                
                if(linha == 1){
                    colchao = new Colchao(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[0]));
                    continue;
                }

                if(linha == 2){
                    porta = new Porta(Integer.parseInt(info[1]), Integer.parseInt(info[0]));
                    continue;
                }
            }
        }
        finally{
            reader.close();
        }
    }
    
    public String resolve(){
        return espacoDisponivelPorta() >= espacoNecessarioColchao() ? "S" : "N";
    }
    
    private int espacoNecessarioColchao(){
        int espacoNecessario = colchao.getComprimento();
        
        if(espacoNecessario > colchao.getLargura()){
            espacoNecessario = colchao.getLargura();
        }
        
        return espacoNecessario;
    }

    private int espacoDisponivelPorta() {
        int espacoDisponivel = calculaMaiorDiagonalPossivel(porta, colchao);
        
        if(espacoDisponivel < porta.getAltura()){
            espacoDisponivel = porta.getAltura();
        }
        
        if(espacoDisponivel < porta.getLargura()){
            espacoDisponivel = porta.getLargura();
        }
        
        return espacoDisponivel;
    }

    private int calculaMaiorDiagonalPossivel(Porta porta, Colchao colchao){
        int espacoImpossivelUtilizar = colchao.getEspessura();
        
        int diagonal = (int) Math.floor(Math.sqrt(Math.pow(porta.getAltura(), 2) + 
                Math.pow(porta.getLargura(), 2)));
        
        return diagonal - espacoImpossivelUtilizar;
    }
    
    private class Colchao{
        private int largura;
        private int comprimento;
        private int espessura;
        
        public Colchao(int largura, int comprimento, int espessura) {
            super();
            
            if((largura < 0 || largura > 300) || 
               (comprimento < 0 || comprimento > 300) || 
               (espessura < 0 || espessura > 300)){
                throw new IllegalArgumentException("1 ≤ A, B, C ≤ 300");
            }
            
            this.largura = largura;
            this.comprimento = comprimento;
            this.espessura = espessura;
        }
        
        public int getLargura() {
            return largura;
        }
        
        public int getComprimento() {
            return comprimento;
        }
        
        public int getEspessura() {
            return espessura;
        }
    }
    
    private class Porta{
        private int largura;
        private int altura;
        
        public Porta(int largura, int altura) {
            super();

            if((largura < 0 || largura > 300) || 
               (altura < 0 || altura > 300)){
                throw new IllegalArgumentException("1 ≤ H, L ≤ 250");
            }
            
            this.largura = largura;
            this.altura = altura;
        }
        
        public int getLargura() {
            return largura;
        }
        
        public int getAltura() {
            return altura;
        }
    }
}
