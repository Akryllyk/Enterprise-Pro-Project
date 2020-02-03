/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author hamma
 */
public class NoResultSetFoundException extends Exception{
    
     /**
     * Constructs an instance for NoArrayFoundException with the specified
     * detail message
     * 
     * @param message the detailed message
     */
    public NoResultSetFoundException(String message) {
        super(message);
    }
}
