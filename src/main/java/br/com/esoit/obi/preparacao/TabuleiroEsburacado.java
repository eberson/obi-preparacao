package br.com.esoit.obi.preparacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TabuleiroEsburacado {

    private Posicao posicaoAtual;
    private List<Posicao> buracos;
    
    private int movimentosPasseio;
    private int[] movimentosExecutar;

    
    public TabuleiroEsburacado(File file){
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
                linha++;
                
                if(linha == 1){
                    movimentosPasseio = Integer.parseInt(line.trim());
                    continue;
                }

                if(linha == 2){
                    String[] movimentos = line.split(" ");
                    movimentosExecutar = new int[movimentos.length];
                    int posicao = 0;
                    
                    for (String movimento : movimentos) {
                        movimentosExecutar[posicao++] = Integer.parseInt(movimento);
                    }

                    continue;
                }
            }
        }
        finally{
            reader.close();
        }
        
        posicaoAtual = new Posicao(4, 3);
        buracos = new ArrayList<Posicao>();
        buracos.add(new Posicao(1, 3));
        buracos.add(new Posicao(2, 3));
        buracos.add(new Posicao(2, 5));
        buracos.add(new Posicao(5, 4));

        if(movimentosPasseio < 1 || movimentosPasseio > 100){
            throw new IllegalArgumentException("1 ≤, N ≤, 100");
        }
        
        if(movimentosExecutar.length != movimentosPasseio){
            throw new IllegalArgumentException("1 ≤ Mi ≤ 8, para i = 1, 2,..., N");
        }
    }
    
    public int resolve(){
        int movimentosExecutados = 0;
        
        for (int movimento : movimentosExecutar) {
            posicaoAtual = proximaPosicao(movimento);
            
            if(posicaoAtual.saiuDoTabuleiro()){
                throw new IllegalArgumentException("Saiu do tabuleiro...");
            }

            movimentosExecutados++;
            
            if (buracos.contains(posicaoAtual)){
                return movimentosExecutados;
            }
            
        }
        
        return movimentosExecutados;
    }
    
    private Posicao proximaPosicao(int movimento){
        int x, y;
        
        switch (movimento) {
            case 1:
                x = posicaoAtual.getX() + 1;
                y = posicaoAtual.getY() + 2;
                break;
            case 2:
                x = posicaoAtual.getX() + 2;
                y = posicaoAtual.getY() + 1;
                break;
            case 3:
                x = posicaoAtual.getX() + 2;
                y = posicaoAtual.getY() - 1;
                break;
            case 4:
                x = posicaoAtual.getX() + 1;
                y = posicaoAtual.getY() - 2;
                break;
            case 5:
                x = posicaoAtual.getX() - 1;
                y = posicaoAtual.getY() - 2;
                break;
            case 6:
                x = posicaoAtual.getX() - 2;
                y = posicaoAtual.getY() - 1;
                break;
            case 7:
                x = posicaoAtual.getX() - 2;
                y = posicaoAtual.getY() + 1;
                break;
            case 8:
                x = posicaoAtual.getX() - 1;
                y = posicaoAtual.getY() + 2;
                break;
            default:
                x = -1;
                y = -1;
                break;
        }
        
        return new Posicao(x, y);
    }

    private class Posicao{
        private int x;
        private int y;
        
        public Posicao(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        public boolean saiuDoTabuleiro(){
            return (x < 0 || x > 7) || (y < 0 || y > 7);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Posicao)) {
                return false;
            }
            Posicao other = (Posicao) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }

        private TabuleiroEsburacado getOuterType() {
            return TabuleiroEsburacado.this;
        }
    }
}
