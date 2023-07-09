package org.kamicaze.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(){
        super("Pedido Nao Encontrado");
    }
}
