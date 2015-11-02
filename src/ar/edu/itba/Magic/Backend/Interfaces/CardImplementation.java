package ar.edu.itba.Magic.Backend.Interfaces;


import javax.smartcardio.Card;

/**
 * Created by Martin on 02/11/2015.
 */
public interface CardImplementation<T extends Card> {

    public T CardImpllementation();

}
