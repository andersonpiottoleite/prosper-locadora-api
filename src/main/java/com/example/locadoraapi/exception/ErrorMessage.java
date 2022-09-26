package com.example.locadoraapi.exception;

/** Classe para exibição de mensagem de erros
 *
 * @author Anderson Piotto
 * @version 1.0.0
 * @since 22/09/2022
 */
public class ErrorMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}