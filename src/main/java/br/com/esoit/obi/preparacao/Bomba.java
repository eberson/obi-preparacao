package br.com.esoit.obi.preparacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bomba {
    
    private static final int LIMITE_LOOP_INFINITO = 3;
    
    private static final String VAI_EXPLODIR = "*";
    
    private int numeroRotatorias;
    private int numeroRuas;
    
    private int rotatoriaEntrada;
    private int rotatoriaSaida;
    
    private List<Rua> ruas;
    
    private List<Integer> trajeto;
    
    public Bomba(File entrada){
        try {
            init(entrada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void init(File file) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(file));

        try{
            String line;
            int linha = 0;
            
            ruas = new ArrayList<Rua>();
            
            while((line = reader.readLine()) != null){
                if(linha++ == 0){
                    //se for a primeira linha leia os dados principais: número de rotatórias, rotatória de entrada e saída e número de ruas
                    carregaInfoPrincipal(line);
                    continue;
                }
                
                // divide a linha em um vetor de acordo com a quantidade de espaços
                String[] infoRua = line.split(" "); 
                ruas.add(new Rua(Integer.parseInt(infoRua[0]), 
                                 Integer.parseInt(infoRua[1]), 
                                 Integer.parseInt(infoRua[2])));
            }
        }
        finally{
            reader.close();
        }
        
        //se a quantidade de ruas lidas for diferente do definido na primeira linha do arquivo dispara um erro no sistema
        if (ruas.size() != numeroRuas){
            throw new IllegalArgumentException("Número inválido de ruas informadas...");
        }
        
        trajeto = new ArrayList<Integer>();
    }
    
    private void carregaInfoPrincipal(String line) {
        String[] info = line.split(" ");
        
        numeroRotatorias = Integer.parseInt(info[0]);
        rotatoriaEntrada = Integer.parseInt(info[1]);
        rotatoriaSaida = Integer.parseInt(info[2]);
        numeroRuas = Integer.parseInt(info[3]);
        
        //faz as validações de acordo com as restrições previstas no enunciado
        if(numeroRotatorias < 2 || numeroRotatorias > 500){
            throw new IllegalArgumentException("Número inválido de rotatórias (2 <= N <= 500)");
        }
        
        if(numeroRuas < 1 || numeroRuas > 2000){
            throw new IllegalArgumentException("Número inválido de ruas (1 <= M <= 2000)");
        }

        if((rotatoriaEntrada < 0 || rotatoriaEntrada > numeroRotatorias - 1) ||
           (rotatoriaEntrada < 0 || rotatoriaEntrada > numeroRotatorias - 1)){
            throw new IllegalArgumentException("Número inválido para a rotatória de entrada e/ou saída (0 <= E,S <= N-1)");
        }
    }

    public String resolve(){
        int rotatoriaAtual = rotatoriaEntrada;
        
        trajeto.add(rotatoriaAtual);
        int tempo = 0;
        
        //vai ficar em loop até encontrar a saída ou até observar que entrou em loop infinito
        while(rotatoriaAtual != rotatoriaSaida){
            
            //se entrou em loop infinito retorna que vai explodir
            if(entrouEmLoop()){
                return VAI_EXPLODIR;
            }
            
            for(Rua rua : ruas){
                if(rua.getRotatoriaEntrada() == rotatoriaAtual && rua.isSemaforoAberto(tempo)){
                    rotatoriaAtual = rua.getRotatoriaSaida();
                    tempo++;
                    
                    trajeto.add(rotatoriaAtual);
                    break;
                }
            }
        }
        
        return String.valueOf(tempo);
    }
    
    private boolean entrouEmLoop(){
        if(trajeto.size() < 9){
            return false;
        }
        
        int quantidadeTrajetos = trajeto.size();
        int tamanhoGrupo = 2;
        int limite = (int) Math.floor(trajeto.size() / LIMITE_LOOP_INFINITO);
        
        while(tamanhoGrupo <= limite){
            int ocorrencia = 0;
            
            for(int i = 0; i + tamanhoGrupo < quantidadeTrajetos; i++){
                for(int j = i + tamanhoGrupo; j < quantidadeTrajetos; j += tamanhoGrupo){
                    try {
                        if(j + tamanhoGrupo > trajeto.size()){
                            throw new LoopException();
                        }
                        
                        int[] base = getTrajetoItems(tamanhoGrupo, i);
                        int[] test = getTrajetoItems(tamanhoGrupo, j);
                        
                        if(!Arrays.equals(base, test)){
                            throw new LoopException();
                        }

                        ocorrencia++;
                    } catch (LoopException e) {
                        ocorrencia = 0;
                        break;
                    }
                }

                if(ocorrencia > LIMITE_LOOP_INFINITO){
                    return true;
                }
            }
            
            
            tamanhoGrupo++;
        }
        
        return false;
    }
    
    private int[] getTrajetoItems(int tamanhoGrupo, int posicaoInicial){
        int[] trajetoItems = new int[tamanhoGrupo];
        int posicao = 0;
        
        for(int i = posicaoInicial; i < posicaoInicial + tamanhoGrupo; i++){
            trajetoItems[posicao++] = trajeto.get(i);
        }
        
        return trajetoItems;
    }

    private class Rua{
        private int rotatoriaEntrada;
        private int rotatoriaSaida;
        private int semaforo;

        public Rua(int rotatoriaEntrada, int rotatoriaSaida, int semaforo) {
            super();
            this.rotatoriaEntrada = rotatoriaEntrada;
            this.rotatoriaSaida = rotatoriaSaida;
            this.semaforo = semaforo;
        }

        public int getRotatoriaEntrada() {
            return rotatoriaEntrada;
        }
        
        public int getRotatoriaSaida() {
            return rotatoriaSaida;
        }
        
        public boolean isSemaforoAberto(int tempo){
            return (semaforo == 1 && tempo % 3 == 0) || 
                   (semaforo == 0 && tempo % 3 != 0);
        }
    }
    
    private class LoopException extends Exception{

        private static final long serialVersionUID = 1L;
        
    }
}
